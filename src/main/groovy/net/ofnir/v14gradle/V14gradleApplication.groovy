package net.ofnir.v14gradle


import com.vaadin.flow.component.AbstractField
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.HasValueAndElement
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.page.BodySize
import com.vaadin.flow.component.page.Viewport
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.BeanValidationBinder
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.Route
import com.vaadin.flow.shared.Registration
import com.vaadin.flow.spring.annotation.EnableVaadin
import com.vaadin.flow.theme.Theme
import groovy.transform.Canonical
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import java.time.LocalDate

@SpringBootApplication
@EnableVaadin
class V14gradleApplication {

    static void main(String[] args) {
        SpringApplication.run(V14gradleApplication, args)
    }

}

@Route("")
@Theme(themeFolder = 'app-theme')
@Viewport(Viewport.DEVICE_DIMENSIONS)
@BodySize(height = "100vh", width = "100vw")
class MainView extends Composite<Div> {
    MainView() {
        def pairField = new PairField()
        pairField.value = new Pair()
        content.tap {
            add(pairField)
            add(new Button("Show").tap {
                addClickListener {
                    pairField.binder.validate()
                    Notification.show(pairField.value.toString())
                }
            })
        }
    }
}

abstract class BindingForm<C extends Component, D> extends Composite<C> implements HasValueAndElement<AbstractField.ComponentValueChangeEvent<C, D>, D> {

    final Class<D> clazz
    final Binder<D> binder
    boolean readOnly = false

    abstract protected void buildForm()

    abstract protected void bindFields(Binder<D> binder)

    BindingForm(Class<D> clazz) {
        this.clazz = clazz
        this.binder = buildBinder()
        buildForm()
        bindFields(binder)
    }

    protected Binder<D> buildBinder() {
        new Binder<D>(clazz)
    }

    @Override
    void setValue(D value) {
        binder.setBean(value)
    }

    @Override
    D getValue() {
        binder.getBean()
    }

    @Override
    Registration addValueChangeListener(ValueChangeListener listener) {
        binder.addValueChangeListener(listener)
    }

    @Override
    D getEmptyValue() {
        clazz.getDeclaredConstructor().newInstance()
    }

    @Override
    void clear() {
        // TODO: binder.clearFields()
    }

    @Override
    void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly
        binder.setReadOnly(readOnly)
    }

    @Override
    void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        // TODO
    }

    @Override
    boolean isRequiredIndicatorVisible() {
        // TODO
        false
    }

}

@Canonical
class Person {
    @NotEmpty
    String firstName
    @NotEmpty
    String lastName
    @Email
    String email
    @Past
    @NotNull
    LocalDate dayOfBirth
}

class PersonField extends BindingForm<FormLayout, Person> {

    PersonField() {
        super(Person)
    }

    private TextField firstName
    private TextField lastName
    private EmailField email
    private DatePicker dayOfBirth

    @Override
    protected void buildForm() {
        content.tap {
            add(firstName = new TextField("First name"))
            add(lastName = new TextField("Last name"))
            add(email = new EmailField("Email"))
            add(dayOfBirth = new DatePicker("Date of birth"))
        }
    }

    @Override
    protected void bindFields(Binder<Person> binder) {
        binder.forField(firstName).bind("firstName")
        binder.forField(lastName).bind("lastName")
        binder.forField(email).bind("email")
        binder.forField(dayOfBirth).bind("dayOfBirth")
    }

    @Override
    protected Binder<Person> buildBinder() {
        new BeanValidationBinder<Person>(Person)
    }
}

@Canonical
class Pair {
    @NotNull
    Person a = new Person()

    @NotNull
    Person b = new Person()
}

class PairField extends BindingForm<FormLayout, Pair> {

    PairField() {
        super(Pair)
    }

    private PersonField a, b

    @Override
    protected void buildForm() {
        content.tap {
            add(a = new PersonField())
            add(b = new PersonField())
        }
    }

    @Override
    protected void bindFields(Binder<Pair> binder) {
        binder.forField(a).bind("a")
        binder.forField(b).bind("b")
    }

    @Override
    protected Binder<Pair> buildBinder() {
        new BeanValidationBinder<Pair>(Pair)
    }
}
