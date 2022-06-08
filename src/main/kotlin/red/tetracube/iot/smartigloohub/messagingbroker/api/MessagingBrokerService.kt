package red.tetracube.iot.smartigloohub.messagingbroker.api

import io.smallrye.mutiny.Uni
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class MessagingBrokerService {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MessagingBrokerService::class.java)
    }

    fun updateOnlineStatus(clientId: UUID, username: String, action: String): Uni<Void> {
        val wentOnline = when (action) {
            "session_terminated", "client_disconnected" -> {
                false
            }
            "client_connack", "client_connected" -> {
                true
            }
            else -> {
                null
            }
        } ?: return Uni.createFrom().voidItem()
     /*   val ledFeature = ledFeatureRepository.getFeatureForAuthenticate(clientId, username)
        val switchFeature = switchFeatureRepository.getFeatureForAuthenticate(clientId, username)
        if (ledFeature == null && switchFeature == null) {
            LOGGER.warn("Cannot find any feature with given client id and username")
        } else if (ledFeature != null && switchFeature == null) {
            LOGGER.info("Checking password for light effect")
            val updatedLedFeature = ledFeature.copy(isOnline = wentOnline)
            return ledFeatureRepository.update(updatedLedFeature)
        } else if (ledFeature == null && switchFeature != null) {
            LOGGER.info("Checking password for the switch")
            val updatedSwitchFeature = switchFeature.copy(isOnline = wentOnline)
            return switchFeatureRepository.update(updatedSwitchFeature)
        }*/
    }
}