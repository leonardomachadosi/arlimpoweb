package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.CapabilityClient;
import br.ufma.lsdi.util.WebUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import java.util.Date;

@Controller
@Scope("view")
public class VisualizarRecursoBean {


    private CapabilityClient capabilityClient;
    private Resource resource;
    private Date dataInicio, dataFinal;
    private String agrupamento;

    public VisualizarRecursoBean(CapabilityClient capabilityClient) {
        this.capabilityClient = capabilityClient;
    }

    @PostConstruct
    public void init() {
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
}
