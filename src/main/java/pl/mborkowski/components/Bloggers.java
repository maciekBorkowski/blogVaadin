package pl.mborkowski.components;


import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinService;
import com.vaadin.server.WrappedSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import pl.mborkowski.Data;
import pl.mborkowski.bean.Blogger;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.AddUserWindow;

public class Bloggers extends CustomComponent{

    private VerticalLayout layout;
    private HorizontalLayout menu;
    private Table bloggers;
    private BeanItemContainer beanItemContainer;
    private Button add;

    public Bloggers(){
        layout  = new VerticalLayout();
        setWidth(Constant.UI.DEFAULT_UI_SIZE);
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);

        prepareMenu();
        layout.addComponent(menu);

        prepareUsersTable();
        layout.addComponent(bloggers);

        setCompositionRoot(layout);
    }

    private void setButtonsStyle(){
        add.setStyleName("margin");
    }

    private void prepareMenu(){
        menu = new HorizontalLayout();
        menu.setMargin(new MarginInfo(false, true, false, false));
        add = new Button(FontAwesome.PLUS);

        add.addClickListener(clickEvent -> {
            AddUserWindow addUserWindow = new AddUserWindow();
            getUI().addWindow(addUserWindow);
        });
        menu.addComponents(add);
        setButtonsStyle();
    }

    private void prepareUsersTable(){
        bloggers = new Table();
        beanItemContainer = new BeanItemContainer(Blogger.class);
        bloggers.setContainerDataSource(beanItemContainer);
        beanItemContainer.addAll(Data.users);

        bloggers.setColumnHeader("name", Constant.Label.NAME);
        bloggers.setColumnHeader("login", Constant.Label.LOGIN);
        bloggers.setColumnHeader("registered", Constant.Label.REGISTER_DATE);
        bloggers.setColumnHeader("logged", Constant.Label.IS_LOGGED);


        bloggers.setVisibleColumns("name", "login", "registered", "logged");
        bloggers.setSizeFull();
        bloggers.setImmediate(true);

    }

    public static boolean isLoggedIn(){
        WrappedSession currentSession = VaadinService.getCurrentRequest().getWrappedSession();
        return currentSession.getAttribute("user") != null;
    }


}
