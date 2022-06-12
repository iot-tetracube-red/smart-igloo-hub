package red.tetracube.iot.smartigloohub.data.entities;

import red.tetracube.iot.smartigloohub.data.enumerations.HouseType;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "houses")
public class House {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "house_type", nullable = false)
    private HouseType houseType;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Environment.class, mappedBy = "house")
    private List<Environment> environments = Collections.emptyList();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = HouseAccount.class, mappedBy = "house")
    private List<HouseAccount> housesAccounts = Collections.emptyList();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher.class, mappedBy = "house")
    private List<Switcher> switchers = Collections.emptyList();

}
