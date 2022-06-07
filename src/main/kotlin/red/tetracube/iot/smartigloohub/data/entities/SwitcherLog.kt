package red.tetracube.iot.smartigloohub.data.entities

import red.tetracube.iot.smartigloohub.data.enumerations.LogicState
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "switchers_logs")
data class SwitcherLog(
    @Id
    val id: UUID? = null,

    @Column(name = "stored_at", nullable = false)
    val storedAt: Instant? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "logic_state", nullable = false)
    val logicState: LogicState? = null,

    @JoinColumn(name = "switcher_id", nullable = false)
    @ManyToOne(targetEntity = Switcher::class, fetch = FetchType.LAZY)
    val switcher: Switcher? = null
)