package br.ufma.lsdi.model.auxiliar;

import br.ufma.lsdi.model.interscity.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class ResourceAuxiliar implements Serializable {

    private List<Resource> resources;

}
