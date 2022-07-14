package red.tetracube.iot.settings.hub.pair;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import red.tetracube.iot.smartigloohub.settings.hub.pair.payloads.HubPairingRequest;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;

@QuarkusTest
public class HubPairingControllerTest {

    @Test
    public void paringShouldReturnValidationErrors() {
        var body = new HubPairingRequest();
        var response = given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/settings/hub/pair")
                .then();
        response.statusCode(400);
        response.body("violations[0].field", oneOf("pairHub.request.username", "pairHub.request.password"));
        response.body("violations[1].field", oneOf("pairHub.request.username", "pairHub.request.password"));
    }

    @Test
    public void pairingShouldBeOk() {
        var body = new HubPairingRequest();
        body.setPassword("password");
        body.setUsername("tech_user");
        var response = given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/settings/hub/pair")
                .then();
        response.statusCode(200);
        response.body("hub_name", is("TestHub"));
    }

    @Test
    public void shouldBeUnauthorized() {
        var body = new HubPairingRequest();
        body.setPassword("adsfasdf");
        body.setUsername("tech_useafdr");
        var response = given()
                .body(body)
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .post("/settings/hub/pair")
                .then();
        response.statusCode(401);
    }
}
