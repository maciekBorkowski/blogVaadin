package pl.mborkowski.bean;

import org.hibernate.validator.constraints.NotEmpty;
import pl.mborkowski.constant.Constant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by borek on 28.03.15.
 */

public class Blogger {

    public Blogger () {}

    public Blogger (Blogger blogger) {
        this.setName(blogger.getName());
        this.setLogin(blogger.getLogin());
        this.setPassword(blogger.getPassword());
        this.setLogged(blogger.getLogged());
        this.setRegistered(blogger.getRegistered());
    }

    @Size(min=3, message = Constant.Validation.SIZE)
    private String name = "";
    @Size(min=3, message = Constant.Validation.SIZE)
    private String login = "";
    @Size(min=3, message = Constant.Validation.SIZE)
    private String password = "";
    private Date registered;
    private boolean logged;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public boolean getLogged() {
        return logged;
    }

    public void setLogged(boolean isLogged) {
        this.logged = isLogged;
    }
}
