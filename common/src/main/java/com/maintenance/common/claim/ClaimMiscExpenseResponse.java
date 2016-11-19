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
    List<ClaimMiscExpense> expenses;

    
    public List<ClaimMiscExpense> getExpenses() {
        return expenses;
    }

    
    public void setExpenses(List<ClaimMiscExpense> expenses) {
        this.expenses = expenses;
    }
    
}
