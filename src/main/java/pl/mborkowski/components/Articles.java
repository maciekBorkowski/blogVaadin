package pl.mborkowski.components;

import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.vaadin.dialogs.ConfirmDialog;
import pl.mborkowski.Data;
import pl.mborkowski.constant.Constant;
import pl.mborkowski.ui.MainUI;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by borek on 28.03.15.
 */
public class Articles extends CustomComponent {

    private VerticalLayout layout;
    private HorizontalLayout menu;
    private Button add;
    private VerticalLayout articles;
    private Image image;

    public Articles(){
        layout  = new VerticalLayout();
        setWidth(Constant.UI.DEFAULT_UI_SIZE);
        layout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
        if(Bloggers.isLoggedIn()) {
            addArticleShow();
            layout.addComponent(menu);
        }

        articlesShow();
        layout.addComponent(articles);

        setCompositionRoot(layout);

    }


    private void addArticleShow(){
        menu = new HorizontalLayout();
        menu.setMargin(new MarginInfo(false, true, false, false));
        add = new Button(FontAwesome.PLUS);
        add.setStyleName("margin");

        add.addClickListener(clickEvent -> {
            MainUI root = (MainUI)this.getParent().getParent().getParent();

            root.setPageContent(new Article());
        });

        menu.addComponents(add);
    }

    private void articlesShow(){

        articles = new VerticalLayout();
        articles.setStyleName("margin");
        articles.addStyleName("outlined");
        articles.setHeight(100.0f, Unit.PERCENTAGE);
        articles.setVisible(true);
        
        for (pl.mborkowski.bean.Article article : Data.articles) {
            image = new Image();
            Panel panel = new Panel(article.getTitle() + "\n\n" + "Artykuł dodano: " + article.getPublished());

            Label content = new Label(article.getText());
            content.setStyleName("articles");
            content.setIcon(FontAwesome.CLOUD);
            content.setContentMode(ContentMode.HTML);
            panel.setContent(content);
            if(article.getAttachedFiles() != null){
                String filename = "/tmp/uploads/" + article.getAttachedFiles();
                File file = new File(filename);
                FileResource fileResource = new FileResource(file);
                image.setSource(fileResource);
                image.setHeight("200");
                articles.addComponent(image);
            }
            articles.addComponent(panel);

            if(Bloggers.isLoggedIn()) {
                HorizontalLayout manager = new HorizontalLayout();
                Button edit = new Button(FontAwesome.EDIT);
                edit.addClickListener(clickEvent -> {
                    MainUI root = (MainUI)this.getParent().getParent().getParent();

                    root.setPageContent(new Article(article));
                });
                manager.addComponent(edit);

                Button remove = new Button(FontAwesome.MINUS);
                remove.addClickListener(clickEvent -> {
                    MainUI root = (MainUI)this.getParent().getParent().getParent();
                    ConfirmDialog.show(root, Constant.Label.CONFIRM,
                        new ConfirmDialog.Listener() {
                            public void onClose(ConfirmDialog dialog) {
                                if (dialog.isConfirmed()) {
                                    Data.articles.remove(article);
                                    root.getPage().reload();
                                }
                            }
                    });
                });
                manager.addComponent(remove);
                manager.setStyleName("margin");
                articles.addComponent(manager);
            }

        }


    }

}
