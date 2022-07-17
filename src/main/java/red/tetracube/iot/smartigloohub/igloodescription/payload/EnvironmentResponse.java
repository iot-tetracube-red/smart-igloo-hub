package red.tetracube.iot.smartigloohub.igloodescription.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class EnvironmentResponse {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("appliances")
    private final List<SwitchResponse> appliances;

    public EnvironmentResponse(UUID id, String name, List<SwitchResponse> appliances) {
        this.id = id;
        this.name = name;
        this.appliances = appliances;
    }

    public List<SwitchResponse> getAppliances() {
        return appliances;
    }
}
