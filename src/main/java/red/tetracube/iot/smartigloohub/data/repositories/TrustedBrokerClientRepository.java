package red.tetracube.iot.smartigloohub.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.iot.smartigloohub.data.entities.TrustedBrokerClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class TrustedBrokerClientRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<TrustedBrokerClient> getBrokerClientByClientIdAndUsername(String clientId, String username) {
        return rxSessionFactory.withSession(session ->
                session.createQuery("""
                                            from TrustedBrokerClient t
                                            where t.clientId = :clientId and t.username = :username
                                        """,
                                TrustedBrokerClient.class
                        )
                        .setParameter("clientId", clientId)
                        .setParameter("username", username)
                        .setMaxResults(1)
                        .getSingleResultOrNull()
        );
    }

}