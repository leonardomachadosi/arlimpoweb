package br.ufma.lsdi.model.auxiliar;

import br.ufma.lsdi.model.interscity.Capability;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CapabilityAuxiliar  implements Serializable {

    private List<Capability> capabilities;
}
