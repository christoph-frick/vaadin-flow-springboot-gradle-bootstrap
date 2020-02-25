package net.ofnir.v14gradle

import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import net.ofnir.theme.BaseView
import net.ofnir.theme.HelloWorld
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.vaadin.stefan.fullcalendar.FullCalendar

@SpringBootApplication
class V14gradleApplication {

    static void main(String[] args) {
        SpringApplication.run(V14gradleApplication, args)
    }

}

@Route("")
@Theme(value = Lumo)
class MainView extends BaseView {
    MainView() {
        add(new HelloWorld())
        add(new FullCalendar().tap{

        })
    }
}
