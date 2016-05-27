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
        List<Word> users = Word.getUsers();
        Form<AdminUsers> test =formFactory.form(AdminUsers.class).bindFromRequest();
        List<Word> word = Word.getTenMostCounted();
        Form<Word> userForm = formFactory.form(Word.class);
        return ok(index.render("digipedia", users, userForm, word,test));
    }

    public Result addUser(){
        Form<AdminUsers> test =formFactory.form(AdminUsers.class).bindFromRequest();
        List<Word> users = Word.getUsers();
        List<Word> word = Word.getTenMostCounted();
        Form<Word> userForm = formFactory.form(Word.class).bindFromRequest();

        if (userForm.hasErrors()) {
            return badRequest(index.render("digipedia", users, userForm, word,test));
        } else {
            Logger.debug("UserForm " + userForm);
            Word user = userForm.get();
            user.save();

            return index();
        }
    }

    public Result test(String message){

        return ok(test.render("Test", message));

    }

    public  Result login() {
        Form<AdminUsers> test =formFactory.form(AdminUsers.class).bindFromRequest();

        return ok(login.render(test));
    }
    public Result authenticate() {

        Form<AdminUsers> loginForm =formFactory.form(AdminUsers.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            if (loginForm.get().getUserName().equals(gebruiker) && loginForm.get().getPassword().equals(wachtwoord)) {
                session().clear();
                session("username", loginForm.get().userName);
                return redirect(routes.HomeController.index());
            }else{
                return badRequest(login.render(loginForm));
            }

        }
    }


}
