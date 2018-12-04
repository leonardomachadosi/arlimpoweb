package br.ufma.lsdi.util;



import java.util.ArrayList;
import java.util.List;

public class IndexUtil {

    public static String PM25 ="PM25";
    public static String PM10 ="PM10";
    public static String OZONE="OZONE";
    public static String SULFURE_DIOXIDE ="SULFURE_DIOXIDE";
    public static String NITROGEN_DIOXIDE ="NITROGEN_DIOXIDE";

    public static String LOW ="LOW";
    public static String MODERATE ="MODERATE";
    public static String HIGH="HIGH";
    public static String VERY_HIGH ="VERY_HIGH";

    public static String MSG_LOW ="A qualidade do ar é considerada satisfatória e a poluição do ar representa pouco ou nenhum risco";
    public static String MSG_MODERATE="A qualidade do ar é aceitável; no entanto, para alguns poluentes, pode haver um " +
            "problema de saúde moderado para um número muito pequeno de pessoas que são incomumente sensíveis à poluição do ar.";
    public static String MSG_HIGH="Todos podem começar a sentir " +
            "efeitos na saúde; membros de grupos sensíveis podem sofrer efeitos mais sérios sobre a saúde";
    public static String MSG_VERY_HIGH="Alerta de saúde: todos podem experimentar efeitos mais graves para a saúde";


    public static List<String> getIndexScore(Double concentracaoMedia){
        List<String> result = new ArrayList<>();
        if (concentracaoMedia <= 100){
            result.add(LOW);
            result.add(MSG_LOW);
        }else if (concentracaoMedia <= 160){
            result.add(MODERATE);
            result.add(MSG_MODERATE);
        }else if (concentracaoMedia <= 240){
            result.add(HIGH);
            result.add(MSG_HIGH);
        }else{
            result.add(VERY_HIGH);
            result.add(MSG_VERY_HIGH);
        }
        return result;
    }


}
