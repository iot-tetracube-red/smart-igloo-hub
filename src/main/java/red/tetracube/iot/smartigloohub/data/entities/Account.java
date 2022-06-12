package red.tetracube.iot.smartigloohub.data.entities;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "accounts")
public class Account {

    @Id
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = HouseAccount.class, mappedBy = "account")
    private List<HouseAccount> housesAccounts = Collections.emptyList();

}
