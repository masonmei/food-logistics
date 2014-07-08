package org.personal.mason.fl.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mason on 7/3/14.
 */
public class Constrains {
    public static final String[] ORDER_STATUS = new String[]{
            "ORDERED",
            "NOTIFIED",
            "DELIVERED",
            "ON_THE_WAY",
            "FINISHED"
    };

    public static void main(String... args){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            Date parse = dateFormat.parse("2014-07-06T03:06:04.000Z");
            System.out.println(parse.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
