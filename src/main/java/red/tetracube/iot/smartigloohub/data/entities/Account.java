package red.tetracube.iot.smartigloohub.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    public String getPassword() {
        return password;
    }
}
