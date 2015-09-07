package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Locale;

@Entity
@Table(name = "firstnames")
public class Firstname extends Model {
    private static String LOCALE_TR = "tr";
    @Id
    private Long id;
    private String firstname;
    private String origin;
    private String meaning;
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getFirstnameCapitalized() {
        return firstname.substring(0, 1).toUpperCase(Locale.forLanguageTag(LOCALE_TR)).concat(firstname.substring(1));
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static Model.Finder<Long, Firstname> FIND =
            new Model.Finder(Firstname.class);
}
