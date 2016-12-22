package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author techno
 * @version 1.0
 *          created on 10/21/16
 */
@MappedSuperclass
public abstract class AbstractModel extends Model {

    @Id
    public Long id;

    public static <T> PagedList<T> getPagedList(Class<T> modelClass, String filter, String order, String sortBy, Integer pageSize, Integer page) {
        // TODO make better filtering, this one only filters string, with other than that problem may happen
        String expr = Arrays.stream(modelClass.getFields())
                .filter(field -> !field.getName().startsWith("find") && !field.getName().startsWith("_"))
                .map(Field::getName)
                .map(field -> "OR CAST(" + field + " AS TEXT) LIKE '%" + filter + "%' ")
                .collect(Collectors.joining(" "));
        return Ebean.find(modelClass)
                .where()
                .raw("1=1 " + expr)
                .orderBy(sortBy + " " + order + ", id " + order)
                .findPagedList(page, pageSize);
    }

    public ObjectNode getData() {
        ObjectNode row = Json.newObject();
        Arrays.stream(getClass().getFields())
                .filter(field -> !field.getName().startsWith("find") && !field.getName().startsWith("_"))
                .forEach(field -> {
                    String key = field.getName();
                    String value = "";
                    try {
                        value = field.get(this).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    row.put(key, value);
                });
        return row;
    }
}
