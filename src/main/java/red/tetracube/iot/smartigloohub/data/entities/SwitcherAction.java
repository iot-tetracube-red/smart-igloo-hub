package red.tetracube.iot.smartigloohub.data.entities;

import red.tetracube.iot.smartigloohub.data.enumerations.LogicState;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "switchers_actions")
public class SwitcherAction {

    @Id
    private UUID id;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "logic_state", nullable = false)
    private LogicState logicState;

    @Column(name = "mqtt_command", nullable = false)
    private String mqttCommand;

    @JoinColumn(name = "switcher_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Switcher.class)
    private Switcher switcher;

}