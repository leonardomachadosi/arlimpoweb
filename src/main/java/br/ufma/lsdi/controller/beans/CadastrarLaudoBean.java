package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.auxiliar.CapabilityDataAuxiliar;
import br.ufma.lsdi.model.auxiliar.Data;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.CapabilityClient;
import br.ufma.lsdi.util.WebUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("view")
public class CadastrarLaudoBean {

    private CapabilityClient capabilityClient;
    private Resource resource;
    private String timesatamp;
    private String value;


    public CadastrarLaudoBean(CapabilityClient capabilityClient) {
        this.capabilityClient = capabilityClient;
    }

    @PostConstruct
    public void init() {
        initObjects();
    }

    private void initObjects() {
        getObjetoFlashScope();
    }

    public void salvarLaudo() {
        Data data = new Data();
        List<CapabilityDataAuxiliar> listData = new ArrayList<>();
        CapabilityDataAuxiliar dataAuxiliar = new CapabilityDataAuxiliar();
        dataAuxiliar.setTimestamp(timesatamp);
        dataAuxiliar.setValue(value);
        //dataAuxiliar.setLat(resource.getLat());
        //dataAuxiliar.setLon(resource.getLon());
        dataAuxiliar.setResource(resource);
        listData.add(dataAuxiliar);
        data.setData(listData);

        capabilityClient.saveCapabilityData(data, resource.getUuid(), "Balneabilidade");

        timesatamp = "";
        value = "";

        FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(WebUtil.DADOS_SALVO));

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
