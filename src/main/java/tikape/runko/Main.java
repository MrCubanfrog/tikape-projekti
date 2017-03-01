package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Session;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KayttajaDao;
import tikape.runko.domain.Kayttaja;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KayttajaDao KayttajaDao = new KayttajaDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        
        get("/kirjautuminen", (req, res) -> {
            HashMap map = new HashMap<>();
            
            return new ModelAndView(map, "kirjautuminen");
        }, new ThymeleafTemplateEngine());
        
        post("/login", (req, res) -> {
            String tunnus = req.queryParams("tunnus");
            String salasana = req.queryParams("salasana");

            Kayttaja user = KayttajaDao.findByUsernameAndPassword(tunnus, salasana);

            if (user == null) {
                res.redirect("/");
                return "";
            }

            req.session(true).attribute("user", user);
            res.redirect("/s/users/" + user.getId());
            return "";
        });
        
        before((req, res) -> {
            if(!req.url().contains("/s/")) {
                return;
            }
            
            Session sess = req.session();
            if (sess.attribute("user") == null) {
                sess.invalidate();
                res.redirect("/");
            }
        });
    }
}
