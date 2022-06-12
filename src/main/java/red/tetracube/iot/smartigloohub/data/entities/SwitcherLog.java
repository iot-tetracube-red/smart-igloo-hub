package red.tetracube.iot.smartigloohub.data.entities;

import red.tetracube.iot.smartigloohub.data.enumerations.LogicState;
import java.time.Instant;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "switchers_logs")
public class SwitcherLog {

    @Id
    private UUID id;

    @Column(name = "stored_at", nullable = false)
    private Instant storedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "logic_state", nullable = false)
    private LogicState logicState;

    @JoinColumn(name = "switcher_id", nullable = false)
    @ManyToOne(targetEntity = Switcher.class, fetch = FetchType.LAZY)
    private Switcher switcher;

}