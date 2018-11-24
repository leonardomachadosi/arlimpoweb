package br.ufma.lsdi.model.auxiliar;

import br.ufma.lsdi.model.interscity.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class ResourceDataAuxiliar implements Serializable {
    private Resource data;

}
