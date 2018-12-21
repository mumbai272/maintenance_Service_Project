package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anand on 20-Nov-16.
 */
public class ReportSpareResponseDTO implements Serializable{
    List<ReportSpareDTO> spares;
    private Double spareTotal;



    public List<ReportSpareDTO> getSpares() {
        return spares;
    }

    public void setSpares(List<ReportSpareDTO> spares) {
        this.spares = spares;
    }

    public Double getSpareTotal() {
        return spareTotal;
    }

    public void setSpareTotal(Double spareTotal) {
        this.spareTotal = spareTotal;
    }
}
