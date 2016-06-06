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
    public long id;

    @Constraints.Required
    public String word;

    @Column(columnDefinition = "TEXT")
    public String description;


    public long count;



    // variable voor de aantal woorden beginpagina
    public static final int maxWord = 5;

    public static Model.Find<Long,Word> find = new Model.Find<Long,Word>(){};

    public Word WordById(long id){
        return find.byId(id);
    }

    public long getId() {
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


    public static List<Word> getFiveMostCounted(){

        List<Word> words = find.where().setOrderBy("id").findList();
        return words.stream().limit(maxWord).collect(Collectors.toList());

    }


    public static Word getWordById(int id) {

        return find.byId((long) id);
    }


    public void setWord(String word) {
        this.word = word;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
