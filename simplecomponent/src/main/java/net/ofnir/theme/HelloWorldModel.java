package net.ofnir.theme;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface HelloWorldModel extends TemplateModel {

    String getUserInput();

    void setGreeting(String userInput);
}
