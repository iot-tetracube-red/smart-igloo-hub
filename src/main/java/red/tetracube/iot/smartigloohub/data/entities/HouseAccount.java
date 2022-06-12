package red.tetracube.iot.smartigloohub.data.entities;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "houses_accounts")
public class HouseAccount {

    @Id
    private UUID id;

    @JoinColumn(name = "account_id", nullable = false)
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    private Account account;

    @JoinColumn(name = "house_id", nullable = false)
    @ManyToOne(targetEntity = House.class, fetch = FetchType.LAZY)
    private House house;

}
