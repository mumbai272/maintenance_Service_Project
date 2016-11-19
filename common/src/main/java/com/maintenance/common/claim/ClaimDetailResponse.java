//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.claim;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ClaimDetailResponse extends BaseResponse {

    @XmlElement
    private ClaimBO claim;

    @XmlElement
    private List<ClaimBusinessExpense> businessExpenses;

    @XmlElement
    private List<ClaimConveyanceExpense> conveyanceExpenses;

    @XmlElement
    private List<ClaimMiscExpense> miscExpenses;


    public ClaimBO getClaim() {
        return claim;
    }


    public void setClaim(ClaimBO claim) {
        this.claim = claim;
    }


    public List<ClaimBusinessExpense> getBusinessExpenses() {
        return businessExpenses;
    }


    public void setBusinessExpenses(List<ClaimBusinessExpense> businessExpenses) {
        this.businessExpenses = businessExpenses;
    }


    public List<ClaimConveyanceExpense> getConveyanceExpenses() {
        return conveyanceExpenses;
    }


    public void setConveyanceExpenses(List<ClaimConveyanceExpense> conveyanceExpenses) {
        this.conveyanceExpenses = conveyanceExpenses;
    }


    public List<ClaimMiscExpense> getMiscExpenses() {
        return miscExpenses;
    }


    public void setMiscExpenses(List<ClaimMiscExpense> miscExpenses) {
        this.miscExpenses = miscExpenses;
    }



}
