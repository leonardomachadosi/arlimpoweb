package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Concentracao;
import br.ufma.lsdi.model.Matcher;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.ResourceColectorService;
import br.ufma.lsdi.service.interscity.CapabilityClient;
import br.ufma.lsdi.util.DateUtil;
import br.ufma.lsdi.util.JsonUtil;
import br.ufma.lsdi.util.Util;
import br.ufma.lsdi.util.WebUtil;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.primefaces.model.chart.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("view")
public class VisualizarRecursoBean {


    private CapabilityClient capabilityClient;
    private Resource resource;
    private Calendar calendar = Calendar.getInstance();
    private Date dataInicio, dataFinal;
    private String agrupamento = "dia";
    private String[] selectedParticula;
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// HH:mm:ss");
    private List<CapabilityDataAuxiliar> dataOzone, dataNitrogenio, dataEnxofre, dataPM10, dataPM25;
    private LineChartModel lineModel;
    private ResourceColectorService colectorService;

    public VisualizarRecursoBean(CapabilityClient capabilityClient, ResourceColectorService colectorService) {
        this.capabilityClient = capabilityClient;
        this.colectorService = colectorService;
    }

    @PostConstruct
    public void init() {
        lineModel = new LineChartModel();
        initObjects();
        }

    private void initObjects() {
        getObjetoFlashScope();
    }


    public void getObjetoFlashScope() {

        if (WebUtil.flashScope().get("resource") != null) {
            resource = (Resource) WebUtil.flashScope().get("resource");
        } else {
            redirect();
        }
    }

    public void redirect() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirect = "pretty:buscaRecurso";
        NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
        myNav.handleNavigation(facesContext, null, redirect);
    }


    private void createLineModels(String title, String labelX, int max, int min,
                                  List<Concentracao> mediasSO2, List<Concentracao> mediasO3) {
        lineModel = initLinearModel(mediasSO2, mediasO3);
        lineModel.setTitle(title);
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(min);
        yAxis.setMax(max);
    }
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);

        model.addSeries(boys);
        model.addSeries(girls);

        return model;
    }


    /**
     * Plota o gráfico, ler instâncias em um intervalo
     * Agrupa por dia/mês/ano
     * cria o modelo do gráfico
     */
    public void gerarGrafico(){
       ResourceHelper resourceHelper = getDataResource();

      /*  List<CapabilityDataAuxiliar> dataCapabilitySO2 =null;
        List<CapabilityDataAuxiliar> dataCapability03 =null;
        try {
            dataCapabilitySO2 =  Util.getDataCapability("saoMarcos",Util.NITROGEN_DIOXIDE,"2016",dataInicio,dataFinal);
            dataCapability03 = Util.getDataCapability("saoMarcos",Util.OZONE,"2016",dataInicio,dataFinal);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Concentracao> mediasSO2 = getConcentracaomedia(dataCapabilitySO2);
        List<Concentracao> mediasO3 = getConcentracaomedia(dataCapability03);
        createLineModels("Concentração Média","Dias",200,0,mediasSO2, mediasO3);
    */
    }


    public ResourceHelper getDataResource(){
        ResourceHelper resourceHelper = new ResourceHelper();
        List<String> capabilities = Arrays.asList(selectedParticula);
        List<String> uuids = new ArrayList<>();
        uuids.add(resource.getUuid());

        try {
            resourceHelper = colectorService.findResources(uuids,capabilities,dataInicio,dataFinal);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return resourceHelper;
    }

    /**
     * Calcula a concentração média por agrupamento
     * @param dataList
     * @return
     */
    private List<Concentracao> getConcentracaomedia( List<CapabilityDataAuxiliar> dataList){
        List<Concentracao> listConcentracao = new ArrayList<>();
        Double media = 0.0;
        int counter =0;
        for (int i =0 ; i< dataList.size()-1;i++){
            Date date1 = Util.convertTimestampData(dataList.get(i).getTimestamp());
            Date date2 = Util.convertTimestampData(dataList.get(i+1).getTimestamp());
            if (Util.getDay(date1) == Util.getDay(date2) &&
                    Util.getMonth(date1) == Util.getMonth(date2) && Util.getYear(date1)== Util.getYear(date2)){
                media +=Double.parseDouble(dataList.get(i).getValue());
                counter ++;
            }else{
                Concentracao c = new Concentracao(Util.convertTimestampData(dataList.get(i+1).getTimestamp()),
                        agrupamento, media/counter);
                media =0.0;
                counter =0;
                listConcentracao.add(c);
            }


        }

        return listConcentracao;
    }


    /**
     * Cria o modelo e  injeta dados no grafico de linha
     * @param mediasSO2
     * @param mediasO3
     * @return model
     */
    private LineChartModel initLinearModel(List<Concentracao> mediasSO2, List<Concentracao> mediasO3) {
        LineChartModel model = new LineChartModel();
        LineChartSeries seriesSO2 = new LineChartSeries();
        model.getAxes().put(AxisType.X, new CategoryAxis("Dias"));
        seriesSO2.setLabel("S02");
        int dia =1;
        for(Concentracao media : mediasSO2) {
            seriesSO2.set(dia++, media.getValue());
        }

        LineChartSeries series03 = new LineChartSeries();
        series03.setLabel("O3");
        dia =1;
        for(Concentracao media : mediasO3) {
            series03.set(dia++, media.getValue());
        }
        model.addSeries(seriesSO2);
        model.addSeries(series03);

        return model;
    }




    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getAgrupamento() {
        return agrupamento;
    }

    public void setAgrupamento(String agrupamento) {
        this.agrupamento = agrupamento;
    }

    public String[] getSelectedParticula() {
        return selectedParticula;
    }

    public void setSelectedParticula(String[] selectedParticula) {
        this.selectedParticula = selectedParticula;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
}
