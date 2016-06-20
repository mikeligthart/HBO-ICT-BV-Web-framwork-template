package controllers;

import models.AdminUsers;

import models.Word;
import models.FrontPageText;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.data.validation.Constraints;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import javax.persistence.Column;
import java.util.List;

import static play.data.Form.form;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    @Inject
    FormFactory formFactory;


    /**
     * inloggevens voor de beheerder
     */
    private static final String gebruiker = "admin";
    private static final String wachtwoord = "1234";


    /**
     * een methode die de voorpagina renderd
     * @return render pagina index.scala.html
     */
    public Result index() {
        if (FrontPageText.findbyidfrontPageText(1) == null){
            FrontPageText enigsteFrontPageText = new FrontPageText();
            enigsteFrontPageText.setFrontText("nog geen gegevens ingevoerd");
            enigsteFrontPageText.setId(1);
            enigsteFrontPageText.save();
        }
        String frontPageText = FrontPageText.findbyidfrontPageText(1).getFrontText();
        return ok(index.render("digipedia", frontPageText));
    }

    /**
     * een methode die de woorden.scala renderd.
     * @return woorden pagina
     */
    public Result woorden() {
        List<Word> words = Word.getWords();
        return ok(woorden.render("digipedia", words));
    }

    public Result singleWord(int id){
        Word word = Word.getWordById(id);
        return ok(singleWord.render("digipedia",word));
    }


    /**
     *pagina om een woord toe te vroegen.
     * @return
     */
    public Result addWord() {
        if (session("username").equalsIgnoreCase("admin")) {
            List<Word> words = Word.getWords();
            Form<Word> addWordForm = formFactory.form(Word.class).bindFromRequest();
            if (addWordForm.hasErrors()) {
                return badRequest(addWord.render("digipedia", words, addWordForm));
            } else {
                Word newWord = addWordForm.get();
                newWord.save();
                return beheerPagina();
            }
        } else {
            return login();
        }
    }
    /**
     *genereren van de formulier om een woord toe te vroegen.
     * @return
     */
    public Result addWordForm() {
        if (session("username").equalsIgnoreCase("admin")) {
            List<Word> words = Word.getWords();
            Form<Word> addWordForm = formFactory.form(Word.class).bindFromRequest();
            return ok(addWord.render("digipedia", words, addWordForm));
        } else {
            return login();
        }
    }


    /**
     * methode die controleerd eerst of de gebruiker al niet is ingelogd doormiddel van een sessie.
     * als dat het geval is word hij gelijk doorgestuurd naar de admin gedeelte van de website.
     * zoniet, dan word de loginpagina gerenderd
     * @return
     */
    public Result login() {
        Form<AdminUsers> adminUsersForm = formFactory.form(AdminUsers.class).bindFromRequest();
        if (session().isEmpty()){
            return ok(login.render("Login", adminUsersForm));
        }else{
            if (!(session("username").equalsIgnoreCase("admin"))) {
                return ok(login.render("Login", adminUsersForm));
            } else {
                return beheerPagina();
            }
        }
    }

    /**
     * pagina bedoeld voor het verwijzen naar het inloggen. op deze pagina vindt je de stuk om woorden toe te vroegen, woorden te wijzigen of te verwijderen.
     * @return
     */

    public Result beheerPagina(){
        if (session("username").equalsIgnoreCase("admin")) {
            String fronttext = FrontPageText.findbyidfrontPageText(1).getFrontText();
            List<Word> words = Word.getWords();
            Logger.debug("beheerpagina methode : login klopt");
            return ok(beheerPagina.render("digipedia", words, fronttext));
        } else {
            Logger.debug("beheermethode: login klopt niet");
            return login();
        }
    }

    /**
     * methode om te controleren of de ingevoerde gegevens wel kloppen met de variable die zijn opgeslagen hierbovenaan.
     * @return
     */
    public Result authenticate() {
        Form<AdminUsers> loginForm = formFactory.form(AdminUsers.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(login.render("admin", loginForm));
        } else {
            if (loginForm.get().getUserName().equals(gebruiker) && loginForm.get().getPassword().equals(wachtwoord)) {
                session().clear();
                session("username", loginForm.get().userName);
                return beheerPagina();
            } else {
                return badRequest(login.render("admin", loginForm));
            }
        }
    }

    /**
     * zoals gewoonlijk word er eerst gecontroleerd of de gebruiker niet ingelogd is.
     * als dat het geval is, word gebruiker doorverwezen naar de formulier om een woord up te daten.
     * zoniet dan word gebruiker doorverwezen naar inlogpagina
     * @return
     */
    public Result updateWord(int position) {
        if (session("username").equalsIgnoreCase("admin")) {
            Form<Word> wordForm = formFactory.form(Word.class).bindFromRequest();
            Word word = Word.getWordById(position);
            return ok(updateWord.render("update", wordForm, word));
        } else {
            return login();
        }
    }

    /**
     * methode voor het afhandelen van de invoerveld op de webpagina.
     * als gebruiker ingelogt is word de pagetext object geupdate en anders word het doorverwezen naar loginpagina
     * @return
     */
    public Result updateFront() {
        if (session("username").equalsIgnoreCase("admin")) {
            Form<FrontPageText> frontPageTextForm = formFactory.form(FrontPageText.class).bindFromRequest();
            FrontPageText pageText = FrontPageText.findbyidfrontPageText(1);
            pageText.setFrontText(frontPageTextForm.get().getFrontText());
            pageText.update();
            return beheerPagina();
        } else {
            return login();
        }
    }

    /**
     * methode om een ingevoerde woord up te daten.
     * voor de zekerheid word er nog wel gekeken of gebruiker al in gelogd is.
     * @return
     */
    public Result databaseupdateWord() {

        if (session("username").equalsIgnoreCase("admin")) {
            Form<Word> wordForm = formFactory.form(Word.class).bindFromRequest();
            Word word = Word.getWordById((int) wordForm.get().getId());
            if (wordForm.hasErrors()) {
                return badRequest(updateWord.render("update", wordForm, word));
            } else {
                word.setWord(wordForm.get().getWord());
                word.setDescription(wordForm.get().getDescription());
                word.update();
                return beheerPagina();
            }
        } else {
            return login();
        }
    }

    /**
     * delete functie is bedoeld voor de administrator. deze functie word aangeroepen als gebruiker op de verwijder button klikt.
     *
     *
     * @param position
     * @return
     */
    public Result deleteWord(int position){
        Word word = Word.getWordById(position);
        word.delete();
        return beheerPagina();
    }


}
