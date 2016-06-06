package controllers;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import models.AdminUsers;
import models.User;
import models.Word;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    @Inject
    FormFactory formFactory;
    @Inject
    FormFactory testformFactory;

    private static final String gebruiker = "admin";
    private static final String wachtwoord = "1234";


    /**
     * een methode die de voorpagina renderd
     * @return render pagina index.scala.html
     */
    public Result index() {

        List<Word> word = Word.getFiveMostCounted();

        return ok(index.render("digipedia", word));
    }

    /**
     * een methode die de woorden.scala renderd.
     * @return woorden pagina
     */
    public Result woorden() {
        List<Word> words = Word.getWords();
        return ok(woorden.render("digipedia", words));
    }

    /**
     *
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
     *
     * @return
     */
    public Result addWord() {
        if (session("username").equalsIgnoreCase("admin")) {
            List<Word> words = Word.getWords();
            Form<Word> addWordForm = formFactory.form(Word.class).bindFromRequest();
            if (addWordForm.hasErrors()) {
                return badRequest(addWord.render("digipedia", words, addWordForm));
            } else {
                Logger.debug("wordForm " + addWordForm);
                Word newWord = addWordForm.get();
                newWord.save();
                return addWordForm();
            }
        } else {
            return login();
        }

    }

    /**
     * niet verwijderen kan van pas komen voor update
     * @param message
     * @return
     */
    public Result test(String message) {
        return ok(test.render("Test", message));
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
                return addWord();
            }

        }


    }

    /**
     * methode om te controleren of de ingevoerde gegevens wel kloppen met de variable die zijn opgeslagen hierbovenaan.
     * @return
     */
    public Result authenticate() {
        Logger.debug("ik ben nu in methode authenticatie");
        Form<AdminUsers> loginForm = formFactory.form(AdminUsers.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            Logger.debug("controleer of er haserrors zijn");
            return badRequest(login.render("admin", loginForm));
        } else {
            Logger.debug("er zijn geen haserrors");
            Logger.debug("nu controle of paswoord en gebruikersnaam gelijk is aan opgegeven");
            if (loginForm.get().getUserName().equals(gebruiker) && loginForm.get().getPassword().equals(wachtwoord)) {
                Logger.debug("gebruikersnaam en wachtwoord komen overeen");
                session().clear();
                Logger.debug("sessie word opgeruimd");
                session("username", loginForm.get().userName);
                Logger.debug("gebruiker toegevroegd aan sessie");
                return addWordForm();
            } else {
                Logger.debug("wachtwoord en gebruikernaam komt niet overeen");
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
     * methode om een ingevoerde woord up te daten.
     * voor de zekerheid word er nog wel gekeken of gebruiker al in gelogd is.
     * @return
     */
    public Result databaseupdateWord() {

        if (session("username").equalsIgnoreCase("admin")) {
            Form<Word> wordForm = formFactory.form(Word.class).bindFromRequest();

            Word word = Word.getWordById((int) wordForm.get().getId());
            Logger.debug("ik ben nu in de databaseupdatword");

            if (wordForm.hasErrors()) {
                Logger.debug("hij heeft een fout in error gevonden");
                return badRequest(updateWord.render("update", wordForm, word));

            } else {
                Logger.debug("nu zijn we in de else");
                word.setWord(wordForm.get().getWord());
                word.setDescription(wordForm.get().getDescription());
                word.update();
                return index();

            }
        } else {
            return login();

        }


    }


}
