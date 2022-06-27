package red.tetracube.iot.smartigloohub.data.entities;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "switchers")
public class Switcher {

    @Id
    private UUID id;

    @Column(name = "display_name", nullable = false, unique = true)
    private String displayName;

    @Column(name = "display_color", nullable = false)
    private String displayColor;

    @Column(name = "feedback_topic", nullable = false, unique = true)
    private String feedbackTopic;

    @Column(name = "command_topic", nullable = false, unique = true)
    private String commandTopic;

    @Column(name = "is_online", nullable = false)
    private Boolean online;

    @JoinColumn(name = "environment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Environment.class)
    private Environment environment;

    @OneToMany(targetEntity = SwitcherAction.class, fetch = FetchType.LAZY, mappedBy = "switcher")
    private List<SwitcherAction> switcherActions = Collections.emptyList();

    @OneToMany(targetEntity = SwitcherLog.class, fetch = FetchType.LAZY, mappedBy = "switcher")
    private List<SwitcherLog> switcherLogs = Collections.emptyList();

    public UUID getId() {
        return id;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
