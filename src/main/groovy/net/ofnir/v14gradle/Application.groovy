package net.ofnir.v14gradle

import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.Key
import com.vaadin.flow.component.Text
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.EnableVaadin
import com.vaadin.flow.theme.Theme
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service

@SpringBootApplication
@EnableVaadin
@Theme('app-theme')
class VaadinApplication implements AppShellConfigurator {

    static void main(String[] args) {
        SpringApplication.run(VaadinApplication, args)
    }

}

@Service
class HelloService {
    String sayHello(String to) { "Hello, ${to ?: "World"}" }
}

@Route("")
class MainView extends Composite<Div> {
    MainView(HelloService lolService) {
        TextField tf = new TextField().tap {
            placeholder = "Please enter a name to greet"
            id = "name-input"
            focus()
        }
        Button b = new Button("Greet!").tap {
            addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            id = "greet-button"
            addClickShortcut(Key.ENTER)
            addClickListener {
                content.add(new Div(new Text(lolService.sayHello(tf.value))))
            }
        }
        content.tap {
            add(new H1("Greeting Service"))
            add(new FormLayout(tf, b).tap{
                responsiveSteps = [new FormLayout.ResponsiveStep("0px", 1)]
            })
        }
    }
}
