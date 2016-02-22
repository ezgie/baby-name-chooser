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
import static java.util.stream.Collectors.toList;

public class Application extends Controller {

    private static final int PAGE_SIZE = 20;
    private static final String FIELD_FIRST_NAME = "firstname";
    private static final String FIELD_GENDER = "gender";
    private static final String FIELD_ORIGIN = "origin";
    private static final String ORIGIN_FILTER = "%,%";

    public Result index(Integer page, List<String> firstLetters, List<String> lastLetters, List<String> containsLetters, List<String> notContainsLetters, String containsText, List<String> genders, List<String> origins) {
        Logger.debug("containsText : " + containsText);

        ExpressionList<Firstname> andJunction = Firstname.FIND.where().conjunction();
        addAndJunctionForFirstLetters(andJunction, firstLetters);
        addAndJunctionForLastLetters(andJunction, lastLetters);
        addAndJunctionForContainingLetters(andJunction, containsLetters);
        addAndJunctionForNotContainingLetters(andJunction, notContainsLetters);
        addExpressionForContainsText(andJunction, containsText);
        addExpressionForGender(andJunction, genders);
        addOrJunctionForOrigins(andJunction, origins);
        andJunction.endJunction();
        andJunction.setOrderBy(FIELD_FIRST_NAME);
        PagedList<Firstname> currentPage = andJunction.findPagedList(page - 1, PAGE_SIZE);
        Logger.debug("currentPage.getTotalPageCount() : " + currentPage.getTotalPageCount());
        return ok(home.render(page, currentPage.getTotalPageCount(), currentPage.getList(), firstLetters, lastLetters, containsLetters, notContainsLetters, containsText, genders, origins, getAllOrigins()));
    }

    private List<String> getAllOrigins() {
        return Firstname.FIND
                .select(FIELD_ORIGIN)
                .setDistinct(true)
                .where()
                .not(ilike(FIELD_ORIGIN, ORIGIN_FILTER))
                .orderBy(FIELD_ORIGIN)
                .findList()
                .stream()
                .map(Firstname::getOrigin)
                .collect(toList());
    }

    private void addExpressionForGender(ExpressionList<Firstname> andJunction, List<String> genders) {
        if(!genders.isEmpty()) {
            ExpressionList disjunction = andJunction.disjunction();
            for (String gender : genders) {
                disjunction.add(eq(FIELD_GENDER, gender));
            }
            disjunction.endJunction();
        }
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
    private void addOrJunctionForOrigins(ExpressionList<Firstname> andJunction, List<String> origins) {
        if(!origins.isEmpty()) {
            ExpressionList conjunction = andJunction.disjunction();
            for (String origin : origins) {
                conjunction.add(contains(FIELD_ORIGIN, origin));
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
