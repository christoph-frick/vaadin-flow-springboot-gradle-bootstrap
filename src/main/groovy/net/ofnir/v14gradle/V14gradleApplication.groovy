package net.ofnir.v14gradle

import com.github.appreciated.app.layout.addons.notification.DefaultNotificationHolder
import com.github.appreciated.app.layout.addons.notification.component.NotificationButton
import com.github.appreciated.app.layout.addons.notification.entity.DefaultNotification
import com.github.appreciated.app.layout.component.appbar.AppBarBuilder
import com.github.appreciated.app.layout.component.applayout.LeftLayouts
import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder
import com.github.appreciated.app.layout.component.menu.left.builder.LeftAppMenuBuilder
import com.github.appreciated.app.layout.component.menu.left.builder.LeftSubMenuBuilder
import com.github.appreciated.app.layout.component.menu.left.items.LeftClickableItem
import com.github.appreciated.app.layout.component.menu.left.items.LeftHeaderItem
import com.github.appreciated.app.layout.component.menu.left.items.LeftNavigationItem
import com.github.appreciated.app.layout.component.router.AppLayoutRouterLayout
import com.github.appreciated.app.layout.entity.DefaultBadgeHolder
import com.github.appreciated.app.layout.entity.Section
import com.vaadin.flow.component.Composite
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.EnableVaadin
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableVaadin
class V14gradleApplication {

    static void main(String[] args) {
        SpringApplication.run(V14gradleApplication, args)
    }

}

@Theme(value = Lumo)
class MyAppLayoutRouterLayout extends AppLayoutRouterLayout<LeftLayouts.LeftResponsiveHybrid> {
    private DefaultNotificationHolder notifications;
    private DefaultBadgeHolder badge;

    public MyAppLayoutRouterLayout() {
        notifications = new DefaultNotificationHolder(newStatus -> {
        });
        badge = new DefaultBadgeHolder(5);
        for (int i = 1; i < 6; i++) {
            notifications.add(new DefaultNotification("Test title" + i, "A rather long test description ..............." + i));
        }
        LeftNavigationItem menuEntry = new LeftNavigationItem("Menu", VaadinIcon.MENU.create(), MainView);
        badge.bind(menuEntry.getBadge());
        init(AppLayoutBuilder.get(LeftLayouts.LeftResponsiveHybrid.class)
                .withTitle("App Layout")
                .withAppBar(AppBarBuilder.get()
                        .add(new NotificationButton<>(VaadinIcon.BELL, notifications))
                        .build())
                .withAppMenu(LeftAppMenuBuilder.get()
                        .addToSection(Section.HEADER,
                                new LeftHeaderItem("Menu-Header", "APP_LAYOUT_VERSION", "path/to/your/image"),
                                new LeftClickableItem("Clickable Entry", VaadinIcon.COG.create(), clickEvent -> Notification.show("onClick ..."))
                        )
                        .add(new LeftNavigationItem("Home", VaadinIcon.HOME.create(), MainView),
                                LeftSubMenuBuilder.get("My Submenu", VaadinIcon.PLUS.create())
                                        .add(LeftSubMenuBuilder.get("My Submenu", VaadinIcon.PLUS.create())
                                                .add(new LeftNavigationItem("Charts", VaadinIcon.SPLINE_CHART.create(), MainView),
                                                        new LeftNavigationItem("Contact", VaadinIcon.CONNECT.create(), MainView),
                                                        new LeftNavigationItem("More", VaadinIcon.COG.create(), MainView))
                                                .build(),
                                                new LeftNavigationItem("Contact1", VaadinIcon.CONNECT.create(), MainView),
                                                new LeftNavigationItem("More1", VaadinIcon.COG.create(), MainView))
                                        .build(),
                                menuEntry)
                        .addToSection(Section.FOOTER, new LeftClickableItem("Clickable Entry", VaadinIcon.COG.create(), clickEvent -> Notification.show("onClick ...")))
                        .build())
                .build());
    }
}

@Route(value = "", layout = MyAppLayoutRouterLayout)
class MainView extends Composite<Div> {
    MainView() {
        content.add(new H1("Hello"))
    }
}
