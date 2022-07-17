package red.tetracube.iot.smartigloohub.igloodescription.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IglooDescriptionResponse {

    @JsonProperty("environments")
    private final List<EnvironmentResponse> environments;

    public IglooDescriptionResponse(List<EnvironmentResponse> environments) {
        this.environments = environments;
    }

    public List<EnvironmentResponse> getEnvironments() {
        return environments;
    }
}
