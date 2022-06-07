package red.tetracube.iot.smartigloohub.data.entities

import red.tetracube.iot.smartigloohub.data.enumerations.HouseType
import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "houses")
data class House(
    @Id
    val id: UUID? = null,

    @Column(name = "name", nullable = false, unique = true)
    val name: String? = null,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "house_type", nullable = false)
    val houseType: HouseType? = null,

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Environment::class, mappedBy = "house")
    val environments: List<Environment> = emptyList(),

    @OneToMany(fetch = FetchType.LAZY, targetEntity = HouseAccount::class, mappedBy = "house")
    val housesAccounts: List<HouseAccount> = emptyList(),

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher::class, mappedBy = "house")
    val switchers: List<Switcher> = emptyList()
)
