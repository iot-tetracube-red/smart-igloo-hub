package red.tetracube.iot.smartigloohub.settings.hub.pair.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HubPairingResponse {

    @JsonProperty("hub_name")
    private String hubName;

    @JsonProperty("authentication_token")
    private String authenticationToken;

    public HubPairingResponse(String hubName, String authenticationToken) {
        this.hubName = hubName;
        this.authenticationToken = authenticationToken;
    }
}
