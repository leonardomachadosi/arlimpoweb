package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.auxiliar.Data;
import br.ufma.lsdi.model.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.model.auxiliar.ResourceDataAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.interscity.ResourceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("view")
public class TelaAndersonBean {

    @Autowired
    private ResourceClient resourceClient;

    private ResourceAuxiliar resourceAuxiliar;

    private List<Resource> resources;

    @PostConstruct
    public void init() {
        resourceAuxiliar = new ResourceAuxiliar();
        resources = new ArrayList<>();
        getRecurso();
    }


    public void getRecurso() {

        resourceAuxiliar = resourceClient.getResource();
        if (resourceAuxiliar != null && resourceAuxiliar.getResources() != null) {
            resources = resourceAuxiliar.getResources();
        }

    }


    public ResourceAuxiliar getResourceAuxiliar() {
        return resourceAuxiliar;
    }

    public void setResourceAuxiliar(ResourceAuxiliar resourceAuxiliar) {
        this.resourceAuxiliar = resourceAuxiliar;
    }

    public ResourceClient getResourceClient() {
        return resourceClient;
    }

    public void setResourceClient(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
