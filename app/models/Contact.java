package models;

import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Contact extends AbstractModel {

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

    public void fields() throws Exception {
        try {
//            Map<String, String> map = Arrays.stream(Contact.class.getDeclaredFields())
                    //.collect(Collectors.toMap(Field::getName,Field::getName));
//                    .collect(Collectors.toMap(Field::getName, ));

            //.map((x,y)->(x.getName(), y -> y))
            //.collect(Collectors.toMap(x -> x.getName(), x -> x.get(this)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}