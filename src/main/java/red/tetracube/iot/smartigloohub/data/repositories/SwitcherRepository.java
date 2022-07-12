package red.tetracube.iot.smartigloohub.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.iot.smartigloohub.data.entities.Switcher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class SwitcherRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<Switcher> getSwitcherById(UUID id) {
        return this.rxSessionFactory.openSession().flatMap(session -> session.createQuery("""
                    from Switcher switcher
                    where switcher.id = :id
                """,
                Switcher.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getSingleResultOrNull()
                .eventually(session::close));
    }

    public Uni<Void> updateSwitcher(Switcher switcher) {
        return rxSessionFactory.openSession()
                .flatMap(session -> session.merge(switcher)
                        .chain(session::flush)
                        .eventually(session::close));
    }
}
