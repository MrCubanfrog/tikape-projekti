package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Session;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KayttajaDao;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Kayttaja;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:src/main/resources/foorumi.db");
        database.setDebugMode(true);

        KayttajaDao KayttajaDao = new KayttajaDao(database);
        AlueDao alueDao = new AlueDao(database);
        

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        
        get("/kirjautuminen", (req, res) -> {
            HashMap map = new HashMap<>();
            
            return new ModelAndView(map, "kirjautuminen");
        }, new ThymeleafTemplateEngine());
        
        get("/kirjautuminen2", (req, res) -> {
            HashMap map = new HashMap<>();
            
            return new ModelAndView(map, "kirjautuminen2");
        }, new ThymeleafTemplateEngine());
        
        post("/login", (req, res) -> {
            String tunnus = req.queryParams("tunnus");
            String salasana = req.queryParams("salasana");

            Kayttaja user = KayttajaDao.findByUsernameAndPassword(tunnus, salasana);

            if (user == null) {
                res.redirect("/kirjautuminen2");
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
        
        get("/s/users/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kayttaja", KayttajaDao.findOne(Integer.parseInt(req.params(":id"))));
            map.put("alueet", alueDao.findAll());

            // get 10 chat messages and add them to the map
            // NB! use "tsats" as the name for the messages
            
            
            return new ModelAndView(map, "kirjautunut");
        }, new ThymeleafTemplateEngine());
        
//        get("/s/:alue", (req, res) -> {
//            HashMap map = new HashMap<>();
//            int alueID = Integer.parseInt(req.params(":alue"));
//            List<Alue> keskustelu = new ArrayList<>();
//            //tänne pitää laittaa tietyn alueen keskustelut, eli KeskusteluDao.findAll(id)
//            map.put(map, keskustelu);
//            
//            
//            return new ModelAndView(map, String.valueOf(alueID));
//        }, new ThymeleafTemplateEngine());
        
        get("/rekisteroidy", (req, res) -> {
            HashMap map = new HashMap<>();
            
            return new ModelAndView(map, "rekisteroidy");
        }, new ThymeleafTemplateEngine());
        
        post("/luotunnus", (req, res) -> {
            String nimi = req.queryParams("nimi");
            String tunnus = req.queryParams("tunnus");
            String salasana = req.queryParams("salasana");
            
            KayttajaDao.luoKayttaja(nimi, tunnus, salasana);
            res.redirect("/");
            return "";
            
        });
        
//        get("/alue/:id", (req, res) -> {
//            
//        });
//        
//        get("/s/alue/:id", (req, res) -> {
//            
//        });
    }
}
