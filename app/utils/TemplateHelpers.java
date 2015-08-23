package utils;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import play.Logger;

import java.util.List;

public class TemplateHelpers {

    public static int MIDDLE = 1;
    public static int SHOW_COUNT = 2;

    public static List<Integer> createRange(Integer page, Integer pageCount) {

        Logger.debug("page:" + page + " pageCount:" + pageCount);
        int minNumbering = getMinPage(page);
        int maxNumbering = getMaxPage(page, pageCount);
        Logger.debug("min:" + minNumbering + " max:" + maxNumbering);
        return ContiguousSet.create(Range.closed(minNumbering, maxNumbering), DiscreteDomain.integers()).asList();
    }

    public static Integer getMinPage(Integer page) {
        return (page - MIDDLE) < 1 ? 1 : (page - MIDDLE);
    }
    public static Integer getMaxPage(Integer page, Integer pageCount) {
        int minNumbering = getMinPage(page);
        return (minNumbering + SHOW_COUNT) > pageCount ? pageCount : (minNumbering + SHOW_COUNT);
    }
}
