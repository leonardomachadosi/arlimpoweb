package br.ufma.lsdi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties
public class ResourceHelper implements Serializable {

    List<GetDataContextResource> resources;

}
