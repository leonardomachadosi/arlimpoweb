package br.ufma.lsdi.util;

import br.ufma.lsdi.model.Concentracao;
import br.ufma.lsdi.model.GetDataContextResource;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;

import java.util.*;

public class ConcentracaoMediaUtil {

    /**
     * Monta a lista de dados de contexto e ordena por data
     *
     * @param resourceHelper
     * @return
     */
    private static List<CapabilityDataAuxiliar> getDataOrdenado(ResourceHelper resourceHelper, String particula) {
        List<CapabilityDataAuxiliar> listDataAux = new ArrayList<>();
        if (resourceHelper != null && resourceHelper.getResources() != null) {

            for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                List<Map<String, Object>> dataSensor = capability.get(particula);

                if (dataSensor != null) {
                    for (Map<String, Object> cap2 : dataSensor) {
                        CapabilityDataAuxiliar dataAuxiliar2 = new CapabilityDataAuxiliar(cap2);
                        if (dataAuxiliar2.getResource() != null && dataAuxiliar2.getResource().getLat() != null) {

                            listDataAux.add(dataAuxiliar2);

                        }
                    }
                }
            }
        }

        Collections.sort(listDataAux, new Comparator<CapabilityDataAuxiliar>() {
            @Override
            public int compare(CapabilityDataAuxiliar o1, CapabilityDataAuxiliar o2) {
                Date data1;
                Date data2;

                data1 = DateUtil.convertTimestampData(o1.getTimestamp());
                data2 = DateUtil.convertTimestampData(o2.getTimestamp());

                if (data2.after(data1)) {
                    return -1;
                }
                if (data2.before(data1)) {
                    return 1;
                }
                return 0;
            }
        });
        return listDataAux;
    }

    /**
     * calcula a concentração média agrupada por dia
     *
     * @param resourceHelper
     * @return List<Concentracao>
     */
    public static List<Concentracao> getConcentracaoDia(ResourceHelper resourceHelper,Date dataInicio, String paricula) {
        List<Concentracao> concentracaos = new ArrayList<>();
        Concentracao concentracaoMedia;
        List<CapabilityDataAuxiliar> listDataAux = getDataOrdenado(resourceHelper,paricula);
        if(listDataAux != null && listDataAux.size()>0 ) {
            Double media = 0.0;
            int aux = 0;

            int dia = DateUtil.getDay(dataInicio);
            for (CapabilityDataAuxiliar data : listDataAux) {
                if (dia == DateUtil.getDay(DateUtil.convertTimestampData(data.getTimestamp()))) {
                    media += Double.parseDouble(data.getValue());
                    aux++;
                } else {
                    concentracaoMedia = new Concentracao();
                    concentracaoMedia.setValue(media / aux);
                    concentracaoMedia.setDate(DateUtil.convertTimestampData(data.getTimestamp()));
                    concentracaos.add(concentracaoMedia);
                    dia = DateUtil.getDay(DateUtil.convertTimestampData(data.getTimestamp()));
                    media = 0.0;
                    aux = 0;
                }
            }
            if (media > 0) {
                concentracaoMedia = new Concentracao();
                concentracaoMedia.setValue(media / aux);
                concentracaoMedia.setDate(dataInicio);
                concentracaos.add(concentracaoMedia);
            }
        }else{
            return new ArrayList<>();
        }
        return concentracaos;
    }


    /**
     * Calcula a concentração média por mês
     * @param resourceHelper
     * @return
     */
    public static List<Concentracao> getConcentracaoMes( ResourceHelper resourceHelper, Date dataFinal, String paricula) {
        List<Concentracao> concentracaos = new ArrayList<>();
        Concentracao concentracaoMedia;
        List<CapabilityDataAuxiliar> listDataAux = getDataOrdenado(resourceHelper,paricula);

        if(listDataAux != null && listDataAux.size()>0 ){
        Date dataInicio = DateUtil.convertTimestampData(listDataAux.get(0).getTimestamp());
        Double media = 0.0;
        int aux = 0;

           int mes = DateUtil.getMonth(dataInicio);
            int ano = DateUtil.getYear(dataInicio);
            int contAno = (DateUtil.getYear(dataFinal) - DateUtil.getYear(dataInicio)) + 1;
            aux = 0;
            media = 0.0;

            for (int i = 0; i < contAno; i++) {
                for (CapabilityDataAuxiliar dataAuxiliar : listDataAux) {
                    if (ano == DateUtil.getYear(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()))) {
                        if (mes == DateUtil.getMonth(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()))) {
                            media += Double.parseDouble(dataAuxiliar.getValue());
                            aux++;
                        } else {
                            concentracaoMedia = new Concentracao();
                            concentracaoMedia.setValue(media / aux);
                            concentracaoMedia.setDate(dataInicio);
                            concentracaos.add(concentracaoMedia);
                            dataInicio = DateUtil.convertTimestampData(dataAuxiliar.getTimestamp());
                            mes = DateUtil.getMonth(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()));
                            media = 0.0;
                            aux = 0;
                        }

                    } else {
                        ano = DateUtil.getYear(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()));
                        mes = DateUtil.getMonth(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()));
                        media = 0.0;
                        aux = 0;

                    }
                }
            }
            if (media > 0) {
                concentracaoMedia = new Concentracao();
                concentracaoMedia.setValue(media / aux);
                concentracaoMedia.setDate(dataInicio);
                concentracaos.add(concentracaoMedia);
            }
        }else{
            new ArrayList<>();
        }
        return concentracaos;
    }


    /**
     * Calcula a concentração média por ano
     * @param resourceHelper
     * @return
     */
    public static List<Concentracao> getConcentracaoAno( ResourceHelper resourceHelper, Date dataFinal, String paricula){
        List<Concentracao> concentracaos = new ArrayList<>();
        Concentracao concentracaoMedia ;
        List<CapabilityDataAuxiliar> listDataAux = getDataOrdenado(resourceHelper, paricula);
        if(listDataAux != null && listDataAux.size()>0 ) {
            Date dataInicio = DateUtil.convertTimestampData(listDataAux.get(0).getTimestamp());
            Double media = 0.0;
            int aux = 0;
            int ano = DateUtil.getYear(dataInicio);
            int contAno = (DateUtil.getYear(dataFinal) - DateUtil.getYear(dataInicio)) + 1;
            aux = 0;
            media = 0.0;

            for (int i = 0; i < contAno; i++) {
                for (CapabilityDataAuxiliar dataAuxiliar : listDataAux) {

                    if (ano == DateUtil.getYear(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()))) {
                        media += Double.parseDouble(dataAuxiliar.getValue());
                        aux++;

                    } else {
                        concentracaoMedia = new Concentracao();
                        concentracaoMedia.setValue(media / aux);
                        concentracaoMedia.setDate(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()));
                        concentracaos.add(concentracaoMedia);
                        ano = DateUtil.getYear(DateUtil.convertTimestampData(dataAuxiliar.getTimestamp()));
                        media = 0.0;
                        aux = 0;

                    }

                }
            }
            if (media > 0) {
                concentracaoMedia = new Concentracao();
                concentracaoMedia.setValue(media / aux);
                concentracaoMedia.setDate(DateUtil.convertTimestampData(listDataAux.get(0).getTimestamp()));
                concentracaos.add(concentracaoMedia);
            }
        }else{
            return new ArrayList<>();
        }
        return concentracaos;
    }
}
