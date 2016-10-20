package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Contact extends Model {

    public static Model.Finder<Long, Contact> find = new Finder<>(Contact.class);
    @Id
    public Long id;
    @Constraints.Required
    public String name;
    public String title;
    public String email;

    public static List<Contact> findAll() {
        return find.all();
    }

    public String toString() {
        return name;
    }
}