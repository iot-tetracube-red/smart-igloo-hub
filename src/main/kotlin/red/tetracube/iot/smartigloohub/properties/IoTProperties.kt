package red.tetracube.iot.smartigloohub.properties

interface IoTProperties {

    fun clientId(): String
    fun host(): String
    fun port(): Int
    fun applicationPassword(): String
}
