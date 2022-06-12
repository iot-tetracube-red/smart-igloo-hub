package red.tetracube.iot.smartigloohub.messagingbroker;

import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import red.tetracube.iot.smartigloohub.configuration.properties.SmartIglooProperties;
import red.tetracube.iot.smartigloohub.data.entities.Switcher;
import red.tetracube.iot.smartigloohub.data.repositories.SwitcherRepository;
import red.tetracube.iot.smartigloohub.data.repositories.TrustedBrokerClientRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class MessagingBrokerService {

    @Inject
    SwitcherRepository switcherRepository;

    @Inject
    SmartIglooProperties smartIglooProperties;

    @Inject
    TrustedBrokerClientRepository trustedBrokerClientRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingBrokerService.class);

    Uni<Void> updateOnlineStatus(String clientId, String action) {
        var wentOnline = List.of("session_terminated", "client_disconnected", "client_connack", "client_connected").contains(action);

        var switcherUni = this.switcherRepository.getSwitcherByClientId(clientId);
        var deviceCombineUni = Uni.combine().all().unis(switcherUni, Uni.createFrom().item(false))
                .asTuple();
        return deviceCombineUni
                .map(devicesTuple -> {
                            var switcher = devicesTuple.getItem1();
                            var other = devicesTuple.getItem2();
                            if (switcher != null && other == null) {
                                LOGGER.info("Switcher found with id ${switcher.id}");
                                return switcher;
                            } else if (switcher == null && other != null) {
                                return other;
                            } else {
                                return null;
                            }
                        }
                )
                .flatMap(device -> {
                    if (device instanceof Switcher switcher) {
                        LOGGER.info("Updating switch with status $wentOnline");
                        switcher.setOnline(wentOnline);
                        return this.switcherRepository.updateSwitcher(switcher);
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    public Uni<Boolean> tryToAuthenticateFeature(String clientId,
                                                 String username,
                                                 String password) {
        LOGGER.info("Checking if authentication is application service");
        if (Objects.equals(password, smartIglooProperties.iot().applicationPassword())) {
            return Uni.createFrom().item(true);
        }

        LOGGER.info("Trying to authenticate feature with client id {} and username {}", clientId, username);
        var trustedBrokerClientUni = trustedBrokerClientRepository.getBrokerClientByClientIdAndUsername(
                clientId,
                username
        );
        return trustedBrokerClientUni.map(brokerClient -> {
            if (brokerClient == null) {
                return false;
            } else {
                return passwordEncoder.matches(password, brokerClient.getPassword());
            }
        });
    }
}