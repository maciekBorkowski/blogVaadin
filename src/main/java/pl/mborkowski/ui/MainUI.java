package pl.mborkowski.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.*;
import pl.mborkowski.Data;
import pl.mborkowski.components.Articles;
import pl.mborkowski.components.Menu;

import javax.servlet.annotation.WebServlet;


@Theme("mytheme")
@Widgetset("pl.mborkowski.MyAppWidgetset")
public class MainUI extends UI {

    private VerticalLayout layout;
    private VerticalLayout mainContent;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Data.initData();
        layout = new VerticalLayout();
        layout.setImmediate(true);
        mainContent = new VerticalLayout();
        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        mainContent.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        mainContent.setImmediate(true);
        Menu menu = new Menu();
        layout.addComponent(menu);

        Articles articles = new Articles();
        mainContent.addComponent(articles);
        layout.addComponent(mainContent);

        setContent(layout);
    }

    public static WrappedSession getCurrentSession(){
        return VaadinService.getCurrentRequest().getWrappedSession();
    }

    public void setMainContent(){
        setContent(layout);
    }

    public void setPageContent(Component component){
        mainContent.removeAllComponents();
        mainContent.addComponent(component);
    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MainUI.class)
    public static class Servlet extends VaadinServlet {
    }
}
