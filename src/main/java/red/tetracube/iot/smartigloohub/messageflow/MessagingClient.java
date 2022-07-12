package red.tetracube.iot.smartigloohub.messageflow;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;

import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

class MessageFlowClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageFlowClient.class);

    @Singleton
    public Mqtt3AsyncClient messageFlowClient(SmartIglooProperties smartIglooProperties) {
        LOGGER.info("Creating MQTT client");
        return MqttClient.builder()
                .identifier(smartIglooProperties.iot().clientId())
                .serverHost(smartIglooProperties.iot().host())
                .serverPort(smartIglooProperties.iot().port())
                .useMqttVersion3()
                .automaticReconnect()
                .initialDelay(5, TimeUnit.SECONDS)
                .maxDelay(3, TimeUnit.MINUTES)
                .applyAutomaticReconnect()
                .buildAsync();
    }
}