package red.tetracube.iot.smartigloohub.realtime.listeners;

import java.util.Objects;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import io.quarkus.vertx.ConsumeEvent;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;
import red.tetracube.iot.smartigloohub.data.enumerations.SwitcherLineStatus;
import red.tetracube.iot.smartigloohub.data.repositories.SwitcherRepository;

@Singleton
public class TestamentMessageListener {

    @Inject
    Mqtt3AsyncClient messagingClient;

    @Inject
    SwitcherRepository switcherRepository;

    @Inject
    SmartIglooProperties smartIglooProperties;

    private final static Logger LOGGER = LoggerFactory.getLogger(TestamentMessageListener.class);

    @ConsumeEvent("mqtt-connected")
    public void subscribeToSwitchersTestamentTopics(boolean connected) {
        messagingClient.subscribeWith()
                .topicFilter(smartIglooProperties.iot().topics().switcherLwtTopic())
                .qos(MqttQos.AT_MOST_ONCE)
                .callback(mqtt3Publish -> {
                    var payloadMessage = new String(mqtt3Publish.getPayloadAsBytes());
                    LOGGER.info("Arrived LWT message on topic {} with message {}", mqtt3Publish.getTopic(), payloadMessage);
                    UUID switcherId;
                    try {
                        var switcherIdString = mqtt3Publish.getTopic().getLevels().get(1);
                        switcherId = UUID.fromString(switcherIdString);
                    } catch (Exception ex) {
                        LOGGER.warn("Invalid LWT topic {} ignoring message", mqtt3Publish.getTopic());
                        return;
                    }
                    updateSwitcherStatus(switcherId, payloadMessage);

                })
                .send()
                .whenComplete((ack, ex) ->
                        LOGGER.info("Listening for switchers testament topics")
                );
    }

    private void updateSwitcherStatus(UUID switcherId, String status) {
        var goneOnline = Objects.equals(status, SwitcherLineStatus.ONLINE.getStatus());
        var switcherUni = switcherRepository.getSwitcherById(switcherId);
        switcherUni.chain(switcher -> {
                    switcher.setOnline(goneOnline);
                    return switcherRepository.updateSwitcher(switcher);
                })
                .subscribe()
                .with(ignored -> LOGGER.info("Updated switch {} with status {}", switcherId, status));
    }
}
