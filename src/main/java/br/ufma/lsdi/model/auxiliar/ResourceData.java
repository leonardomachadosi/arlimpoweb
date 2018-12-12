package br.ufma.lsdi.model.auxiliar;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties
public class ResourceData {

    private String uuid;
    private List<CapabilityDataAuxiliar> capabilityDataAuxiliars;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<CapabilityDataAuxiliar> getCapabilityDataAuxiliars() {
        if (capabilityDataAuxiliars == null) {
            capabilityDataAuxiliars = new ArrayList<>();
        }
        return capabilityDataAuxiliars;
    }

    public void setCapabilityDataAuxiliars(List<CapabilityDataAuxiliar> capabilityDataAuxiliars) {
        this.capabilityDataAuxiliars = capabilityDataAuxiliars;
    }
}
