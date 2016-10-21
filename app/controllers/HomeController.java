package controllers;

import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.AbstractModel;
import models.Contact;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.lang.reflect.Field;
import java.util.Map;

public class HomeController extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result list() {


        //DynamicForm dynamicForm = Form.form().bindFromRequest();

        /**
         * Get needed params
         */

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        String filter = params.get("search[value]")[0];
        String order = params.get("order[0][dir]")[0];
        String sortBy = params.get("order[0][column]")[0];
        Integer pageSize = Integer.valueOf(params.get("length")[0]);
        Integer page = Integer.valueOf(params.get("start")[0]) / pageSize;
        PagedList<Contact> contactsPage = Contact.find.where().
                raw("name LIKE '%" + filter + "%' " +
                "OR title LIKE '%" + filter + "%' " +
                "OR email LIKE '%" + filter + "%' ")
                .orderBy(sortBy + " " + order + ", id " + order)
                .findPagedList(page, pageSize);

        ObjectNode result = Json.newObject();
        result.put("draw", Integer.valueOf(params.get("draw")[0]));
        Integer iTotalRecords = Contact.find.findRowCount();
        Integer iTotalDisplayRecords = contactsPage.getTotalRowCount();
        result.put("recordsTotal", iTotalRecords);
        result.put("recordsFiltered", iTotalDisplayRecords);

        ArrayNode an = result.putArray("data");

        //PagedList<AbstractModel> pagedList = Contact.getFields(params);
        contactsPage.getList().stream().forEach(contact -> {
            ObjectNode row = Json.newObject();
            for (Field field : contact.getClass().getFields()) {
                String name = field.getName();
                String value = "";
                if (!name.startsWith("find") && !name.startsWith("_ebean_props")) {
                    try {
                        value = "" + field.get(contact);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + "::::" + value);
                    row.put(name, value);
                }
            }
            an.add(row);
        });

        return ok(result);
    }



}