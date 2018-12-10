package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Concentracao;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.ResourceColectorService;
import br.ufma.lsdi.service.interscity.CapabilityClient;
import br.ufma.lsdi.util.ConcentracaoMediaUtil;
import br.ufma.lsdi.util.DateUtil;
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
    private Date dataInicio, dataFinal;
    private String agrupamento = "dia" ;
    private String[] selectedParticula;
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// HH:mm:ss");
    private LineChartModel lineModel;
    private ResourceColectorService colectorService;
    private List<Concentracao> mediasPM10 ;
    private List<Concentracao> mediasPM25 ;
    private List<Concentracao> mediasEnxofre ;
    private List<Concentracao> mediasNitrogenio ;
    private List<Concentracao> mediasOzonio ;
    String tipoGrafico;

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


    /**
     * Cria o modelo do gráfico de linha
     * @param title
     * @param labelX
     * @param max
     * @param min
     */
    private void createLineModels(String title, String labelX, int max, int min
    ) {
        //getDataResource();
        lineModel = initLinearModel();
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
        mediasPM10 = getMediasPorParticula(Util.PM10, resourceHelper);
         mediasPM25 = getMediasPorParticula(Util.PM25, resourceHelper);
         mediasEnxofre = getMediasPorParticula(Util.SULFURE_DIOXIDE, resourceHelper);
         mediasNitrogenio = getMediasPorParticula(Util.NITROGEN_DIOXIDE, resourceHelper);
         mediasOzonio = getMediasPorParticula(Util.OZONE, resourceHelper);

        createLineModels("Concentração Média","Dias",200,0);

    }
    
    /**
     * Monta a lista de valores das médias de cada partiula
     * @param particula
     */
    private List<Concentracao> getMediasPorParticula(String particula, ResourceHelper resourceHelper){
        List<Concentracao> medias = new ArrayList<>();

            if (agrupamento.equals("dia")) {
                medias = ConcentracaoMediaUtil.getConcentracaoDia(resourceHelper, dataInicio, particula);
            } else if (agrupamento.equals("mes")) {
                medias = ConcentracaoMediaUtil.getConcentracaoMes(resourceHelper, dataInicio, particula);
            } else {
                medias = ConcentracaoMediaUtil.getConcentracaoAno(resourceHelper, dataInicio, particula);
            }

        return medias;
    }

    /**
     * Busca dados de contexto
     * @return
     */
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
     * Cria o modelo e  injeta dados no grafico de linha
     * @param
     * @return model
     */
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        model.getAxes().put(AxisType.X, new CategoryAxis(agrupamento));
        LineChartSeries seriesPM10 ;
        LineChartSeries seriesPM25 ;
        LineChartSeries seriesSO2 ;
        LineChartSeries seriesO3 ;
        LineChartSeries seriesNO2 ;

        seriesPM10 = getChartSeries(mediasPM10);
        if (seriesPM10 != null) {
            seriesPM10.setLabel(Util.PM10);
            model.addSeries(seriesPM10);
        }
        seriesPM25 = getChartSeries(mediasPM25);
        if (seriesPM25 != null) {
            seriesPM25.setLabel(Util.PM25);
            model.addSeries(seriesPM25);
        }
        seriesNO2 = getChartSeries(mediasNitrogenio);
        if (seriesNO2 != null) {
            seriesNO2.setLabel(Util.NITROGEN_DIOXIDE);
            model.addSeries(seriesNO2);
        }

        seriesSO2 = getChartSeries(mediasEnxofre);
        if (seriesSO2 != null) {
            seriesSO2.setLabel(Util.SULFURE_DIOXIDE);
            model.addSeries(seriesSO2);
        }
        seriesO3 = getChartSeries(mediasOzonio);
        if (seriesO3 != null) {
            seriesO3.setLabel(Util.OZONE);
            model.addSeries(seriesO3);
        }

        return model;
    }


    /**
     *  Monta um ChartSeries com base no agrupamento atual
     * @param listConcentracao
     * @return
     */
    private LineChartSeries getChartSeries(List<Concentracao> listConcentracao){
        LineChartSeries series = new LineChartSeries();
        if(listConcentracao != null && listConcentracao.size()>0) {
            for (Concentracao media : listConcentracao) {
                if (agrupamento.equals("dia")) {
                    series.set(DateUtil.getDay(media.getDate()) + "/" + DateUtil.getMonth(media.getDate()), media.getValue());
                } else if (agrupamento.equals("mes")) {
                    series.set(DateUtil.getMonth(media.getDate()) + "/" + DateUtil.getYear(media.getDate()), media.getValue());
                } else {
                    series.set(DateUtil.getYear(media.getDate()), media.getValue());
                }
            }
        }else{
            return null;
        }
        return series;
    }


    public void setAgrupamento(){

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

    public String getTipoGrafico() {
        return tipoGrafico;
    }

    public void setTipoGrafico(String tipoGrafico) {
        this.tipoGrafico = tipoGrafico;
    }
}
