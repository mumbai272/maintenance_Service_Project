//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.claim;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ClaimBusinessExpenseResponse extends BaseResponse {

    @XmlElement
    List<ClaimBusinessExpense> businessExpenses;

    
    public List<ClaimBusinessExpense> getBusinessExpenses() {
        return businessExpenses;
    }

    
    public void setBusinessExpenses(List<ClaimBusinessExpense> businessExpenses) {
        this.businessExpenses = businessExpenses;
    }


   

}
