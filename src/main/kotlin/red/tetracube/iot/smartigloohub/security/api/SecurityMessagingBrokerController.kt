package red.tetracube.iot.smartigloohub.security.api

import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import red.tetracube.iot.smartigloohub.security.api.payloads.MessagingBrokerClientAuthenticationRequest
import javax.annotation.security.PermitAll
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/message-broker")
class SecurityMessagingBrokerController(
    private val securityMessagingBrokerService: SecurityMessagingBrokerService
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(SecurityMessagingBrokerController::class.java)
    }

    @POST
    @PermitAll
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun authenticate(@RequestBody @Valid request: MessagingBrokerClientAuthenticationRequest): Uni<Response> {
        LOGGER.info("Authenticating client id ${request.clientId}")
        val authenticationResultUni = securityMessagingBrokerService.tryToAuthenticateFeature(
            request.clientId!!,
            request.username!!,
            request.password!!
        )
        return authenticationResultUni.map { authenticationResult ->
            if (authenticationResult)
                Response.ok(request.clientId).build()
            else
                Response.status(Response.Status.UNAUTHORIZED)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("INVALID_BROKER_CREDENTIALS")
                    .build()

        }
    }
}