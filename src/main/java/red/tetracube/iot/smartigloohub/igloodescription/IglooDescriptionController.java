package red.tetracube.iot.smartigloohub.igloodescription;

import io.smallrye.mutiny.Uni;
import red.tetracube.iot.smartigloohub.igloodescription.payload.IglooDescriptionResponse;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/igloo-description")
public class IglooDescriptionController {

    @Inject
    IglooDescriptionLogic iglooDescriptionLogic;

    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<IglooDescriptionResponse> getIglooDescription() {
        return iglooDescriptionLogic.getIglooDescription();
    }
}
