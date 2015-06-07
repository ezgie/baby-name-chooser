package controllers;

import com.avaje.ebean.PagedList;
import models.Firstname;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

public class Application extends Controller {

    private static final int PAGE_SIZE = 20;
    public Result index(Integer page) {
        PagedList<Firstname> currentPage = Firstname.FIND.orderBy("id").findPagedList(page-1, PAGE_SIZE);
        return ok(home.render(page, currentPage.getTotalPageCount(), PAGE_SIZE, currentPage.getList()));
    }
}
