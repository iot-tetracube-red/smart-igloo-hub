package red.tetracube.iot.smartigloohub.data.entities;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "environments")
public class Environment {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(targetEntity = House.class, fetch = FetchType.LAZY)
    private House house;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher.class, mappedBy = "environment")
    private List<Switcher> environments = Collections.emptyList();

}
