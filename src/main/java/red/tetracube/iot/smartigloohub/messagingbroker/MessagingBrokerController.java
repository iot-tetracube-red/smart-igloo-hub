package red.tetracube.iot.smartigloohub.messagingbroker;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.iot.smartigloohub.messagingbroker.payloads.MessagingBrokerClientAuthenticationRequest;
import red.tetracube.iot.smartigloohub.messagingbroker.payloads.MessagingBrokerEventRequest;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/message-broker")
public class MessagingBrokerController {

    @Inject
    MessagingBrokerService messagingBrokerService;

    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingBrokerController.class);

    @POST
    @PermitAll
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> storeEvent(@RequestBody @Valid MessagingBrokerEventRequest request) {
        LOGGER.info("Event arrived {}", request);
        var updateOnlineStatusUni = messagingBrokerService.updateOnlineStatus(
                request.clientId(),
                request.action()
        );
        return updateOnlineStatusUni.map(ignored -> Response.ok().build());
    }

    @POST
    @PermitAll
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> authenticate(@RequestBody @Valid MessagingBrokerClientAuthenticationRequest request) {
        LOGGER.info("Authenticating client id {}", request.clientId());
        var authenticationResultUni = messagingBrokerService.tryToAuthenticateFeature(
                request.clientId(),
                request.username(),
                request.password()
        );
        return authenticationResultUni.map(authenticationResult -> {
            if (authenticationResult) {
                return Response.ok(request.clientId()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("INVALID_BROKER_CREDENTIALS")
                        .build();
            }
        });
    }
}