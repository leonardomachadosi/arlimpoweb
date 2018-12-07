package br.ufma.lsdi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class ResourceHelper implements Serializable {

    List<GetDataContextResource> resources;

    public List<GetDataContextResource> getResources() {
        if(resources == null){
            resources = new ArrayList<>();
        }
        return resources;
    }

    public void setResources(List<GetDataContextResource> resources) {
        this.resources = resources;
    }
}
