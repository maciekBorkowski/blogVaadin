package pl.mborkowski;

import pl.mborkowski.bean.Article;
import pl.mborkowski.bean.Blogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Data {
    public static List<Article> articles = new ArrayList<>();
    public static List<Blogger> users = new ArrayList<>();
    private static boolean dataLoaded = false;


    public static void initData(){
        if(dataLoaded){
            return;
        }
        Blogger user = new Blogger();
        user.setLogin("blogger");
        user.setName("Bloger");
        user.setPassword("blog");
        user.setRegistered(new Date());
        user.setLogged(true);

        users.add(user);

        Article article = new Article();
        article.setTitle("testowy wpis na blogu");
        article.setText("jakiś tekst");
        article.setLastUpdate(new Date());
        article.setPublished(new Date());

        articles.add(article);

        dataLoaded = true;
    }
}
