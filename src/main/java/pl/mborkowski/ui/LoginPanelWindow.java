package pl.mborkowski.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import pl.mborkowski.components.Articles;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.form.LoginForm;


public class LoginPanelWindow extends Window{

    private LoginForm loginForm;
    private Notification welcomeNotification;

    public LoginPanelWindow() {
        super(Constant.Label.LOGIN_WINDOW_TITLE);
        this.setResizable(false);
        this.setClosable(false);
        this.setModal(true);
        this.setIcon(FontAwesome.SIGN_IN);
        loginForm = new LoginForm();
        setContent(loginForm);
    }
}
