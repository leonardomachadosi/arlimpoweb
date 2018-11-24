package br.ufma.lsdi.model.interscity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capability implements Serializable {

    private Long id;
    private String name;
    private int function;
    private String description;
    private String capability_type;

}
