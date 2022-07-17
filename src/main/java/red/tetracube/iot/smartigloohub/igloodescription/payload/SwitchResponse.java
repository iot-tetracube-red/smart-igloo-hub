package red.tetracube.iot.smartigloohub.igloodescription.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import red.tetracube.iot.smartigloohub.data.enumerations.LogicState;

import java.time.LocalDateTime;
import java.util.UUID;

public class SwitchResponse {

    @JsonProperty("id")
    private final UUID id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("currentLogicState")
    private final LogicState logicState;

    @JsonProperty("lastProbedState")
    private final LocalDateTime lastProbedState;

    @JsonProperty("isOnline")
    private final Boolean isOnline;

    public SwitchResponse(UUID id,
                          String name,
                          LogicState logicState,
                          LocalDateTime lastProbedState,
                          Boolean isOnline) {
        this.id = id;
        this.name = name;
        this.logicState = logicState;
        this.lastProbedState = lastProbedState;
        this.isOnline = isOnline;
    }
}
