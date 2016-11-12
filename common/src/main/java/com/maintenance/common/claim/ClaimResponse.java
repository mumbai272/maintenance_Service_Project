//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.claim;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ClaimResponse extends BaseResponse {

    @XmlElement
    List<ClaimBO> claims;


    public List<ClaimBO> getClaims() {
        return claims;
    }


    public void setClaims(List<ClaimBO> claims) {
        this.claims = claims;
    }

}
