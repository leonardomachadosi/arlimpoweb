package br.ufma.lsdi.service;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.interscity.Resource;
import br.ufma.lsdi.util.DateUtil;
import br.ufma.lsdi.util.Util;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.val;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ResourceColectorService {
    private Gson gson = new Gson();

    public  ResourceHelper getDataResource(String jsonMatcher){
        ResourceHelper resourceHelper = new ResourceHelper() ;
        //Catalog catalog = new Catalog();
        // catalog.setCapabilities(Arrays.asList("NITROGEN_DIOXIDE"));
        // catalog.setUuids(Arrays.asList("70b8f4fe-3f17-4dcb-beff-cfb586fb344f"));


        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(jsonMatcher);
        //request.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<ResourceHelper> response = restTemplate
                .exchange(Util.URL_BASE+"collector/resources/907850e5-e6ef-4958-9e83-1e461a535355/data",
                        HttpMethod.POST, request, ResourceHelper.class);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            resourceHelper = response.getBody();
        }

        return  resourceHelper;
    }


    public ResourceHelper findResources(List<String> uuids, List<String> capabilities,
                                        Date inicio, Date fim) throws UnirestException {
        int anoInicio = DateUtil.getYear(inicio);
        int anoFim = DateUtil.getYear(fim);
        int mesInicio = DateUtil.getMonth(inicio);
        int mesFim = DateUtil.getMonth(fim);
        int diaInicio = DateUtil.getDay(inicio);
        int diaFim = DateUtil.getDay(fim);
        int contAno = (anoFim - anoInicio) +1;

        int totalDias = DateUtil.dataDiff(inicio, fim);


        ResourceHelper resourceHelper = new ResourceHelper();
        Catalog catalog = new Catalog();
        catalog.setCapabilities(capabilities);
        catalog.setUuids(uuids);
        catalog.setStart_date(""+anoInicio+"-"+mesInicio+"-"+diaInicio+"T01:00:00");
        catalog.setEnd_date(""+anoFim+"-"+mesFim+"-"+diaFim+"T01:00:00");
        gson = new Gson();

        val response = Unirest.post(Util.URL_BASE+"collector/resources/data")

                        .header("accept", "application/json")
                        .header("content-type", "application/json")
                        //.queryString("page", i)
                        .body((gson.toJson(catalog)))
                        .asJson().getBody().toString();
               resourceHelper = gson.fromJson(response, ResourceHelper.class);


        return resourceHelper;

    }

}


