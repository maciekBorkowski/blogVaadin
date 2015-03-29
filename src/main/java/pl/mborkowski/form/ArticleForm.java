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
import pl.mborkowski.utils.FileReceiver;

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
    final Image image = new Image("Uploaded Image");
    private String filename;

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
        image.setVisible(false);
        formLayout.addComponent(text);

        Panel panel = new Panel();
        Layout panelContent = new VerticalLayout();
        Upload upload = this.prepareReceiverImage();
        panelContent.addComponents(upload, image);
        panel.setContent(panelContent);
        formLayout.addComponent(panel);

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
                editArticle.setAttachedFiles(filename);
            } else {
                article.setPublished(new Date());
                article.setLastUpdate(new Date());
                article.setText(text.getValue());
                article.setAttachedFiles(filename);
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

    private Upload prepareReceiverImage() {
        FileReceiver receiver = new FileReceiver(image);

        final Upload upload = new Upload(Constant.Label.UPLOAD, receiver);
        upload.setButtonCaption(Constant.Label.ADD);
        upload.addSucceededListener(receiver);

        final long UPLOAD_LIMIT = 1000000l;
        upload.addStartedListener(new Upload.StartedListener() {
            @Override
            public void uploadStarted(Upload.StartedEvent event) {
                filename = event.getFilename();
                if (!event.getMIMEType().contains("image")) {
                    Notification.show(Constant.Validation.NOT_IMAGE,
                            Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
                if (event.getContentLength() > UPLOAD_LIMIT) {
                    Notification.show(Constant.Validation.TOO_BIG,
                            Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
            }
        });

        upload.addProgressListener(new Upload.ProgressListener() {
            @Override
            public void updateProgress(long readBytes, long contentLength) {
                if (readBytes > UPLOAD_LIMIT) {
                    Notification.show(Constant.Validation.TOO_BIG,
                            Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
            }
        });
        return upload;
    }

}
