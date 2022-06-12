package red.tetracube.iot.smartigloohub.startup;

import io.quarkus.runtime.StartupEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SmartIglooHubStartupNotifier{

    @Inject
    EventBus eventBus;

    private final static Logger LOGGER = LoggerFactory.getLogger(SmartIglooHubStartupNotifier.class);

    public void notifyApplicationStartup(@Observes StartupEvent startup) {
        LOGGER.info("CosyNest is started... notifying listeners");
        eventBus.publish("application-started", true);
    }
}