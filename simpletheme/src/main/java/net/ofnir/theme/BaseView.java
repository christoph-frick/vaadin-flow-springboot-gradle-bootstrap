package net.ofnir.theme;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;

@CssImport("./src/theme.css")
public class BaseView extends Div {
    public BaseView() {
        getElement().getThemeList().add("my-theme");
    }
}
