package br.ufma.lsdi.util;



import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.enuns.IndexEnum;

import java.util.ArrayList;
import java.util.List;

public class IndexUtil {

    public static String PM25 = "PM25";
    public static String PM10 = "PM10";
    public static String OZONE = "OZONE";
    public static String SULFURE_DIOXIDE = "SULFURE_DIOXIDE";
    public static String NITROGEN_DIOXIDE = "NITROGEN_DIOXIDE";

    public static String LOW = "LOW";
    public static String MODERATE = "MODERATE";
    public static String HIGH = "HIGH";
    public static String VERY_HIGH = "VERY_HIGH";

    public static String BAIXO = "BAIXO";
    public static String BAIXO2 = "BAIXO NÍVEL 2";
    public static String BAIXO3 = "BAIXO NÍVEL 3";
    public static String MODERADO = "MODERADO";
    public static String MODERADO2 = "MODERADO NÍVEL 2";
    public static String MODERADO3 = "MODERADO NÍVEL 3";
    public static String ALTO = "ALTO";
    public static String ALTO2 = "ALTO NÍVEL 2";
    public static String ALTO3 = "ALTO NÍVEL 3";
    public static String MUITOALTO = "MUITO ALTO";


    public static void getIndexScore(List<CapabilityDataAuxiliar> capabilityDataAuxiliars) {

        for (CapabilityDataAuxiliar dataAuxiliar : capabilityDataAuxiliars) {
            if (dataAuxiliar.getName().equals("PM10")) {
                setIndexPM10(dataAuxiliar);
            } else if (dataAuxiliar.getName().equals("PM25")) {
                setIndexPM25(dataAuxiliar);

            } else if (dataAuxiliar.getName().equals("OZONE")) {
                setIndexOzone(dataAuxiliar);
            } else if (dataAuxiliar.getName().equals("SULFURE_DIOXIDE")) {
                setIndexSulfureDioxide(dataAuxiliar);
            } else if (dataAuxiliar.getName().equals("NITROGEN_DIOXIDE")) {
                setIndexNitrogenDioxide(dataAuxiliar);
            }
        }

    }


    public static void setIndexPM10(CapabilityDataAuxiliar capabilityDataAuxiliar) {
        Double value = Double.valueOf((String) capabilityDataAuxiliar.getValue());

        if (value >= 0 && value <= 16) {
            capabilityDataAuxiliar.setLabel("BAIXO");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW1.getValue());
        } else if (value > 16 && value <= 33) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW2.getValue());
        } else if (value > 33 && value <= 50) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW3.getValue());
        } else if (value > 50 && value <= 58) {
            capabilityDataAuxiliar.setLabel("MODERADO");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE1.getValue());
        } else if (value > 58 && value <= 66) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE2.getValue());
        } else if (value > 66 && value <= 75) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE3.getValue());
        } else if (value > 75 && value <= 83) {
            capabilityDataAuxiliar.setLabel("ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH1.getValue());
        } else if (value > 83 && value <= 91) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH2.getValue());
        } else if (value > 91 && value <= 100) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH3.getValue());
        } else if (value > 100) {
            capabilityDataAuxiliar.setLabel("MUITO ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.VERY_HIGH.getValue());
        }

    }

    public static void setIndexPM25(CapabilityDataAuxiliar capabilityDataAuxiliar) {
        Double value = Double.valueOf((String) capabilityDataAuxiliar.getValue());

        if (value >= 0 && value <= 11) {
            capabilityDataAuxiliar.setLabel("BAIXO");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW1.getValue());
        } else if (value > 11 && value <= 23) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW2.getValue());
        } else if (value > 23 && value <= 35) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW3.getValue());
        } else if (value > 35 && value <= 41) {
            capabilityDataAuxiliar.setLabel("MODERADO");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE1.getValue());
        } else if (value > 41 && value <= 47) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE2.getValue());
        } else if (value > 47 && value <= 53) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE3.getValue());
        } else if (value > 53 && value <= 58) {
            capabilityDataAuxiliar.setLabel("ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH1.getValue());
        } else if (value > 58 && value <= 64) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH2.getValue());
        } else if (value > 64 && value <= 70) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH3.getValue());
        } else if (value > 70) {
            capabilityDataAuxiliar.setLabel("MUITO ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.VERY_HIGH.getValue());
        }

    }

    public static void setIndexOzone(CapabilityDataAuxiliar capabilityDataAuxiliar) {
        Double value = Double.valueOf((String) capabilityDataAuxiliar.getValue());

        if (value >= 0 && value <= 33) {
            capabilityDataAuxiliar.setLabel("BAIXO");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW1.getValue());
        } else if (value > 33 && value <= 66) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW2.getValue());
        } else if (value > 66 && value <= 100) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW3.getValue());
        } else if (value > 100 && value <= 120) {
            capabilityDataAuxiliar.setLabel("MODERADO");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE1.getValue());
        } else if (value > 120 && value <= 140) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE2.getValue());
        } else if (value > 140 && value <= 160) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE3.getValue());
        } else if (value > 160 && value <= 187) {
            capabilityDataAuxiliar.setLabel("ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH1.getValue());
        } else if (value > 187 && value <= 213) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH2.getValue());
        } else if (value > 213 && value <= 240) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH3.getValue());
        } else if (value > 240) {
            capabilityDataAuxiliar.setLabel("MUITO ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.VERY_HIGH.getValue());
        }

    }

    public static void setIndexSulfureDioxide(CapabilityDataAuxiliar capabilityDataAuxiliar) {
        Double value = Double.valueOf((String) capabilityDataAuxiliar.getValue());

        if (value >= 0 && value <= 88) {
            capabilityDataAuxiliar.setLabel("BAIXO");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW1.getValue());
        } else if (value > 88 && value <= 177) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW2.getValue());
        } else if (value > 177 && value <= 266) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW3.getValue());
        } else if (value > 266 && value <= 354) {
            capabilityDataAuxiliar.setLabel("MODERADO");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE1.getValue());
        } else if (value > 354 && value <= 443) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE2.getValue());
        } else if (value > 443 && value <= 532) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE3.getValue());
        } else if (value > 532 && value <= 710) {
            capabilityDataAuxiliar.setLabel("ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH1.getValue());
        } else if (value > 710 && value <= 887) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH2.getValue());
        } else if (value > 887 && value <= 1064) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH3.getValue());
        } else if (value > 1064) {
            capabilityDataAuxiliar.setLabel("MUITO ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.VERY_HIGH.getValue());
        }

    }

    public static void setIndexNitrogenDioxide(CapabilityDataAuxiliar capabilityDataAuxiliar) {
        Double value = Double.valueOf((String) capabilityDataAuxiliar.getValue());

        if (value >= 0 && value <= 67) {
            capabilityDataAuxiliar.setLabel("BAIXO");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW1.getValue());
        } else if (value > 67 && value <= 134) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW2.getValue());
        } else if (value > 134 && value <= 200) {
            capabilityDataAuxiliar.setLabel("BAIXO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.LOW3.getValue());
        } else if (value > 200 && value <= 267) {
            capabilityDataAuxiliar.setLabel("MODERADO");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE1.getValue());
        } else if (value > 267 && value <= 334) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE2.getValue());
        } else if (value > 334 && value <= 400) {
            capabilityDataAuxiliar.setLabel("MODERADO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.MODERATE3.getValue());
        } else if (value > 400 && value <= 467) {
            capabilityDataAuxiliar.setLabel("ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH1.getValue());
        } else if (value > 467 && value <= 534) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 2");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH2.getValue());
        } else if (value > 534 && value <= 600) {
            capabilityDataAuxiliar.setLabel("ALTO NÍVEL 3");
            capabilityDataAuxiliar.setIndex(IndexEnum.HIGH3.getValue());
        } else if (value > 600) {
            capabilityDataAuxiliar.setLabel("MUITO ALTO");
            capabilityDataAuxiliar.setIndex(IndexEnum.VERY_HIGH.getValue());
        }

    }


}
