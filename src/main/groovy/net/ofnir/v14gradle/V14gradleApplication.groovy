package net.ofnir.v14gradle

import com.vaadin.flow.component.AbstractField
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.HasValue
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
import com.vaadin.flow.data.binder.BinderValidationStatus
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
        def pairField = new PairField().tap{
            value = new Pair()
            addValueChangeListener{
                Notification.show("value change from ${it.oldValue} to ${it.value} from ${it.fromClient ? 'client' : 'system'}")
            }
        }

        content.tap {
            add(pairField)
            add(new Button("Show").tap {
                addClickListener {
                    pairField.validate()
                    Notification.show(pairField.value.toString())
                }
            })
        }
    }
}

trait BinderField<D> implements HasValue<BinderFieldValueChangeEvent<D>, D> {

    private Binder<D> binder

    private boolean readOnly = false

    private D oldValue

    abstract Class<D> getClazz()

    abstract void bindFields(Binder<D> binder)

    void setupBindings() {
        this.binder = buildBinder()
        bindFields(binder)
    }

    Binder<D> buildBinder() {
        new Binder<D>(clazz)
    }

    @Override
    void setValue(D value) {
        binder.readBean(value)
        oldValue = value
    }

    @Override
    D getValue() {
        def newValue = emptyValue
        binder.writeBeanAsDraft(newValue)
        newValue
    }

    @Override
    Registration addValueChangeListener(ValueChangeListener listener) {
        binder.addValueChangeListener{
            listener.valueChanged(
                // FIXME: this is not the proper old value; this is the initially set value
                new BinderFieldValueChangeEvent<D>(this, value, oldValue, it.fromClient)
            )
        }
    }

    @Override
    D getEmptyValue() {
        clazz.getDeclaredConstructor().newInstance()
    }

    @Override
    void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly
        binder.setReadOnly(readOnly)
    }

    @Override
    boolean isReadOnly() {
        this.readOnly
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

    BinderValidationStatus<D> validate() {
        binder.validate()
    }

    @Canonical
    static class BinderFieldValueChangeEvent<D> implements ValueChangeEvent<D> {
        final HasValue<?,D> hasValue
        final D value
        final D oldValue
        final boolean fromClient
    }

}

trait BeanValidationBinderField<D> extends BinderField<D> {
    @Override
    Binder<D> buildBinder() {
        new BeanValidationBinder<D>(clazz)
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

class PersonField extends FormLayout implements BeanValidationBinderField<Person> {

    final Class<Person> clazz = Person

    PersonField() {
        add(firstName = new TextField("First name"))
        add(lastName = new TextField("Last name"))
        add(email = new EmailField("Email"))
        add(dayOfBirth = new DatePicker("Date of birth"))
        setupBindings()
    }

    private TextField firstName
    private TextField lastName
    private EmailField email
    private DatePicker dayOfBirth

    @Override
    void bindFields(Binder<Person> binder) {
        binder.forField(firstName).bind("firstName")
        binder.forField(lastName).bind("lastName")
        binder.forField(email).bind("email")
        binder.forField(dayOfBirth).bind("dayOfBirth")
    }

}

@Canonical
class Pair {
    @NotNull
    Person a

    @NotNull
    Person b
}

class PairField extends FormLayout implements BeanValidationBinderField<Pair> {

    final Class<Pair> clazz = Pair

    PairField() {
        add(a = new PersonField())
        add(b = new PersonField())
        setupBindings()
    }

    private PersonField a, b

    @Override
    void bindFields(Binder<Pair> binder) {
        binder.forField(a).bind("a")
        binder.forField(b).bind("b")
    }
}
