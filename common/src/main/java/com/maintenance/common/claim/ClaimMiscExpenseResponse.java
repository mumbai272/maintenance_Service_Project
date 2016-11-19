//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.claim;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ClaimMiscExpenseResponse extends BaseResponse {
    @XmlElement
    List<ClaimMiscExpense> miscExpenses;

    
    public List<ClaimMiscExpense> getMiscExpenses() {
        return miscExpenses;
    }

    
    public void setMiscExpenses(List<ClaimMiscExpense> miscExpenses) {
        this.miscExpenses = miscExpenses;
    }

       
}
