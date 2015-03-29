package pl.mborkowski.form;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import pl.mborkowski.Data;
import pl.mborkowski.bean.Article;
import pl.mborkowski.components.Articles;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.MainUI;

import javax.xml.soap.Text;
import java.util.Date;

/**
 * Created by borek on 29.03.15.
 */
public class ArticleForm extends CustomComponent {

    private FormLayout formLayout;
    private TextArea text;
    private FieldGroup binder;
    private BeanItem<Article> item;
    private Article article;
    private Article oldArticle;

    public ArticleForm(){
        article = new Article();
        formLayout = new FormLayout();
        customizeFormValidation(article);
        addFormFieldsToLayout();
    }

    public ArticleForm(Article article){
        oldArticle = article;
        this.article = new Article(article);
        formLayout = new FormLayout();
        customizeFormValidation(article);
        addFormFieldsToLayout();
    }

    private void addFormFieldsToLayout(){
        formLayout.addComponents(binder.buildAndBind(Constant.Label.TITLE, "title"));
        text = new TextArea(Constant.Label.TEXT);
        text.setSizeFull();
        text.setValue(article.getText());
        formLayout.addComponent(text);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponent(back);
        buttons.addComponent(save);
        formLayout.addComponent(buttons);
        formLayout.setMargin(true);
        setCompositionRoot(formLayout);
    }

    private void customizeFormValidation(Article article){
        item = new BeanItem<>(article);
        binder = new BeanFieldGroup<>(Article.class);
        binder.setItemDataSource(item);
        binder.setBuffered(true);
    }

    private Button save = new Button(Constant.Label.SAVE, (Button.ClickListener) (clickEvent) -> {
        try {
            binder.commit();
            if(this.article.getPublished() != null){
                Article editArticle = Data.articles.get(Data.articles.indexOf(oldArticle));
                editArticle.setText(text.getValue());
                editArticle.setTitle(item.getBean().getTitle());
            } else {
                article.setPublished(new Date());
                article.setLastUpdate(new Date());
                article.setText(text.getValue());
                Data.articles.add(article);
            }
            MainUI root = (MainUI)this.getParent().getParent().getParent().getParent().getParent();
            root.setPageContent(new Articles());
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
    });

    private Button back = new Button(Constant.Label.BACK, (Button.ClickListener) (clickEvent) -> {
        MainUI root = (MainUI)this.getParent().getParent().getParent().getParent().getParent();
        root.setPageContent(new Articles());
    });
}
