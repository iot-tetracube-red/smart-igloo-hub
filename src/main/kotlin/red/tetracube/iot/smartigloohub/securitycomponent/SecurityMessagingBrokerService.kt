package red.tetracube.iot.smartigloohub.securitycomponent

import io.smallrye.mutiny.Uni
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import red.tetracube.iot.smartigloohub.data.repositories.TrustedBrokerClientRepository
import red.tetracube.iot.smartigloohub.properties.SmartIglooProperties
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SecurityMessagingBrokerService(
    private val smartIglooProperties: SmartIglooProperties,
    private val trustedBrokerClientRepository: TrustedBrokerClientRepository
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(SecurityMessagingBrokerService::class.java)
    }

    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    fun tryToAuthenticateFeature(clientId: String, username: String, password: String): Uni<Boolean> {
        LOGGER.info("Checking if authentication is application service")
        if (password == smartIglooProperties.iot().applicationPassword()) {
            return Uni.createFrom().item(true)
        }

        LOGGER.info("Trying to authenticate feature with client id {} and username {}", clientId, username)
        val trustedBrokerClientUni = trustedBrokerClientRepository.getBrokerClientByClientIdAndUsername(
            clientId,
            username
        )
        return trustedBrokerClientUni.map { brokerClient ->
            if (brokerClient == null) {
                false
            } else {
                passwordEncoder.matches(password, brokerClient.password)
            }
        }
    }
}