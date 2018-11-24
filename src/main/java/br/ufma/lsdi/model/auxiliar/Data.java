package br.ufma.lsdi.model.auxiliar;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

    private List<CapabilityDataAuxiliar> data;

    public List<CapabilityDataAuxiliar> getData() {
        return data;
    }

    public void setData(List<CapabilityDataAuxiliar> data) {
        this.data = data;
    }
}
