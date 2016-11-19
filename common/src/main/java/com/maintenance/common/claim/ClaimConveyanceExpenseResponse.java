//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.claim;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ClaimConveyanceExpenseResponse extends BaseResponse {
    @XmlElement
    List<ClaimConveyanceExpense> conveyanceExpenses;

    
    public List<ClaimConveyanceExpense> getConveyanceExpenses() {
        return conveyanceExpenses;
    }

    
    public void setConveyanceExpenses(List<ClaimConveyanceExpense> conveyanceExpenses) {
        this.conveyanceExpenses = conveyanceExpenses;
    }

    
   
    
    
}
