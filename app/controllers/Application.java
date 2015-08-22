package controllers;

import com.avaje.ebean.*;
import models.Firstname;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

import java.util.List;

import static com.avaje.ebean.Expr.iendsWith;
import static com.avaje.ebean.Expr.istartsWith;

public class Application extends Controller {


    private static final int PAGE_SIZE = 20;
    private static final String FIELD_FIRST_NAME = "firstname";

    public Result index(Integer page, List<String> fl, List<String> ll) {
        Logger.debug(fl.toString());
        Logger.debug(ll.toString());

        ExpressionList<Firstname> andJunction = Firstname.FIND.where().conjunction();
        addAndJunctionForFirstLetters(andJunction, fl);
        addAndJunctionForLastLetters(andJunction, ll);
        andJunction.endJunction();
        PagedList<Firstname> currentPage = andJunction.findPagedList(page - 1, PAGE_SIZE);
        Logger.debug("currentPage.getTotalPageCount() : " + currentPage.getTotalPageCount());
        return ok(home.render(page, currentPage.getTotalPageCount(), currentPage.getList(), fl, ll));
    }

    public void addAndJunctionForFirstLetters(ExpressionList<Firstname> andJunction, List<String> letters) {
        if(!letters.isEmpty()) {
            ExpressionList disjunction = andJunction.disjunction();
            for (String letter : letters) {
                disjunction.add(istartsWith(FIELD_FIRST_NAME, letter));
            }
            disjunction.endJunction();
        }
    }
    public void addAndJunctionForLastLetters(ExpressionList<Firstname> andJunction, List<String> letters) {
        if(!letters.isEmpty()) {
            ExpressionList disjunction = andJunction.disjunction();
            for (String letter : letters) {
                disjunction.add(iendsWith(FIELD_FIRST_NAME, letter));
            }
            disjunction.endJunction();
        }
    }
}
