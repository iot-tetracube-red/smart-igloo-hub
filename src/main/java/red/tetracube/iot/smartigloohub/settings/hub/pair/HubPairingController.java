package red.tetracube.iot.smartigloohub.settings.hub.pair;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingRequest;

@Path("/settings/hub")
public class HubPairingController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pairing")
    public Uni<Void> pairHub(@Valid HubPairingRequest request) {
        return Uni.createFrom().voidItem();
    }
}
