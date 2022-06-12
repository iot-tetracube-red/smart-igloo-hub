package red.tetracube.iot.smartigloohub.data.entities;

import red.tetracube.iot.smartigloohub.data.enumerations.ClientType;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(schema = "igloo_hub", name = "trusted_broker_clients")
public class TrustedBrokerClient {

    @Id
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "client_type", nullable = false)
    private ClientType clientType;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Switcher.class, mappedBy = "trustedBrokerClient")
    private List<Switcher> environments = Collections.emptyList();

    public String getPassword() {
        return password;
    }
}