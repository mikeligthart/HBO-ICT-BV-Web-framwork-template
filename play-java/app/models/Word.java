package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Gebruiker on 25-5-2016.
 */
@Entity
public class Word extends Model{


    @Id
    @GeneratedValue
    public int id;

    @Constraints.Required
    public String word;

    @Column(columnDefinition = "TEXT")
    public String description;

    public static Model.Find<Integer,Word> find = new Model.Find<Integer,Word>(){};

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public static List<Word> getWords(){
        return find.all();
    }


    public static Word getWordById(int id) {

        return find.byId(id);
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
