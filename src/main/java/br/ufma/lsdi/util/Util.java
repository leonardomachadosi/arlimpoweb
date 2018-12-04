package br.ufma.lsdi.util;


import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    public static String PM25 ="PM25";
    public static String PM10 ="PM10";
    public static String OZONE="OZONE";
    public static String SULFURE_DIOXIDE ="SULFURE_DIOXIDE";
    public static String NITROGEN_DIOXIDE ="NITROGEN_DIOXIDE";
    public static String URL_BASE = "http://cidadesinteligentes.lsdi.ufma.br/";
    private static Gson gson = new Gson();
    private static SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");// HH:mm:ss");
    private static Calendar calendar = Calendar.getInstance();
    public static List<CapabilityDataAuxiliar>


    /**
     * Ler instâncias de uma particulo dentro de um intervalo
     */
    getDataCapability(String ponto, String particula, String ano, Date inicio, Date fim ) throws FileNotFoundException, ParseException {
        List<CapabilityDataAuxiliar> listData;
        List<CapabilityDataAuxiliar> listDataFiltrado = new ArrayList<>();
        String data = null;
        try {
            data = new String(Files.readAllBytes(Paths.get("C:\\madrid\\"+ano+"\\"+ponto+particula+ano+".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listData = gson.fromJson(data, new TypeToken<List<CapabilityDataAuxiliar>>(){}.getType());

        for (CapabilityDataAuxiliar dataAuxiliar : listData){
            Date dateAux = convertTimestampData(dataAuxiliar.getTimestamp());


            if(((getMonth(dateAux) > getMonth(inicio) && getYear(dateAux) > getYear(inicio))&&
               getMonth(dateAux) < getMonth(fim) && getYear(dateAux) < getYear(fim)) ||
               (getMonth(dateAux) == getMonth(inicio) && getYear(dateAux) == getYear(inicio) && getDay(dateAux) >= getDay(inicio)) ||
               (getMonth(dateAux) == getMonth(fim) && getYear(dateAux) == getYear(fim) && getDay(dateAux) <= getDay(inicio)) ||
               (getMonth(dateAux)< getMonth(inicio) && getYear(dateAux) > getYear(inicio))){

                    listDataFiltrado.add(dataAuxiliar);
               }
            }




        return listDataFiltrado;
    }
    public static int getDay(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    public static int getMonth(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }
    public static int getYear(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static Date convertTimestampData(String date)  {
        String[] dateAux = date.replace(".428Z","")
                .replace("T"," ")
                .replace("-","/").split(" ");
        try {
            return formato.parse(dateAux[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }
}