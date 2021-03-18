package net.ofnir.v14gradle

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Tag
import com.vaadin.flow.component.dependency.JsModule
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.EnableVaadin
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import elemental.json.Json
import groovy.json.JsonOutput
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableVaadin
class V14gradleApplication {

    static void main(String[] args) {
        SpringApplication.run(V14gradleApplication, args)
    }

}

@Route("")
@Theme(value = Lumo)
@Viewport(Viewport.DEVICE_DIMENSIONS)
@BodySize(height = "100vh", width = "100vw")
@Tag('edit-grid')
@JsModule('EditGrid.ts')
class MainView extends Component /* must be component, LitElement is not yet there */{
    MainView() {
        // TODO: there is no "Model" right now
        element.setPropertyJson('items', Json.instance().parse( /* Json.parse always want's to cast to JsonObject */
                JsonOutput.toJson(
                        (0..59).collectMany {
                            [
                                    [alive: true, name: "Ozzy", role: 'singer'],
                                    [alive: false, name: "Lemmy", role: 'bass'],
                            ]
                        }
                )
        )
        )
    }
}
