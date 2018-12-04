package br.ufma.lsdi.model;

import java.io.Serializable;
import java.util.List;

public class Catalog implements Serializable {
    List<String> uuids;
    List<String> capabilities;

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }
}
