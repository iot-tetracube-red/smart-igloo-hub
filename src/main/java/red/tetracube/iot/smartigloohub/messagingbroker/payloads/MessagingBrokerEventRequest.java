package red.tetracube.iot.smartigloohub.messagingbroker.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record MessagingBrokerEventRequest(@JsonProperty("username") @NotEmpty @NotNull String username,
                                          @JsonProperty("clientid") @NotNull @NotEmpty String clientId,
                                          @JsonProperty("action") @NotEmpty @NotNull String action
) {

}