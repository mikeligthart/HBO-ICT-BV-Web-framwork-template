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
    @Inject FormFactory formFactory;
    @Inject FormFactory testformFactory;

    private static final String gebruiker = "admin";
    private static final String wachtwoord = "1234";



    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
//        List<Word> users = Word.getWords();
//        Form<AdminUsers> test =formFactory.form(AdminUsers.class).bindFromRequest();
        List<Word> word = Word.getFiveMostCounted();
//        Form<Word> userForm = formFactory.form(Word.class);
        return ok(index.render("digipedia", word));
    }



    public Result woorden() {
        List<Word> words = Word.getWords();
        return ok(woorden.render("digipedia",words));
    }

    public Result addWordForm() {
        List<Word> words = Word.getWords();
//        List<Word> word = Word.getTenMostCounted();
        Form<Word> addWordForm = formFactory.form(Word.class).bindFromRequest();

        return ok(addWord.render("digipedia",words, addWordForm));
    }

    public Result addWord(){
//        Form<AdminUsers> test =formFactory.form(AdminUsers.class).bindFromRequest();
        List<Word> words = Word.getWords();
//        List<Word> word = Word.getTenMostCounted();
        Form<Word> addWordForm = formFactory.form(Word.class).bindFromRequest();

        if (addWordForm.hasErrors()) {
            return badRequest(addWord.render("digipedia",words, addWordForm));
        } else {
            Logger.debug("wordForm " + addWordForm);
            Word newWord = addWordForm.get();
            newWord.save();

            return addWordForm();
        }
    }

    public Result test(String message){

        return ok(test.render("Test", message));

    }

    public  Result login() {
        Form<AdminUsers> test =formFactory.form(AdminUsers.class).bindFromRequest();

        return ok(login.render("Login",test));
    }
    public Result authenticate() {
        Logger.debug("ik ben nu in methode authenticatie");
        Form<AdminUsers> loginForm =formFactory.form(AdminUsers.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            Logger.debug("controleer of er haserrors zijn");
            return badRequest(login.render("admin",loginForm));
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
            }else{
                Logger.debug("wachtwoord en gebruikernaam komt niet overeen");
                return badRequest(login.render("admin",loginForm));
            }

        }
    }


}
