package red.tetracube.iot.smartigloohub

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Contact
import org.eclipse.microprofile.openapi.annotations.info.Info
import javax.ws.rs.core.Application

@OpenAPIDefinition(
    tags = [
        /*Tag(name = "authorization", description = "Operations to retrieve and store authorization data"),
        Tag(name = "settings", description = "Operations related to app settings"),
        Tag(name = "features", description = "Operations to retrieve feature details accordingly with the type"),
        Tag(name = "features user interface", description = "Operations with users can interact with their features")*/
    ],
    info = Info(
        title = "Smart Igloo Hub",
        version = "1.0.0",
        description = "APIs to interact and to make your Igloo more Smart automating actions",
        contact = Contact(
            email = "penginet.it@gmail.com",
            name = "TetraCube RED"
        )
    )
)
class SmartIglooHub : Application() {
}