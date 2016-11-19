//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class AssetLogResponse extends BaseResponse {

    @XmlElement
    List<AssetLog> assetLogs ;


    public List<AssetLog> getAssetLogs() {
        return assetLogs;
    }


    public void setAssetLogs(List<AssetLog> assetLogs) {
        this.assetLogs = assetLogs;
    }

}
