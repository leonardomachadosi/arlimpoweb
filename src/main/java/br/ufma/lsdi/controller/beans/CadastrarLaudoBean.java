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
            List<String> lista = Arrays.asList("balneabilidade\\2015\\28-10-2015.csv");
            for (String s : lista) {
                List<String[]> linhas = Util.lerAquivoBalneabilidade(s);
                List<CapabilityDataAuxiliar> dataAuxiliars = new ArrayList<>();
                for (CapabilityDataAuxiliar capabilityDataAuxiliar : capabilityDataAuxiliars) {
                    if (capabilityDataAuxiliar.getResource() != null) {
                        for (String[] linha : linhas) {
                            if (capabilityDataAuxiliar.getResource().getDescription().trim().equals(linha[0])) {
                                CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar();
                                dataAuxiliar.setValue(linha[2]);
                                dataAuxiliar.setResource(capabilityDataAuxiliar.getResource());
                                dataAuxiliar.setLon(capabilityDataAuxiliar.getLon());
                                dataAuxiliar.setLat(capabilityDataAuxiliar.getLat());
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
            }

            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(WebUtil.DADOS_SALVO));

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
                            uuids.add(re.getUuid());
                        }
                    }
                }
            }
        }

        if (!uuids.isEmpty()) {
            catalog = new Catalog();
            capabilities.addAll(Arrays.asList("BALNEABILIDADE"));
            catalog.setCapabilities(capabilities);
            catalog.setUuids(uuids);
            try {
                getLastData(catalog);
            } catch (Exception e) {
                e.printStackTrace();
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
                            capabilityDataAuxiliars.add(dataAuxiliar);
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
