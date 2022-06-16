package net.ofnir.v14gradle

import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.EnableVaadin
import com.vaadin.flow.theme.Theme
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Service

import java.time.DayOfWeek
import java.time.Month
import java.time.chrono.IsoChronology
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.time.temporal.WeekFields

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
        def datePicker = new DatePicker().tap {
            setI18n(new DatePicker.DatePickerI18n().tap {
                def locale = Locale.GERMAN

                // date formats
                setDateFormats(
                    DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                        FormatStyle.MEDIUM,
                        null,
                        IsoChronology.INSTANCE,
                        locale
                    ).replace('y', 'yyyy'),
                    // this would be useful in theory, but is not in practice, because the first format always wins
                    DateTimeFormatterBuilder.getLocalizedDateTimePattern(
                        FormatStyle.SHORT,
                        null,
                        IsoChronology.INSTANCE,
                        locale
                    )
                )

                // days
                def daysOfWeek = [DayOfWeek.SUNDAY] + DayOfWeek.values().toList() // sunday first, because ISO is a joke for some
                setWeekdays(daysOfWeek*.getDisplayName(TextStyle.FULL, locale))
                setWeekdaysShort(daysOfWeek*.getDisplayName(TextStyle.SHORT, locale))
                setFirstDayOfWeek(WeekFields.of(locale).firstDayOfWeek.value % 7) // this works because, because value is + 1

                // months
                setMonthNames(Month.values()*.getDisplayName(TextStyle.FULL, locale))

                // words (use your translation backend here)
                setWeek("Woche")
                setToday("Heute")
                setCancel("Abbrechen")
            })
            addValueChangeListener({
                Notification.show(it.value.toString())
            })
        }
        content.add(datePicker)
    }
}
