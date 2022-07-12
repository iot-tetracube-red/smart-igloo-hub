package red.tetracube.iot.smartigloohub.messageflow;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class MessageFlowLink {

    @Inject
    SmartIglooProperties smartIglooProperties;

    @Inject
    Mqtt3AsyncClient messageFlowClient;

    @Inject
    EventBus eventBus;

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFlowLink.class);

    @ConsumeEvent("application-started")
    public void makeConnection(Boolean isStarted) {
        messageFlowClient.connectWith()
                .simpleAuth()
                .username(smartIglooProperties.app().name())
                .applySimpleAuth()
                .send()
                .whenComplete((mqttConnAck, exception) -> {
                    if (exception != null) {
                        LOGGER.warn("Something went wrong during connection due: {}",
                                exception.getMessage());
                        LOGGER.warn(
                                "The backend will continue to work, but no interaction with your devices will be dispatched or received");
                        return;
                    }
                    LOGGER.info("Connection success: {}", mqttConnAck.getReturnCode().getCode());
                    eventBus.publish("mqtt-connected", true);
                });
    }

}
