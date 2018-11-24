package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.auxiliar.ResourceDataAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.ResourceClient;
import br.ufma.lsdi.util.WebUtil;
import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Scope("view")
public class CadastrarRecursoBean {

    private ResourceClient resourceClient;
    private Resource resource;
    public List<String> capabilities;
    public List<String> capabilitiesSelected;
    public List<String> subCategoria;
    private String capability;
    public String categoria;


    Gson g = new Gson();

    public CadastrarRecursoBean(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    @PostConstruct
    public void init() {
        initObjects();
    }

    private void initObjects() {
        resource = new Resource();
        capabilitiesSelected = new ArrayList<>();
        capabilities = Arrays.asList("Balneabilidade", "Ozone", "ParticullateMatter25", "CarbonMonoxide");
        subCategoria = Arrays.asList("Novo Recurso", "Praia do Calhau", "Praia de São Marcos", "Praia Ponta D' Areia");
    }

    public void salvarRecurso() {
        ResourceDataAuxiliar data = new ResourceDataAuxiliar();
        resource.setCapabilities(capabilitiesSelected);
        data.setData(resource);
        ResourceDataAuxiliar resourceCreated = resourceClient.saveResource(data);
        //System.out.println(resourceCreated.getData().getUuid());

        if (resourceCreated != null) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(WebUtil.DADOS_SALVO));
            resource = new Resource();
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(WebUtil.ERROR_SALVAR));
        }

    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(List<String> subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> cities) {
        this.capabilities = cities;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public void setCapabilitiesSelected(List<String> capabilitiesSelected) {
        this.capabilitiesSelected = capabilitiesSelected;
    }

    public List<String> getCapabilitiesSelected() {
        return capabilitiesSelected;
    }
}
