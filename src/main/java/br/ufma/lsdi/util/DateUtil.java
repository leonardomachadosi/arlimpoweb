package br.ufma.lsdi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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


    public static int dataDiff(java.util.Date dataLow, java.util.Date dataHigh){
        GregorianCalendar startTime = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();
        GregorianCalendar curTime = new GregorianCalendar();
        GregorianCalendar baseTime = new GregorianCalendar();
        startTime.setTime(dataLow);
        endTime.setTime(dataHigh);
        int dif_multiplier = 1;
        // Verifica a ordem de inicio das datas
        if( dataLow.compareTo( dataHigh ) < 0 ){
            baseTime.setTime(dataHigh);
            curTime.setTime(dataLow);
            dif_multiplier = 1;
        }else{
            baseTime.setTime(dataLow);
            curTime.setTime(dataHigh);
            dif_multiplier = -1;
        }
        int result_years = 0;
        int result_months = 0;
        int result_days = 0;
        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissesto
        while( curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||
                curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)  )
        {
            int max_day = curTime.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );
            result_months += max_day;
            curTime.add(GregorianCalendar.MONTH, 1);
        }
        // Marca que é um saldo negativo ou positivo
        result_months = result_months*dif_multiplier;
        // Retirna a diferenca de dias do total dos meses
        result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));
        return result_years+result_months+result_days;
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
