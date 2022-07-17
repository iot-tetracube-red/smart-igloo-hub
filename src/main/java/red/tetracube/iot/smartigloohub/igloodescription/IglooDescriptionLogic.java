package red.tetracube.iot.smartigloohub.igloodescription;

import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.iot.smartigloohub.data.entities.Environment;
import red.tetracube.iot.smartigloohub.data.repositories.EnvironmentRepository;
import red.tetracube.iot.smartigloohub.igloodescription.payload.EnvironmentResponse;
import red.tetracube.iot.smartigloohub.igloodescription.payload.IglooDescriptionResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class IglooDescriptionLogic {

    private final static Logger LOGGER = LoggerFactory.getLogger(IglooDescriptionLogic.class);

    @Inject
    EnvironmentRepository environmentRepository;

    public Uni<IglooDescriptionResponse> getIglooDescription() {
        LOGGER.info("Getting igloo environments");
        var environmentsUni = environmentRepository.getIglooEnvironments();
        return environmentsUni.map(this::mapEnvironments)
                .flatMap(iglooDescriptionResponse -> mapSwitcher(iglooDescriptionResponse));
    }

    private Uni<? extends IglooDescriptionResponse> mapSwitcher(IglooDescriptionResponse iglooDescriptionResponse) {
        return iglooDescriptionResponse.getEnvironments().stream()
                .map(environmentResponse -> {

                });
    }

    private IglooDescriptionResponse mapEnvironments(List<Environment> environments) {
        var iglooResponse = new IglooDescriptionResponse(new ArrayList<>());
        var environmentsResponse = environments.stream()
                .map(environment ->
                        new EnvironmentResponse(environment.getId(), environment.getName(), new ArrayList<>())
                )
                .toList();
        iglooResponse.getEnvironments().addAll(environmentsResponse);
        return iglooResponse;
    }
}
