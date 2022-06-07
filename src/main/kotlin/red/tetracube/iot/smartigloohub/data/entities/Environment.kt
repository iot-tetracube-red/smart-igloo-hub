package red.tetracube.iot.smartigloohub.data.entities

import java.util.UUID
import javax.persistence.*

@Entity
@Table(schema = "igloo_hub", name = "environments")
data class Environment(
    @Id
    val id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String? = null,

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(targetEntity = House::class, fetch = FetchType.LAZY)
    val house: House? = null,

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher::class, mappedBy = "environment")
    val environments: List<Switcher> = emptyList()
)
