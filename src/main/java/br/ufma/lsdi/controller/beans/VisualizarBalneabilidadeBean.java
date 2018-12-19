package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.Concentracao;
import br.ufma.lsdi.model.GetDataContextResource;
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
public class VisualizarBalneabilidadeBean {


    private CapabilityClient capabilityClient;
    private Resource resource;
    private Date dataInicio, dataFinal;
    private ResourceColectorService colectorService;
    private List<CapabilityDataAuxiliar> capabilityDataList;

    public VisualizarBalneabilidadeBean(CapabilityClient capabilityClient, ResourceColectorService colectorService) {
        this.capabilityClient = capabilityClient;
        this.colectorService = colectorService;
    }

    @PostConstruct
    public void init() {
        initObjects();
    }

    private void initObjects() {
        getObjetoFlashScope();
        capabilityDataList = new ArrayList<>();
        try {
            getHistorico(resource.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
        }
       if (capabilityDataList == null){
           capabilityDataList = new ArrayList<>();
       }

    }


    public boolean isProprio(String value){
        if (value.equals("PRÓPRIO") || value.equals("PROPRIO")){
            return true;
        }
        return false;
    }

    public boolean isImproprio(String value){
        if (value.equals("IMPRÓPRIO") || value.equals("IMPROPRIO") || value.equals("IMPRÃ“PRIO") ){
            return true;
        }
        return false;
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




    public void getHistorico( String uuid) throws Exception {
        Catalog catalog = new Catalog();
        catalog.setCapabilities(Arrays.asList("BALNEABILIDADE"));

        ResourceHelper resourceHelper = new ResourceHelper();
        resourceHelper = getDataResource();
        Set<CapabilityDataAuxiliar> capabilityDataAuxiliars = new HashSet<>();
        if (resourceHelper != null && resourceHelper.getResources() != null) {
            for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();
                List<Map<String, Object>> data = capability.get("BALNEABILIDADE");

                if (data != null) {
                    for (Map<String, Object> cap : data) {
                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                        if (dataAuxiliar.getLat() != null) {
                            if (!dataAuxiliar.getTimestamp().equals("2015-12-03T17:52:25.428Z")
                                    && !dataAuxiliar.getTimestamp().equals("2015-12-04T17:52:25.428Z")
                                    && !dataAuxiliar.getTimestamp().equals("2016-01-09T17:52:25.428Z")
                                    && !dataAuxiliar.getTimestamp().equals("2016-01-01T17:52:25.428Z")
                                    && !dataAuxiliar.getTimestamp().equals("2016-01-15T17:52:25.428Z")) {
                                dataAuxiliar.setName("BALNEABILIDADE");
                                capabilityDataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }

                    List<CapabilityDataAuxiliar> listData = new ArrayList<>();
                    listData.addAll(capabilityDataAuxiliars);

                    if (!capabilityDataAuxiliars.isEmpty()) {
                        Collections.sort(listData, new Comparator<CapabilityDataAuxiliar>() {
                            @Override
                            public int compare(CapabilityDataAuxiliar o1, CapabilityDataAuxiliar o2) {
                                Date date1;
                                Date date2;

                                date1 = DateUtil.convertTimestampData(o1.getTimestamp());
                                date2 = DateUtil.convertTimestampData(o2.getTimestamp());
                                if (date2.after(date1)) {
                                    return 1;
                                }
                                if (date2.before(date1)) {
                                    return -1;
                                }

                                return 0;
                            }
                        });

                        capabilityDataList = listData;
                    }
                }

            }
        }

    }


    /**
     * Busca dados de contexto
     * @return
     */
    public ResourceHelper getDataResource(){
        ResourceHelper resourceHelper = new ResourceHelper();
        List<String> capabilities = Arrays.asList("BALNEABILIDADE");
        List<String> uuids = new ArrayList<>();
        uuids.add(resource.getUuid());

        try {
            resourceHelper = colectorService.findResources(uuids,capabilities,dataInicio,dataFinal);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return resourceHelper;
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

    public List<CapabilityDataAuxiliar> getCapabilityDataList() {
        return capabilityDataList;
    }

    public void setCapabilityDataList(List<CapabilityDataAuxiliar> capabilityDataList) {
        this.capabilityDataList = capabilityDataList;
    }
}
