package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.GetDataContextResource;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.auxiliar.Data;
import br.ufma.lsdi.model.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.CapabilityClient;
import br.ufma.lsdi.service.interscity.ResourceClient;
import br.ufma.lsdi.util.Util;
import br.ufma.lsdi.util.WebUtil;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@Scope("view")
public class CadastrarLaudoBean {

    private CapabilityClient capabilityClient;
    private Resource resource;
    private String timesatamp;
    private String value;
    private Catalog catalog;

    private ResourceAuxiliar resourceAuxiliar;
    private ResourceClient resourceClient;

    private List<String> uuids;
    private List<String> capabilities;
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;
    private List<Resource> resources;

    public CadastrarLaudoBean(CapabilityClient capabilityClient, ResourceClient resourceClient) {
        this.capabilityClient = capabilityClient;
        this.resourceClient = resourceClient;
    }

    @PostConstruct
    public void init() {
        initObjects();
        carregarRecursos();
    }

    private void initObjects() {
        getObjetoFlashScope();
        capabilityDataAuxiliars = new ArrayList<>();
        uuids = new ArrayList<>();
        capabilities = new ArrayList<>();
        resources = new ArrayList<>();
    }

    public void salvarLaudo() {
        Data data = new Data();
        List<CapabilityDataAuxiliar> listData = new ArrayList<>();
        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar();
        dataAuxiliar.setTimestamp(timesatamp);
        dataAuxiliar.setValue(value);
        dataAuxiliar.setLat(resource.getLat());
        dataAuxiliar.setLon(resource.getLon());
        dataAuxiliar.setResource(resource);
        listData.add(dataAuxiliar);
        data.setData(listData);

        capabilityClient.saveCapabilityData(data, resource.getUuid(), "BALNEABILIDADE");

        timesatamp = "";
        value = "";

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(WebUtil.DADOS_SALVO));

    }

    public void salvarAllLaudo() {

        try {
            List<String> lista = Arrays.asList("05-07-2018.csv", "12-07-2018.csv", "19-07-2018.csv", "26-07-2018.csv", "02-08-2018.csv", "09-08-2018.csv", "16-08-2018.csv", "23-08-2018.csv", "30-08-2018.csv", "06-09-2018.csv", "13-09-2018.csv", "20-09-2018.csv", "27-09-2018.csv", "04-10-2018.csv", "11-10-2018.csv", "18-10-2018.csv", "25-10-2018.csv", "01-11-2018.csv", "08-11-2018.csv", "14-11-2018.csv", "22-11-2018.csv", "28-11-2018.csv", "06-12-2018.csv", "13-12-2018.csv");
            for (String s : lista) {
                List<String[]> linhas = Util.lerAquivoBalneabilidade(s);
                Gson gson = new Gson();
                List<CapabilityDataAuxiliar> dataAuxiliars = new ArrayList<>();

                for (String[] linha : linhas) {
                    Resource resource = new Resource();
                    resource = getResourceByName(linha[6]);
                    if (resource != null) {
                        if (resource.getUuid().trim().equals(linha[6].trim())) {
                            CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar();
                            dataAuxiliars = new ArrayList<>();
                            dataAuxiliar.setValue(linha[2]);
                            dataAuxiliar.setResource(resource);
                            dataAuxiliar.setLon(resource.getLon());
                            dataAuxiliar.setLat(resource.getLat());
                            dataAuxiliar.setTimestamp(linha[3].replaceAll("[\"]", ""));
                            dataAuxiliars.add(dataAuxiliar);
                            Data data = new Data();
                            data.setData(dataAuxiliars);
                            capabilityClient.saveCapabilityData(data,
                                    dataAuxiliar.getResource().getUuid(),
                                    "BALNEABILIDADE");
                        }
                    }

                }
            }

            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(WebUtil.DADOS_SALVO));

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Resource getResourceByName(String nome) {
        for (Resource resource : resources) {
            if (resource.getUuid().trim().equals(nome.trim())) {
                return resource;
            }
        }
        return null;
    }

    public void getObjetoFlashScope() {

        if (WebUtil.flashScope().get("resource") != null) {
            resource = (Resource) WebUtil.flashScope().get("resource");
        } else {
            redirect();
        }
    }

    private void carregarRecursos() {
        resourceAuxiliar = resourceClient.getAllResourceByCapability();
        if (resourceAuxiliar != null && !resourceAuxiliar.getResources().isEmpty()) {
            for (Resource re : resourceAuxiliar.getResources()) {
                if (re.getLat() != null) {
                    for (String cap : re.getCapabilities()) {
                        if (cap.equals("BALNEABILIDADE")) {
                            resources.add(re);
                        }
                    }
                }
            }
        }

    }

    private void getLastData(Catalog catalog) {
        ResourceHelper resourceHelper = resourceClient.getLastData(catalog);
        if (resourceHelper != null && resourceHelper.getResources() != null) {

            for (GetDataContextResource getDataContextResource : resourceHelper.getResources()) {
                Map<String, List<Map<String, Object>>> capability = getDataContextResource.getCapabilities();

                List<Map<String, Object>> data = capability.get("BALNEABILIDADE");

                if (data != null) {
                    for (Map<String, Object> cap : data) {

                        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar(cap);
                        if (dataAuxiliar.getLat() != null) {
                            if (!dataAuxiliar.getTimestamp().equals("2015-12-03T17:52:25.428Z")) {
                                capabilityDataAuxiliars.add(dataAuxiliar);
                            }
                        }
                    }
                }
            }
        }
    }

    public void redirect() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirect = "pretty:buscaRecurso";
        NavigationHandler myNav = facesContext.getApplication().getNavigationHandler();
        myNav.handleNavigation(facesContext, null, redirect);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getTimesatamp() {
        return timesatamp;
    }

    public void setTimesatamp(String timesatamp) {
        this.timesatamp = timesatamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
