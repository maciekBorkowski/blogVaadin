package pl.mborkowski.form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import pl.mborkowski.Data;
import pl.mborkowski.bean.Blogger;
import pl.mborkowski.components.Bloggers;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.MainUI;

import java.util.Date;

/**
 * Created by borek on 29.03.15.
 */
public class BloggerForm extends CustomComponent {

    private FormLayout formLayout;
    private FieldGroup binder;
    private BeanItem<Blogger> item;
    private Blogger blogger;
    private Blogger oldBlogger;

    public BloggerForm(){
        blogger = new Blogger();
        formLayout = new FormLayout();
        customizeFormValidation(blogger);
        addFormFieldsToLayout();
    }

    public BloggerForm(Blogger blogger){
        oldBlogger = blogger;
        this.blogger = new Blogger(blogger);
        formLayout = new FormLayout();
        customizeFormValidation(blogger);
        addFormFieldsToLayout();
    }

    private void addFormFieldsToLayout(){
        formLayout.addComponents(binder.buildAndBind(Constant.Label.NAME, "name"),
                binder.buildAndBind(Constant.Label.LOGIN, "login"),
                binder.buildAndBind(Constant.Label.PASSWORD, "password", PasswordField.class)
        );
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(back);
        buttons.addComponent(save);
        formLayout.addComponent(buttons);
        formLayout.setMargin(true);
        setCompositionRoot(formLayout);
    }

    private void customizeFormValidation(Blogger blogger){
        item = new BeanItem<>(blogger);
        binder = new BeanFieldGroup<>(Blogger.class);
        binder.setItemDataSource(item);
        binder.setBuffered(true);
    }

    private Button save = new Button(Constant.Label.SAVE, (Button.ClickListener) (clickEvent) -> {
        try {
            binder.commit();
            if(this.blogger.getRegistered() != null){
                Blogger editBlogger = Data.users.get(Data.users.indexOf(oldBlogger));
                editBlogger.setName(item.getBean().getName());
                editBlogger.setPassword(item.getBean().getPassword());
                editBlogger.setLogin(item.getBean().getLogin());
            } else {
                blogger.setRegistered(new Date());
                Data.users.add(blogger);
            }
            MainUI root = (MainUI)this.getParent().getParent().getParent().getParent().getParent();
            root.setPageContent(new Bloggers());
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
    });

    private Button back = new Button(Constant.Label.BACK, (Button.ClickListener) (clickEvent) -> {
        MainUI root = (MainUI)this.getParent().getParent().getParent().getParent().getParent();
        root.setPageContent(new Bloggers());
    });
}
