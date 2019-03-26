package br.ufma.lsdi.service.interscity;

import br.ufma.lsdi.model.auxiliar.CapabilityAuxiliar;
import br.ufma.lsdi.model.auxiliar.Data;
import br.ufma.lsdi.model.interscity.Capability;
import br.ufma.lsdi.service.client.CapabilityClientFallback;
import br.ufma.lsdi.service.client.FeignInmateConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "capabilityClient",
       // url = "http://cidadesinteligentes.lsdi.ufma.br",
        url = "http://localhost:8080/paciente",
        fallback = CapabilityClientFallback.class,
        configuration = FeignInmateConfiguration.class
)
public interface CapabilityClient {

    @RequestMapping(method = RequestMethod.GET, value = "/catalog/capabilities")
    CapabilityAuxiliar getCapability();

    @RequestMapping(method = RequestMethod.POST, value = "/catalog/capabilities")
    Capability saveCapability(@RequestBody Capability capability);

    @RequestMapping(method = RequestMethod.GET, value = "/catalog/capabilities/{name}")
    Capability getCapabilityByName(@PathVariable("name") String name);

    @RequestMapping(method = RequestMethod.POST, value = "/adaptor/resources/{uuid}/data/{capability}")
    Data saveCapabilityData(@RequestBody Data data,
                            @PathVariable("uuid") String uuid,
                            @PathVariable("capability") String capability);

    @RequestMapping(method = RequestMethod.GET, value = "/{id")
    Object getPacienteObject(@PathVariable("id") Long id);



}
