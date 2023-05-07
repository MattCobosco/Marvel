package core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateUtil {
    ;

    public static String parseDate(final Date date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return format.format(date);
    }
}
