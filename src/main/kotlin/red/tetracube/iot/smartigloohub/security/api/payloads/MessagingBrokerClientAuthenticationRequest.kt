package red.tetracube.iot.smartigloohub.security.api.payloads

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import io.smallrye.common.constraint.NotNull
import javax.validation.constraints.NotEmpty

data class MessagingBrokerClientAuthenticationRequest @JsonCreator constructor(
    @field:NotNull
    @JsonProperty("client_id")
    val clientId: String? = null,

    @field:NotNull
    @field:NotEmpty
    @JsonProperty("username")
    val username: String? = null,

    @field:NotNull
    @field:NotEmpty
    @JsonProperty("password")
    val password: String? = null
)
