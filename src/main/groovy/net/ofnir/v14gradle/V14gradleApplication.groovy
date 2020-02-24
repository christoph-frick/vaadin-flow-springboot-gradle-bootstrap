package net.ofnir.v14gradle

import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service

@SpringBootApplication
class V14gradleApplication {

    static void main(String[] args) {
        SpringApplication.run(V14gradleApplication, args)
    }

}

@Service
class LolService {
    String lol() { "lol" }
}

@Route("")
@Theme(value = Lumo)
class MainView extends Composite<Div> {
    MainView(LolService lolService) {
        content.add(new H1(lolService.lol()))
        content.add(new ComboBox<String>().tap{
            setItems(["1", "2", "3", "4", "5"])
        })
    }
}
