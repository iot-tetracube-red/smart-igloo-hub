package red.tetracube.iot.smartigloohub.startup

import io.quarkus.runtime.StartupEvent
import io.vertx.mutiny.core.eventbus.EventBus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.enterprise.event.Observes
import javax.inject.Singleton

@Singleton
class SmartIglooHubStartupNotifier(
    private val eventBus: EventBus
) {

    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(SmartIglooHubStartupNotifier::class.java)
    }

    fun notifyApplicationStartup(@Observes startup: StartupEvent) {
        LOGGER.info("CosyNest is started... notifying listeners")
        eventBus.publish("application-started", true)
    }
}