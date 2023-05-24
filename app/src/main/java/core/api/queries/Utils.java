package core.api.queries;

import java.util.Date;
import java.util.List;

import core.utils.DateUtil;

public class Utils {
    public static final int MAX_SIZE = 100;
    public static final String LIST_SEPARATOR = ",";
    public static void checkOffset(int offset) {
        if (0 > offset) {
            throw new IllegalArgumentException("Offset must be greater or equal to 0");
        }
    }

    public static void checkLimit(final int limit) {
        if (0 >= limit) {
            throw new IllegalArgumentException("limit must be bigger than zero");
        }

        if (MAX_SIZE < limit) {
            throw new IllegalArgumentException("limit must be smaller than 100");
        }
    }

    public static void checkNull(final List<Integer> list) {
        if (null == list) {
            throw new IllegalArgumentException("the collection can not be null");
        }
    }

    public static String convertDate(final Date date) {
        if (null == date) {
            return null;
        }
        return DateUtil.parseDate(date);
    }

    public static String convertOrderBy(final String orderBy, final boolean ascendant) {
        if (null == orderBy) {
            return null;
        }
        return (ascendant) ? orderBy : "-" + orderBy;
    }

    public static String convertToList(final List<Integer> list) {
        String plainList = "";
        for (int i = 0; i < list.size(); i++) {
            plainList += Integer.toString(list.get(i));
            if (i < list.size() - 1) {
                plainList += LIST_SEPARATOR;
            }
        }
        return (plainList.isEmpty()) ? null : plainList;
    }
}
