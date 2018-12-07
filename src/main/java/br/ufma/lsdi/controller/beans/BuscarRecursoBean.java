package br.ufma.lsdi.controller.beans;

import br.ufma.lsdi.model.Matcher;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.model.auxiliar.ResourceDataAuxiliar;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.service.ResourceColectorService;
import br.ufma.lsdi.service.interscity.ResourceClient;
import br.ufma.lsdi.util.DateUtil;
import br.ufma.lsdi.util.JsonUtil;
import br.ufma.lsdi.util.Util;
import br.ufma.lsdi.util.WebUtil;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.omnifaces.util.Messages;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Scope("view")
public class BuscarRecursoBean {

    private ResourceClient resourceClient;

    private List<Resource> resources;

    private Resource resource;

    private ResourceColectorService colectorService;

    public BuscarRecursoBean(ResourceClient resourceClient, ResourceColectorService colectorService) {
        this.resourceClient = resourceClient;
        this.colectorService = colectorService;
    }

    @PostConstruct
    public void init() {
        initObjects();
        carregarTodasOsRecursos();
    }

    private void initObjects() {
        resource = new Resource();
        resources = new ArrayList<>();
    }

    private void carregarTodasOsRecursos() {
        ResourceAuxiliar auxiliar = resourceClient.getResource();
        resources = auxiliar.getResources();
    }

    public void buscarRecursoByUuid() {
        if (resource.getUuid() != null) {
            ResourceDataAuxiliar auxiliar = resourceClient.getResourceByUuid(resource.getUuid().trim().toLowerCase());
            Resource resourceData = auxiliar.getData();
            Resource resource = new Resource();
            resources = new ArrayList<>();
            if (resourceData.getId() != null) {
                Gson gson = new Gson();
                String re = gson.toJson(resourceData);
                resource = gson.fromJson(re, Resource.class);

            }

            if (resource.getId() != null) {
                resources.add(resource);
            }
            Messages.addGlobalInfo(WebUtil.BUSCAR_SUCESSO);
        } else {
            Messages.addGlobalWarn(WebUtil.BUSCAR_OBRIGATORIO);
        }
    }



    /**
     * Abrir tela de cadastro de laudo técnico
     * @param resource
     * @return
     */
    public String criarLaudo(Resource resource){
        WebUtil.flashScope().put("resource", resource);
        return "pretty:cadastrarLaudo";
    }

    /**
     * Abrir tela de visualização de recurso
     * @param resource
     * @return
     */
    public String visualizarRecurso(Resource resource){
        WebUtil.flashScope().put("resource", resource);
        return "pretty:visualizarRecurso";
    }


    public ResourceHelper getDataResource(){
        ResourceHelper resourceHelper = new ResourceHelper();
        List<String> capabilities = Arrays.asList("PM25");
        List<Matcher> matchers = new ArrayList<>();
        Matcher matcher = new Matcher("ano.eq", 2015);
        matchers.add(matcher);
        matcher = new Matcher("ano.lte", 2015);
        matchers.add(matcher);

        /*
        matcher = new Matcher("mes.gte", 05);
        matchers.add(matcher);
        matcher = new Matcher("mes.lte", 06);
        matchers.add(matcher);

        matcher = new Matcher("dia.gte", 10);
        matchers.add(matcher);
        matcher = new Matcher("dia.lte", 10);
        matchers.add(matcher);
        */
        String jsonMatcher = JsonUtil.getjsonMatches(matchers,capabilities);

           // resourceHelper = colectorService.findResources(jsonMatcher,"907850e5-e6ef-4958-9e83-1e461a535355");

        return resourceHelper;
    }



    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
