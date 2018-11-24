package br.ufma.lsdi.service.client;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(
        name = "inmateClient",
        url = "https://ma.sac24.com.br/api/v1/ma-seap/localizacao",
        fallback = ResourceClientFallback.class,
        configuration = FeignInmateConfiguration.class
)
public interface InmateClient {

//    @RequestMapping(value = "/{cpf}", method = RequestMethod.GET)
//    Monitorado findInmate(@RequestHeader("Authorization") String token, @PathVariable("cpf") String cpf);
}
