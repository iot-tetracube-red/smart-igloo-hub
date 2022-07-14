package red.tetracube.iot.smartigloohub.settings.hub.pair;

import java.time.Duration;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.unchecked.Unchecked;
import org.eclipse.microprofile.config.inject.ConfigProperty;
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

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    AccountRepository accountRepository;

    @Inject
    SmartIglooProperties smartIglooProperties;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Uni<HubPairingResponse> pairHub(HubPairingRequest hubPairingRequest) {
        LOGGER.info("Trying to authenticate user {}", hubPairingRequest.getUsername());
        var accountUni = accountRepository.getAccountFromUsername(hubPairingRequest.getUsername());
        return accountUni.map(
                Unchecked.function(account -> {
                    if (!checkAccount(account, hubPairingRequest.getPassword())) {
                        throw new UnauthorizedException("WRONG_CREDENTIALS");
                    }
                    var token = generateToken(account);
                    return new HubPairingResponse(
                            smartIglooProperties.app().id(),
                            smartIglooProperties.app().name(),
                            token
                    );
                })
        );
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

    private String generateToken(Account account) {
        LOGGER.info("Creating JWT token");
        var currentTime = Calendar.getInstance().getTimeInMillis();
        return Jwt.issuer(issuer)
                .upn(account.getUsername())
                .issuedAt(currentTime)
                .expiresIn(Duration.ofDays(365))
                .groups(Set.of(account.getRole().toString()))
                .sign();
    }
}
