package red.tetracube.iot.smartigloohub.settings.hub.pair;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;
import red.tetracube.iot.smartigloohub.data.entities.Account;
import red.tetracube.iot.smartigloohub.data.repositories.AccountRepository;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingRequest;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingResponse;

@ApplicationScoped
public class HubPairingLogic {

    private final static Logger LOGGER = LoggerFactory.getLogger(HubPairingLogic.class);

    @Inject
    AccountRepository accountRepository;

    @Inject
    SmartIglooProperties smartIglooProperties;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Uni<HubPairingResponse> pairHub(HubPairingRequest hubPairingRequest) {
        LOGGER.info("Trying to authenticate user {}", hubPairingRequest.getUsername());
        var accountUni = accountRepository.getAccountFromUsername(hubPairingRequest.getUsername());
        return accountUni.map(account -> {
            if (!checkAccount(account, hubPairingRequest.getPassword())) {
                throw new UnauthorizedException("WRONG_CREDENTIALS");
            }
            return new HubPairingResponse(smartIglooProperties.app().name(), "authenticationToken");
        });
    }

    private boolean checkAccount(Account account, String rawPassword) {
        if (account == null) {
            LOGGER.warn("Cannot find any user with specified username");
            return false;
        }
        if (!passwordEncoder.matches(rawPassword, account.getPassword())) {
            LOGGER.warn("Wrong credentials for user");
            return false;
        }
        LOGGER.info("User authenticated");
        return true;
    }
}
