package red.tetracube.iot.settings.hub.pair;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class HubPairingControllerTest {

    @Test
    public void paringShouldReturnValidationErrors() {
        var body = new HubPairingRequest();
        var response = given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/settings/hub/pairing")
                .then();
        response.statusCode(400);
        response.body("violations[0].field", is("pairHub.request.username"));
        response.body("violations[1].field", is("pairHub.request.password"));
    }
}
