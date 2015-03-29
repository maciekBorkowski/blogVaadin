package pl.mborkowski.form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import pl.mborkowski.Data;
import pl.mborkowski.bean.Blogger;
import pl.mborkowski.components.Articles;
import pl.mborkowski.components.Bloggers;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.LoginPanelWindow;
import pl.mborkowski.ui.MainUI;

public class LoginForm extends CustomComponent {

    private TextField login;
    private PasswordField password;
    private FormLayout formLayout;
    private FieldGroup binder;
    private BeanItem<Blogger> item;
    private Blogger user;
    private Notification errorNotification;
    private Button submit, cancel;

    public LoginForm(){
        formLayout = new FormLayout();
        user = new Blogger();
        item = new BeanItem<> (user);
        binder = new BeanFieldGroup<>(Blogger.class);
        binder.setItemDataSource(item);
        binder.setBuffered(true);
        this.addFormFieldsToLayout();
        formLayout.setMargin(true);
        this.setCompositionRoot(formLayout);
    }


    private void addFormFieldsToLayout(){
        login = binder.buildAndBind(Constant.Label.LOGIN, "login", TextField.class);
        password = binder.buildAndBind(Constant.Label.PASSWORD, "password", PasswordField.class);

        submit = new Button(Constant.Label.LOGIN_BUTTON,  (Button.ClickListener) (clickEvent) -> {

            try {
                binder.commit();

                if(!correctCredentials()){
                    showLoginErrorMessage();
                    return;
                }
                LoginPanelWindow parent = (LoginPanelWindow)getParent();

                MainUI root = (MainUI)parent.getParent();

                parent.close();

                root.setPageContent(new Articles());
                root.getPage().reload();
                MainUI.getCurrentSession().setAttribute("user", user.getLogin());

                showWelcome();

            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
        });

        cancel = new Button(Constant.Label.CANCEL,  (Button.ClickListener) (clickEvent) -> {
                LoginPanelWindow parent = (LoginPanelWindow)getParent();
                parent.close();
        });

        HorizontalLayout buttons = new HorizontalLayout();
        submit.setStyleName("left");
        cancel.setStyleName("button_cancel");

        buttons.addComponent(submit);
        buttons.addComponent(cancel);
        formLayout.addComponents(login, password, buttons);
    }

    private void showLoginErrorMessage(){
        errorNotification = new Notification(Constant.Validation.INVALID_CREDITALS, Notification.Type.ERROR_MESSAGE);
        errorNotification.setDelayMsec(Constant.Notification.DELAY);
        errorNotification.show(Page.getCurrent());
    }

    private boolean correctCredentials(){
        for(Blogger current : Data.users){
            if(user.getLogin().equals(current.getLogin()) && user.getPassword().equals(current.getPassword())){
                return true;
            }
        }
        return false;
    }

    private void showWelcome() {
        Notification welcomeNotification = new Notification(Constant.Label.LOGIN_SUCCESS, Notification.Type.HUMANIZED_MESSAGE);
        welcomeNotification.setDelayMsec(Constant.Notification.DELAY);
        welcomeNotification.show(Page.getCurrent());
    }
}
