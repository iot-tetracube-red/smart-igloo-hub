package red.tetracube.iot.smartigloohub.data.entities

import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "accounts")
data class Account(
    @Id
    val id: UUID? = null,

    @Column(name = "username", nullable = false, unique = true)
    val username: String? = null,

    @Column(name = "password", nullable = false)
    val password: String? = null,

    @OneToMany(fetch = FetchType.LAZY, targetEntity = HouseAccount::class, mappedBy = "account")
    val housesAccounts: List<HouseAccount> = emptyList()
)
