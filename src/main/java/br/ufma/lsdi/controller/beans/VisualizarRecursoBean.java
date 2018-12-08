package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Concentracao;
import br.ufma.lsdi.model.GetDataContextResource;
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
import java.text.ParseException;
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
                                  List<Concentracao> mediasPM10) {
        lineModel = initLinearModel(mediasPM10);
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


        List<Concentracao> mediasPM10 = getConcentracaoMedia(resourceHelper);

        createLineModels("Concentração Média","Dias",200,0, mediasPM10);

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
     * @param resourceHelper
     * @return
     */
    private List<Concentracao> getConcentracaoMedia( ResourceHelper resourceHelper){
        List<CapabilityDataAuxiliar> listDataAux = new ArrayList<>();
        List<Concentracao> concentracaos = new ArrayList<>();
        Concentracao concentracaoMedia ;

        if (resourceHelper != null && resourceHelper.getResources() != null) {

            for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                List<Map<String, Object>> dataSensor = capability.get("PM10");

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
                Date data1  ;
                Date data2 ;

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

        Double media =0.0;
        int aux = 0;

        for (CapabilityDataAuxiliar data : listDataAux){
            if (data.getValue() != null ){
                if (agrupamento.equals("dia")){
                    int dia = DateUtil.getDay(dataInicio);
                    if (dia == DateUtil.getDay(DateUtil.convertTimestampData(data.getTimestamp()))){
                        media += Double.parseDouble(data.getValue());
                        aux++;
                    }else{
                        concentracaoMedia = new Concentracao();
                        concentracaoMedia.setAgrupamento(agrupamento);
                        concentracaoMedia.setValue(media/aux);
                        concentracaoMedia.setDate(DateUtil.convertTimestampData(data.getTimestamp()));
                        concentracaos.add(concentracaoMedia);
                        dia = DateUtil.getDay(DateUtil.convertTimestampData(data.getTimestamp()));
                        media =0.0;
                        aux = 0;
                    }
                }
            }
        }


        return concentracaos;
    }


    /**
     * Cria o modelo e  injeta dados no grafico de linha
     * @param mediasPM10
     * @return model
     */
    private LineChartModel initLinearModel(List<Concentracao> mediasPM10) {
        LineChartModel model = new LineChartModel();
        LineChartSeries seriesPM10 = new LineChartSeries();
        model.getAxes().put(AxisType.X, new CategoryAxis(agrupamento));
        seriesPM10.setLabel("PM10");
        int dia =1;
        for(Concentracao media : mediasPM10) {
            if (agrupamento.equals("dia")) {
                seriesPM10.set(DateUtil.getDay(media.getDate())+"/"+DateUtil.getMonth(media.getDate()), media.getValue());
            }else if(agrupamento.equals("mes")){
                seriesPM10.set(DateUtil.getMonth(media.getDate())+"/"+DateUtil.getYear(media.getDate()), media.getValue());
            }else{
                seriesPM10.set(DateUtil.getYear(media.getDate()), media.getValue());
            }
        }

        model.addSeries(seriesPM10);

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
