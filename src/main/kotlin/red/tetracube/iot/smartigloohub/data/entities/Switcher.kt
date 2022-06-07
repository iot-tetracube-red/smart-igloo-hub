package red.tetracube.iot.smartigloohub.data.entities

import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "switchers")
data class Switcher(
    @Id
    val id: UUID? = null,

    @Column(name = "display_name", nullable = false, unique = true)
    val displayName: String? = null,

    @Column(name = "display_color", nullable = false)
    val displayColor: String? = null,

    @Column(name = "feedback_topic", nullable = false, unique = true)
    val feedbackTopic: String? = null,

    @Column(name = "command_topic", nullable = false, unique = true)
    val commandTopic: String? = null,

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = House::class)
    val house: House? = null,

    @JoinColumn(name = "environment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Environment::class)
    val environment: Environment? = null,

    @JoinColumn(name = "trusted_broker_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TrustedBrokerClient::class)
    val trustedBrokerClient: TrustedBrokerClient? = null,

    @OneToMany(targetEntity = SwitcherAction::class, fetch = FetchType.LAZY, mappedBy = "switcher")
    val switcherActions: List<SwitcherAction> = emptyList(),

    @OneToMany(targetEntity = SwitcherLog::class, fetch = FetchType.LAZY, mappedBy = "switcher")
    val switcherLogs: List<SwitcherLog> = emptyList()
)
