package br.ufma.lsdi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollutionData {
    private String station;
    private String date;
    private Double nitrogenDioxideNO2;
    private Double sulphurDioxideSO2;
    private Double ozoneO3;
    private Double particlesPM25;
    private Double particlesPM10;
}
