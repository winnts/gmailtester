package space.adyachenko.utils;

import java.text.SimpleDateFormat;

public class Utilities {
    public static String uniqueId() {
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return dt.format(System.currentTimeMillis());
    }
}
