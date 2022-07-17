package red.tetracube.iot.smartigloohub.data.repositories;

import io.smallrye.mutiny.Uni;
import org.hibernate.reactive.mutiny.Mutiny;
import red.tetracube.iot.smartigloohub.data.entities.Environment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class EnvironmentRepository {

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

    public Uni<List<Environment>> getIglooEnvironments() {
        var rxSessionUni = rxSessionFactory.openSession();
        return rxSessionUni.flatMap(rxSession ->
                rxSession.createQuery("from Environment environment", Environment.class)
                        .getResultList()
                        .eventually(rxSession::close)
        );
    }

}