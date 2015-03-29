package pl.mborkowski.bean;

import org.hibernate.validator.constraints.NotEmpty;
import pl.mborkowski.constant.Constant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by borek on 28.03.15.
 */
public class Article {

    public Article() {
    }

    public Article(Article article) {
        this.setText(article.getText());
        this.setTitle(article.getTitle());
        this.setPublished(article.getPublished());
        this.setLastUpdate(article.getLastUpdate());
    }
    @Size(min=5, message = Constant.Validation.SIZE)
    private String title = "";
    @Size(min=5, message = Constant.Validation.SIZE)
    private String text = "";
    private Date published;
    private Date lastUpdate;
    private List<URI> attachedFiles;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<URI> getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(List<URI> attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public boolean equals(Article obj) {
        return this.getTitle().equals(obj.getTitle());
    }
}
