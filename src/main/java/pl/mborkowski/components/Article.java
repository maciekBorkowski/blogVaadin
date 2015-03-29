package pl.mborkowski.components;

import com.vaadin.ui.*;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.form.ArticleForm;

/**
 * Created by borek on 28.03.15.
 */
public class Article extends CustomComponent {

    private VerticalLayout layout;
    private ArticleForm articleForm;

    public Article(){
        layout  = new VerticalLayout();
        setWidth(Constant.UI.DEFAULT_UI_SIZE);
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        articleForm = new ArticleForm();
        layout.addComponent(articleForm);
        setCompositionRoot(layout);

    }

    public Article(pl.mborkowski.bean.Article article){
        this();
        layout.removeAllComponents();
        articleForm = new ArticleForm(article);
        layout.addComponent(articleForm);
    }

}
