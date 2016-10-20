package controllers;

import com.avaje.ebean.Expr;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Contact;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.Map;

public class HomeController extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result list() {
        /**
         * Get needed params
         */
        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = Contact.find.findRowCount();
        String filter = params.get("search[value]")[0];

        Integer pageSize = Integer.valueOf(params.get("length")[0]);
        Integer page = Integer.valueOf(params.get("start")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "name";
        String order = params.get("order[0][dir]")[0];

        switch (Integer.valueOf(params.get("order[0][column]")[0])) {
            case 0:
                sortBy = "name";
                break;
            case 1:
                sortBy = "title";
                break;
            case 2:
                sortBy = "email";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        PagedList<Contact> contactsPage = Contact.find.where()
                .or(
                        Expr.ilike("name", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("title", "%" + filter + "%"),
                                Expr.ilike("email", "%" + filter + "%")
                        )
                )
                .orderBy(sortBy + " " + order + ", id " + order)
                .findPagedList(page, pageSize);

        Integer iTotalDisplayRecords = contactsPage.getTotalRowCount();

        /**
         * Construct the JSON to return
         */
        ObjectNode result = Json.newObject();

        result.put("draw", Integer.valueOf(params.get("draw")[0]));
        result.put("recordsTotal", iTotalRecords);
        result.put("recordsFilter", iTotalDisplayRecords);

        ArrayNode an = result.putArray("data");
        contactsPage.getList().stream().forEach(contact->{
            ObjectNode row = Json.newObject();
            row.put("0", contact.name);
            row.put("1", contact.title);
            row.put("2", contact.email);
            an.add(row);
        });
//
//        for (Contact c : contactsPage.getList()) {
//            ObjectNode row = Json.newObject();
//            row.put("0", c.name);
//            row.put("1", c.title);
//            row.put("2", c.email);
//            an.add(row);
//        }

        return ok(result);
    }

}