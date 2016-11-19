//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class AssetLogAssignmentResponse extends BaseResponse {

    @XmlElement
    List<AssetLogAssignmentBO> assetLogs ;
    
    public List<AssetLogAssignmentBO> getAssetLogs() {
        return assetLogs;
    }

    
    public void setAssetLogs(List<AssetLogAssignmentBO> assetLogs) {
        this.assetLogs = assetLogs;
    }
    
    


  
}
