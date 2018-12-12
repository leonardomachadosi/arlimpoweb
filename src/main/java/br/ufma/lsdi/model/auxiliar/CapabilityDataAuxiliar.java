package br.ufma.lsdi.model.auxiliar;

import br.ufma.lsdi.model.interscity.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonIgnoreProperties
public class CapabilityDataAuxiliar implements Serializable {

    private String value;
    private String timestamp;
    private Resource resource;
    private Double lat;
    private Double lon;
    private String name;
    private String label;
    private Long index;

    public CapabilityDataAuxiliar(Map<String, Object> capability) {
        try {
            this.value = (String) capability.get("value");
        } catch (Exception e) {
            this.value = String.valueOf((Double) capability.get("value"));
        }
        this.timestamp = (String) capability.get("date");
        this.lat = (Double) capability.get("lat");
        this.lon = (Double) capability.get("lon");
        Map<String, Object> resourceMap = (Map<String, Object>) capability.get("resource");

        Resource resource = new Resource();

        if (resourceMap != null) {
            for (String key : resourceMap.keySet()) {
                resource.setUuid((String) resourceMap.get("uuid"));
                resource.setDescription((String) resourceMap.get("description"));
                resource.setLat((Double) resourceMap.get("lat"));
                resource.setLon((Double) resourceMap.get("lon"));
            }
        }

        this.resource = resource;

    }


    public CapabilityDataAuxiliar() {
    }

}
