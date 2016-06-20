package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Gebruiker on 8-6-2016.
 */

@Entity
public class FrontPageText extends Model {

    @Id
    public int id;

    @Constraints.Required
    @Column(columnDefinition = "TEXT")
    public String frontText;

    public static Model.Find<Integer,FrontPageText> find = new Model.Find<Integer,FrontPageText>(){};

    public String getFrontText() {
        return frontText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static FrontPageText findbyidfrontPageText(int id) {
        return find.byId(id);
    }

    public void setFrontText(String frontText) {
        this.frontText = frontText;
    }
}
