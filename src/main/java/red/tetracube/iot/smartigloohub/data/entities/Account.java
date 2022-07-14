package red.tetracube.iot.smartigloohub.data.entities;

import red.tetracube.iot.smartigloohub.enumerations.AccountHubRole;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "igloo_hub", name = "accounts")
public class Account {

    @Id
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private AccountHubRole role;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccountHubRole getRole() {
        return role;
    }
}
