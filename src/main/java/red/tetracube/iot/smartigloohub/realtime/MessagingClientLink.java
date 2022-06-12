package red.tetracube.iot.smartigloohub.realtime;

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;

@Singleton
class MessagingClientLink {

    @Inject
    SmartIglooProperties smartIglooProperties;

    @Inject
    Mqtt3AsyncClient messagingClient;

    @Inject
    EventBus eventBus;

    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingClientLink.class);

    @ConsumeEvent("application-started")
    public void makeConnection(Boolean isStarted) {
        messagingClient.connectWith()
                .simpleAuth()
                .username(smartIglooProperties.app().name())
                .password(smartIglooProperties.iot().applicationPassword().getBytes(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .send()
                .whenComplete((mqttConnAck, exception) -> {
                    if (exception != null) {
                        LOGGER.warn("Something went wrong during connection due: {}", exception.getMessage());
                        LOGGER.warn("The backend will continue to work, but no interaction with your devices will be dispatched or received");
                        return;
                    }
                    LOGGER.info("Connection success: {}", mqttConnAck.getReturnCode().getCode());
                    eventBus.publish("mqtt-connected", true);
                });
    }
}