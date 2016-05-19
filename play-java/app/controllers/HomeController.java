package controllers;

import models.User;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import views.html.*;

import javax.inject.Inject;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    @Inject FormFactory formFactory;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        List<User> users = User.getUsers();
        Form<User> userForm = formFactory.form(User.class);
        return ok(index.render("Voorbeeld", users, userForm));
    }

    public Result addUser(){
        List<User> users = User.getUsers();
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();

        if (userForm.hasErrors()) {
            return badRequest(index.render("Voorbeeld", users, userForm));
        } else {
            Logger.debug("UserForm " + userForm);
            User user = userForm.get();
            user.save();
            return index();
        }
    }

}
