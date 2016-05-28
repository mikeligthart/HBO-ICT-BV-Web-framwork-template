package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

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
    public long id;

    @Constraints.Required
    public String word;

    @Constraints.Required
    public String description;

    public long count;

    // variable voor de aantal woorden beginpagina
    public static final int maxWord = 5;

    public static Model.Find<Long,Word> find = new Model.Find<Long,Word>(){};

    public Word UserById(long id){
        return find.byId(id);
    }

    public String getWord() {
        return word;
    }

    public static List<Word> getWords(){
        return find.all();
    }


    public static List<Word> getTenMostCounted(){

        List<Word> words = find.where().setOrderBy("id").findList();
        return words.stream().limit(maxWord).collect(Collectors.toList());

    }

}
