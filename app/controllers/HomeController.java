package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import models.AbstractModel;
import models.Contact;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.Map;

public class HomeController extends Controller {

    @Inject
    private FormFactory formFactory;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result list() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();

        ObjectNode result = Json.newObject();

        String filter = params.get("search[value]")[0];
        String order = params.get("order[0][dir]")[0];
        String sortBy = Integer.parseInt(params.get("order[0][column]")[0]) + 1 + "";
        Integer pageSize = Integer.valueOf(params.get("length")[0]);
        Integer page = Integer.valueOf(params.get("start")[0]) / pageSize;

        PagedList<Contact> contactsPage = AbstractModel.getPagedList(Contact.class, filter, order, sortBy, pageSize, page);

        Integer draw = Integer.valueOf(params.get("draw")[0]);
        Integer recordsFilter = contactsPage.getTotalRowCount();
        Integer recordsTotal = Ebean.find(Contact.class).findRowCount();
        result.put("draw", draw);
        result.put("recordsFilter", recordsFilter);
        result.put("recordsTotal", recordsTotal);

        ArrayNode data = result.putArray("data");
        contactsPage.getList().stream().forEach(contact -> data.add(contact.getData()));

        return ok(result);
    }
}