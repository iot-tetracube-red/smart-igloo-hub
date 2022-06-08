package red.tetracube.iot.smartigloohub.messagingbroker.api

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import red.tetracube.iot.smartigloohub.messagingbroker.api.payloads.MessagingBrokerEventRequest
import javax.annotation.security.PermitAll
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Path("/message-broker")
class MessagingBrokerController(
    private val messagingBrokerService: MessagingBrokerService
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MessagingBrokerController::class.java)
    }

    @POST
    @PermitAll
    @Path("/events")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun storeEvent(@RequestBody @Valid request: MessagingBrokerEventRequest): Uni<Response> {
        LOGGER.info("Event arrived {}", request)
        val updateOnlineStatusUni = messagingBrokerService.updateOnlineStatus(
            request.clientId!!,
            request.username!!,
            request.action!!
        )
        return updateOnlineStatusUni.map {
            Response.ok().build()
        }
    }
}