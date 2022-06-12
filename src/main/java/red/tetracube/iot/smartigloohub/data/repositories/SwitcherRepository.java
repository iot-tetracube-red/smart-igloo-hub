package red.tetracube.iot.smartigloohub.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.iot.smartigloohub.data.entities.Switcher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class SwitcherRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<Switcher> getSwitcherByClientId(String clientId) {
        return this.rxSessionFactory.withSession(session ->
                session.createQuery("""
                                            from Switcher switcher
                                            where switcher.trustedBrokerClient.clientId = :clientId
                                        """,
                                Switcher.class
                        )
                        .setParameter("clientId", clientId)
                        .setMaxResults(1)
                        .getSingleResultOrNull()
        );
    }

    public Uni<Void> updateSwitcher(Switcher switcher) {
        return this.rxSessionFactory.withSession(session ->
                session.merge(switcher)
                        .chain(session::flush)
        );
    }

}