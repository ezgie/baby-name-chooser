package controllers;

import com.avaje.ebean.*;
import models.Firstname;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.home;

import java.util.List;

import static com.avaje.ebean.Expr.*;

public class Application extends Controller {


    private static final int PAGE_SIZE = 20;
    private static final String FIELD_FIRST_NAME = "firstname";

    public Result index(Integer page, List<String> fl, List<String> ll, List<String> contains, List<String> notContains, String containsText) {
        Logger.debug("containsText : " + containsText);

        ExpressionList<Firstname> andJunction = Firstname.FIND.where().conjunction();
        addAndJunctionForFirstLetters(andJunction, fl);
        addAndJunctionForLastLetters(andJunction, ll);
        addAndJunctionForContainingLetters(andJunction, contains);
        addAndJunctionForNotContainingLetters(andJunction, notContains);
        addExpressionForContainsText(andJunction, containsText);
        andJunction.endJunction();
        andJunction.setOrderBy(FIELD_FIRST_NAME);
        PagedList<Firstname> currentPage = andJunction.findPagedList(page - 1, PAGE_SIZE);
        Logger.debug("currentPage.getTotalPageCount() : " + currentPage.getTotalPageCount());
        return ok(home.render(page, currentPage.getTotalPageCount(), currentPage.getList(), fl, ll, contains, notContains, containsText));
    }

    private void addExpressionForContainsText(ExpressionList<Firstname> andJunction, String containsText) {
        if(!StringUtils.isEmpty(containsText)) {
            andJunction.add(contains(FIELD_FIRST_NAME, containsText));
        }
    }

    private void addAndJunctionForContainingLetters(ExpressionList<Firstname> andJunction, List<String> letters) {
        if(!letters.isEmpty()) {
            ExpressionList conjunction = andJunction.conjunction();
            for (String letter : letters) {
                conjunction.add(contains(FIELD_FIRST_NAME, letter));
            }
            conjunction.endJunction();
        }
    }

    private void addAndJunctionForNotContainingLetters(ExpressionList<Firstname> andJunction, List<String> letters) {
        if(!letters.isEmpty()) {
            ExpressionList conjunction = andJunction.conjunction();
            for (String letter : letters) {
                conjunction.add(not(contains(FIELD_FIRST_NAME, letter)));
            }
            conjunction.endJunction();
        }
    }

    public void addAndJunctionForFirstLetters(ExpressionList<Firstname> andJunction, List<String> letters) {
        if(!letters.isEmpty()) {
            ExpressionList disjunction = andJunction.disjunction();
            for (String letter : letters) {
                disjunction.add(startsWith(FIELD_FIRST_NAME, letter));
            }
            disjunction.endJunction();
        }
    }
    public void addAndJunctionForLastLetters(ExpressionList<Firstname> andJunction, List<String> letters) {
        if(!letters.isEmpty()) {
            ExpressionList disjunction = andJunction.disjunction();
            for (String letter : letters) {
                disjunction.add(endsWith(FIELD_FIRST_NAME, letter));
            }
            disjunction.endJunction();
        }
    }
}
