package red.tetracube.iot.smartigloohub.mqtt

import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import io.quarkus.vertx.ConsumeEvent
import io.vertx.mutiny.core.eventbus.EventBus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import red.tetracube.iot.smartigloohub.properties.SmartIglooProperties
import javax.inject.Singleton

@Singleton
class MessagingClientLink(
    private val smartIglooProperties: SmartIglooProperties,
    private val messagingClient: Mqtt3AsyncClient,
    private val eventBus: EventBus
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MessagingClientLink::class.java)
    }

    @ConsumeEvent("application-started")
    fun makeConnection(isStarted: Boolean) {
        messagingClient.connectWith()
            .simpleAuth()
            .username(smartIglooProperties.app().name())
            .password(smartIglooProperties.iot().applicationPassword().encodeToByteArray())
            .applySimpleAuth()
            .send()
            .whenComplete { mqttConnAck, exception ->
                if (exception != null) {
                    LOGGER.warn("Something went wrong during connection due: ${exception.message}")
                    LOGGER.warn("The backend will continue to work, but no interaction with your devices will be dispatched or received")
                    return@whenComplete
                }
                LOGGER.info("Connection success: ${mqttConnAck.returnCode.code}")
                eventBus.publish("mqtt-connected", true)
            }
    }
}