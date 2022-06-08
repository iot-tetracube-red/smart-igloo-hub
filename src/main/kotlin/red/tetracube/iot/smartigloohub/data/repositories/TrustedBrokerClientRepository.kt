package red.tetracube.iot.smartigloohub.data.repositories

import io.smallrye.mutiny.Uni
import org.hibernate.reactive.mutiny.Mutiny
import red.tetracube.iot.smartigloohub.data.entities.TrustedBrokerClient
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TrustedBrokerClientRepository(
    private val rxSessionFactory: Mutiny.SessionFactory
) {

    fun getBrokerClientByClientIdAndUsername(clientId: String, username: String): Uni<TrustedBrokerClient?> {
        return rxSessionFactory.withSession { sesssion ->
            sesssion.createQuery(
                """
                from TrustedBrokerClient t
                where t.clientId = :clientId and t.username = :username
            """.trimIndent(),
                TrustedBrokerClient::class.java
            )
                .setParameter("clientId", clientId)
                .setParameter("username", username)
                .setMaxResults(1)
                .singleResultOrNull
        }
    }
}