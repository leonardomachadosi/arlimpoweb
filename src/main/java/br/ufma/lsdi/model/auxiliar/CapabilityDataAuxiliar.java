package br.ufma.lsdi.model.auxiliar;

import br.ufma.lsdi.model.interscity.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@JsonIgnoreProperties
@NoArgsConstructor
@AllArgsConstructor
public class CapabilityDataAuxiliar implements Serializable {

    private String value;
    private String timestamp;
    private Double lat;
    private Double lon;
    private Resource resource;


}
