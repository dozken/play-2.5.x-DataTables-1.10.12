package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.PagedList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Map;

/**
 * @author techno
 * @version 1.0
 *          created on 10/21/16
 */
@MappedSuperclass
public abstract class AbstractModel extends Model {

    @Id
    public Long id;


    public String hello(){
        return "Hello";
    }

    public PagedList<AbstractModel> getFields(Map<String, String[]> params){
        String filter = params.get("search[value]")[0];
        String order = params.get("order[0][dir]")[0];
        String sortBy = params.get("order[0][column]")[0];
        Integer pageSize = Integer.valueOf(params.get("length")[0]);
        Integer page = Integer.valueOf(params.get("start")[0]) / pageSize;
        PagedList<AbstractModel> pagedList = Ebean.find(AbstractModel.class).where().
                raw("name LIKE '%" + filter + "%' " +
                        "OR title LIKE '%" + filter + "%' " +
                        "OR email LIKE '%" + filter + "%' ")
                .orderBy(sortBy + " " + order + ", id " + order)
                .findPagedList(page, pageSize);

        return pagedList;
    }
}
