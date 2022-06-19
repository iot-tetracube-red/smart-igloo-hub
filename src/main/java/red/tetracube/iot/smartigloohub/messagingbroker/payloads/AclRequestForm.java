package red.tetracube.iot.smartigloohub.messagingbroker.payloads;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import javax.ws.rs.core.MediaType;

public class AclRequestForm {

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String access;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String username;

    @RestForm("client_id")
    @PartType(MediaType.TEXT_PLAIN)
    public String clientId;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String topic;

}
