package red.tetracube.iot.smartigloohub.data.entities

import red.tetracube.iot.smartigloohub.data.enumerations.LogicState
import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "switchers_actions")
data class SwitcherAction(
    @Id
    val id: UUID? = null,

    @Column(name = "display_name", nullable = false)
    val displayName: String? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "logic_state", nullable = false)
    val logicState: LogicState? = null,

    @Column(name = "mqtt_command", nullable = false)
    val mqttCommand: String? = null,

    @JoinColumn(name = "switcher_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Switcher::class)
    val switcher: Switcher? = null
)