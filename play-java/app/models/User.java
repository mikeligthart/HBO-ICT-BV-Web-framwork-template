package models;

import com.avaje.ebean.Model;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mike Ligthart on 18-May-16.
 */
@Entity
public class User extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String password;

    public int count;


    /*
    @Formats.DateTime(pattern="dd-MM-yyyy")
    public Date birthdate;
    */

    /**
     * Generic query helper for entity User with id Long
     */
    public static Find<Long,User> find = new Find<Long,User>(){};

    public User UserById(long id){
        return find.byId(id);
    }

    public static List<User> getUsers(){
        return find.all();
    }


}
