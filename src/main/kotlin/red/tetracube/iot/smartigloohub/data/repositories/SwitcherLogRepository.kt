package red.tetracube.iot.smartigloohub.data.repositories

import org.hibernate.reactive.mutiny.Mutiny
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SwitcherLogRepository(
    private val rxSessionFactory: Mutiny.SessionFactory
) {
}