package red.tetracube.iot.smartigloohub.messageflow.listeners;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import io.quarkus.vertx.ConsumeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;
import red.tetracube.iot.smartigloohub.data.enumerations.SwitcherLineStatus;
import red.tetracube.iot.smartigloohub.data.repositories.SwitcherRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.UUID;

@Singleton
public class TestamentMessageListener {

    @Inject
    Mqtt3AsyncClient messageFlowClient;

    @Inject
    SwitcherRepository switcherRepository;

    @Inject
    SmartIglooProperties smartIglooProperties;

    private final static Logger LOGGER = LoggerFactory.getLogger(TestamentMessageListener.class);

    @ConsumeEvent("mqtt-connected")
    public void subscribeToSwitchersTestamentTopics(boolean connected) {
        var topicFilter = smartIglooProperties.iot().topics().switcherLwtTopic();
        messageFlowClient.subscribeWith()
                .topicFilter(topicFilter)
                .qos(MqttQos.AT_MOST_ONCE)
                .callback(mqtt3Publish -> this.testamentTopicCallback(mqtt3Publish))
                .send()
                .whenComplete((ack, ex) -> LOGGER.info("Listening for switchers testament topics"));
    }

    private void testamentTopicCallback(Mqtt3Publish mqtt3Publish) {
        var payloadMessage = new String(mqtt3Publish.getPayloadAsBytes());
        LOGGER.info("Arrived LWT message on topic {} with message {}",
                mqtt3Publish.getTopic(),
                payloadMessage);
        UUID switcherId;
        try {
            var switcherIdString = mqtt3Publish.getTopic().getLevels().get(1);
            switcherId = UUID.fromString(switcherIdString);
        } catch (Exception ex) {
            LOGGER.warn("Invalid LWT topic {} ignoring message", mqtt3Publish.getTopic());
            return;
        }
        updateSwitcherStatus(switcherId, payloadMessage);
    }

    private void updateSwitcherStatus(UUID switcherId, String status) {
        var goneOnline = Objects.equals(status, SwitcherLineStatus.ONLINE.getStatus());
        var switcherUni = switcherRepository.getSwitcherById(switcherId);
        switcherUni.chain(switcher -> {
            switcher.setOnline(goneOnline);
            return switcherRepository.updateSwitcher(switcher);
        })
                .subscribe()
                .with(ignored -> LOGGER.info(
                        "Updated switch {} with status {}",
                        switcherId,
                        status));
    }
}
