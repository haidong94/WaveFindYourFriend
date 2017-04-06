package vinsoft.com.wavefindyourfriend.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by DONG on 06-Apr-17.
 */

public class Util {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

    public static String getHours(long timestamp){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        String hours = sdf2.format(cal.getTime());
        return hours;
    }

    public static String getTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        String time = sdf.format(cal.getTime());
        return time;
    }
}
