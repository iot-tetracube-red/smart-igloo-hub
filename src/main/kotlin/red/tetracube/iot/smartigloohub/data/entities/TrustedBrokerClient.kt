package red.tetracube.iot.smartigloohub.data.entities

import red.tetracube.iot.smartigloohub.data.enumerations.ClientType
import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "trusted_broker_clients")
data class TrustedBrokerClient(
    @Id
    val id: UUID? = null,

    @Column(name = "client_id", nullable = false, unique = true)
    val clientId: String? = null,

    @Column(name = "username", nullable = false, unique = true)
    val username: String? = null,

    @Column(name = "password", nullable = false)
    val password: String? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "client_type", nullable = false)
    val clientType: ClientType? = null,

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher::class, mappedBy = "trustedBrokerClient")
    val environments: List<Switcher> = emptyList()
)
