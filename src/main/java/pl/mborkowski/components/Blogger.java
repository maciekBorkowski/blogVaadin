package pl.mborkowski.components;

import com.vaadin.ui.*;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.form.ArticleForm;
import pl.mborkowski.form.BloggerForm;

/**
 * Created by borek on 29.03.15.
 */
public class Blogger extends CustomComponent {

    private VerticalLayout layout;
    private HorizontalLayout menu;
    private Button save, back;
    private BloggerForm bloggerForm;

    public Blogger(){
        layout  = new VerticalLayout();
        setWidth(Constant.UI.DEFAULT_UI_SIZE);
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        bloggerForm = new BloggerForm();
        layout.addComponent(bloggerForm);
        setCompositionRoot(layout);

    }

    public Blogger(pl.mborkowski.bean.Blogger blogger){
        this();
        layout.removeAllComponents();
        bloggerForm = new BloggerForm(blogger);
        layout.addComponent(bloggerForm);
    }
}
