package red.tetracube.iot.smartigloohub.properties

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "smart-igloo-hub")
interface SmartIglooProperties {

    fun app(): AppProperties
    fun iot(): IoTProperties
}