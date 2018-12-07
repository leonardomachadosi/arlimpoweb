package br.ufma.lsdi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Calendar calendar = Calendar.getInstance();
   private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss");

    public static Date convertTimestampData(String date)  {
        String[] dateAux = date.replace(".428Z","")
                .replace("T"," ")
                .replace("-","/").split(" ");
        try {
            return fmt.parse(dateAux[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }



    public static int getDay(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getMonth(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    public static int getYear(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
