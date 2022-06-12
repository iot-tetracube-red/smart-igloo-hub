package red.tetracube.iot.smartigloohub.messagingbroker.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;

import javax.validation.constraints.NotEmpty;

public record MessagingBrokerClientAuthenticationRequest(@NotNull @JsonProperty("client_id") String clientId,
                                                         @NotNull @NotEmpty @JsonProperty("username") String username,
                                                         @NotNull @NotEmpty @JsonProperty("password") String password) {
}
