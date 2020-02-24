package net.ofnir.v14gradle

import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider
import com.vaadin.flow.data.provider.Query
import com.vaadin.flow.router.Route
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service

import java.util.stream.Stream

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
        def cb
        content.add(new H1(lolService.lol()))
        content.add(cb = new ComboBox<String>().tap {
            dataProvider = new LargeDataProvider()
            addValueChangeListener {
                println("cb value-change: fromClient=${it.fromClient}; value=${it.value}")
            }
            value = "for sure no Large instance"
        })
        content.add(new Button("Log value", { println "cb current value=${cb.value}" }))
    }
}

@TupleConstructor
@ToString(includePackage = false)
@EqualsAndHashCode
class Large {
    String id
}

class LargeDataProvider extends AbstractBackEndDataProvider<Large, String> {

    private final List<Large> data = (1..100000).collect { new Large(it.toString()) }

    protected Stream<Large> filteredStream(Query<Large, String> query) {
        data.stream().with { s ->
            query.filter.ifPresent { f ->
                s = s.filter { it.id.contains(f) }
            }
            return s
        }
    }

    @Override
    protected Stream<Large> fetchFromBackEnd(Query<Large, String> query) {
        return filteredStream(query).skip(query.offset).limit(query.limit)
    }

    @Override
    protected int sizeInBackEnd(Query<Large, String> query) {
        return filteredStream(query).count()
    }

    @Override
    boolean isInMemory() {
        return false
    }
}
