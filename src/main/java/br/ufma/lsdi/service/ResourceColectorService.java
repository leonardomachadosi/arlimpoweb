package br.ufma.lsdi.service;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.util.Util;
import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class ResourceColectorService {
    private Gson gson = new Gson();

    public  ResourceHelper getDataResource(){
        ResourceHelper resourceHelper = new ResourceHelper() ;
        Catalog catalog = new Catalog();
        catalog.setCapabilities(Arrays.asList("PM25"));
        catalog.setUuids(Arrays.asList("8b1b3926-ccc9-4a41-8d6b-3baff7c0f520"));

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Catalog> request = new HttpEntity<>(catalog);
        //request.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<ResourceHelper> response = restTemplate
                .exchange(Util.URL_BASE+"collector/resources/data", HttpMethod.POST, request, ResourceHelper.class);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            resourceHelper = response.getBody();
        }

        return  resourceHelper;
    }




}


