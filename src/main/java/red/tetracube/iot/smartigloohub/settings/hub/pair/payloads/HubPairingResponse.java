package red.tetracube.iot.smartigloohub.settings.hub.pair.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class HubPairingResponse {

    @JsonProperty("hub_id")
    private UUID hubId;

    @JsonProperty("hub_name")
    private String hubName;

    @JsonProperty("authentication_token")
    private String authenticationToken;

    public HubPairingResponse(UUID hubId, String hubName, String authenticationToken) {
        this.hubId = hubId;
        this.hubName = hubName;
        this.authenticationToken = authenticationToken;
    }
}
