package red.tetracube.iot.smartigloohub.data.entities

import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "houses_accounts")
data class HouseAccount(
    @Id
    val id: UUID? = null,

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne(targetEntity = Account::class, fetch = FetchType.LAZY)
    val account: Account? = null,

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(targetEntity = House::class, fetch = FetchType.LAZY)
    val house: House? = null
)
