package utils;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

import java.util.List;

public class TemplateHelpers {

    public static int MIDDLE = 5;
    public static int SHOW_COUNT = 10;

    public static List<Integer> createRange(Integer page, Integer pageCount) {
        int minNumbering = getMinPage(page, pageCount);
        int maxNumbering = getMaxPage(page, pageCount);

//        List<Integer> range = IntStream.range(minNumbering, maxNumbering).boxed().collect(Collectors.toList());
        return ContiguousSet.create(Range.closed(minNumbering, maxNumbering), DiscreteDomain.integers()).asList();
//        return range;
    }

    public static Integer getMinPage(Integer page, Integer pageCount) {
        return (page - MIDDLE) < 1 ? 1 : (page - MIDDLE);
    }
    public static Integer getMaxPage(Integer page, Integer pageCount) {
        int minNumbering = getMinPage(page, pageCount);
        return (minNumbering + SHOW_COUNT) > pageCount ? pageCount : (minNumbering + SHOW_COUNT);
    }
}
