package red.tetracube.iot.smartigloohub.data.repositories;

import org.hibernate.reactive.mutiny.Mutiny;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EnvironmentRepository{

    @Inject
    Mutiny.SessionFactory rxSessionFactory;

}