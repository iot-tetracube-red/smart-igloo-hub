package red.tetracube.iot.smartigloohub.messagingbroker.api.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MessagingBrokerEventRequest @JsonCreator constructor(
    @JsonProperty("username")
    @field:NotEmpty
    @field:NotNull
    val username: String? = null,

    @JsonProperty("clientid")
    val clientId: @NotNull UUID? = null,

    @JsonProperty("action")
    @NotEmpty @NotNull
    val action: String? = null
)