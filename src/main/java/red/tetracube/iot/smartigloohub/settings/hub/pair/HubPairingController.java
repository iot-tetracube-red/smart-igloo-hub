package red.tetracube.iot.smartigloohub.settings.hub.pair;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.smallrye.mutiny.Uni;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingRequest;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingResponse;

@Path("/settings/hub")
public class HubPairingController {

    @Inject
    HubPairingLogic hubPairingLogic;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pair")
    public Uni<HubPairingResponse> pairHub(@Valid HubPairingRequest request) {
        return hubPairingLogic.pairHub(request);
    }
}
