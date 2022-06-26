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

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher.class, mappedBy = "environment")
    private List<Switcher> environments = Collections.emptyList();

}
