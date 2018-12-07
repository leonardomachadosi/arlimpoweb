package br.ufma.lsdi.service.client;

import br.ufma.lsdi.model.Catalog;
import br.ufma.lsdi.model.ResourceHelper;
import br.ufma.lsdi.model.auxiliar.ResourceAuxiliar;
import br.ufma.lsdi.model.auxiliar.ResourceDataAuxiliar;
import br.ufma.lsdi.service.interscity.ResourceClient;
import org.springframework.stereotype.Component;

@Component
public class ResourceClientFallback implements ResourceClient {
    @Override
    public ResourceAuxiliar getResource() {
        return new ResourceAuxiliar();
    }

    @Override
    public ResourceDataAuxiliar saveResource(ResourceDataAuxiliar data) {
        return new ResourceDataAuxiliar();
    }

    @Override
    public ResourceDataAuxiliar getResourceByUuid(String uuid) {
        return new ResourceDataAuxiliar();
    }

    @Override
    public ResourceAuxiliar getAllResourceByCapability() {
        return new ResourceAuxiliar();
    }

    @Override
    public ResourceHelper getLastData(Catalog catalog) {
        return new ResourceHelper();
    }

    @Override
    public ResourceAuxiliar getAllResourceSensor() {
        return new ResourceAuxiliar();
    }


}
