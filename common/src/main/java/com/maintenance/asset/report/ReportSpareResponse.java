//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ReportSpareResponse extends BaseResponse {

    @XmlElement
    List<ReportSpareBO> spares;
    private Double spareTotal;


    public List<ReportSpareBO> getSpares() {
        return spares;
    }


    public void setSpares(List<ReportSpareBO> spares) {
        this.spares = spares;
    }


    
    public Double getSpareTotal() {
        return spareTotal;
    }


    
    public void setSpareTotal(Double spareTotal) {
        this.spareTotal = spareTotal;
    }
    

}
