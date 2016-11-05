//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.io.Serializable;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssetLogCreateRequest implements Serializable {

    @XmlElement
    @Valid
    private AssetLogDTO assetLog;


    public AssetLogDTO getAssetLog() {
        return assetLog;
    }


    public void setAssetLog(AssetLogDTO assetLog) {
        this.assetLog = assetLog;
    }


}
