package br.com.foo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by moises on 5/31/14.
 */
public class CalendarUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static String format(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static Calendar parse(String dateString) {
        Calendar result = Calendar.getInstance();
        try {
            result.setTime(DATE_FORMAT.parse(dateString));
            return result;
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean validString(String dateString) {
        try {
            DATE_FORMAT.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
