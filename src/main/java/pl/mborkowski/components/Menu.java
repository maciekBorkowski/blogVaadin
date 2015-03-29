package pl.mborkowski.components;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.LoginPanelWindow;
import pl.mborkowski.ui.MainUI;

public class Menu extends CustomComponent{

    private MenuBar menubar;
    private VerticalLayout verticalLayout;

    public Menu(){
        verticalLayout = new VerticalLayout();
        this.setWidth(Constant.UI.DEFAULT_UI_SIZE);
        menubar = new MenuBar();
        menubar.setSizeFull();
        menubar.setStyleName("v-menubar-padding");
        menubar.setImmediate(true);
        addMenuItems();
        verticalLayout.addComponent(menubar);
        setCompositionRoot(verticalLayout);
        verticalLayout.setImmediate(true);
    }

    private void addMenuItems(){
        MenuBar.MenuItem articles = menubar.addItem(Constant.Label.ARTICLES, null, articlesClickEvent);
        articles.setIcon(FontAwesome.BOOK);
        if(Bloggers.isLoggedIn()) {
            MenuBar.MenuItem users = menubar.addItem(Constant.Label.USERS, null, usersClickEvent);
            users.setIcon(FontAwesome.USER);
            MenuBar.MenuItem logout = menubar.addItem(Constant.Label.LOGOUT, null, logoutEvent);
            logout.setIcon(FontAwesome.SIGN_OUT);
            logout.setStyleName("right");
        } else {
            MenuBar.MenuItem logIn = menubar.addItem(Constant.Label.LOGIN_TEXT, null, loginClickEvent);
            logIn.setIcon(FontAwesome.CHAIN);
            logIn.setStyleName("right");
        }
    }

    private MenuBar.Command articlesClickEvent = selectedItem -> {
        MainUI mainContent = (MainUI)this.getParent().getParent();
        mainContent.setPageContent(new Articles());
    };

    private MenuBar.Command loginClickEvent = selectedItem -> {
        MainUI mainContent = (MainUI)this.getParent().getParent();
        LoginPanelWindow loginPanelWindow = new LoginPanelWindow();
        mainContent.addWindow(loginPanelWindow);

    };

    private MenuBar.Command usersClickEvent = selectedItem -> {
        MainUI mainContent = (MainUI)this.getParent().getParent();
        mainContent.setPageContent(new Bloggers());
    };

    private MenuBar.Command logoutEvent = selectedItem -> {
        MainUI.getCurrentSession().removeAttribute("user");
        MainUI root = (MainUI)this.getParent().getParent();

        root.setPageContent(new Articles());
        root.getPage().reload();
    };
}