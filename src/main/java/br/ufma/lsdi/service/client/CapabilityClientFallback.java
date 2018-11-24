package br.ufma.lsdi.service.client;

import br.ufma.lsdi.model.auxiliar.CapabilityAuxiliar;
import br.ufma.lsdi.model.auxiliar.Data;
import br.ufma.lsdi.model.interscity.Capability;
import br.ufma.lsdi.service.interscity.CapabilityClient;
import org.springframework.stereotype.Component;

@Component
public class CapabilityClientFallback implements CapabilityClient {


    @Override
    public CapabilityAuxiliar getCapability() {
        return new CapabilityAuxiliar();
    }

    @Override
    public Capability saveCapability(Capability capability) {
        return new Capability();
    }

    @Override
    public Capability getCapabilityByName(String name) {
        return new Capability();
    }

    @Override
    public Data saveCapabilityData(Data data, String uuid, String capability) {
        return new Data();
    }


}
