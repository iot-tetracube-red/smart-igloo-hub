package red.tetracube.iot.smartigloohub.configuration.properties;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "smart-igloo-hub")
public interface SmartIglooProperties {

    AppProperties app();
    IoTProperties iot();

}