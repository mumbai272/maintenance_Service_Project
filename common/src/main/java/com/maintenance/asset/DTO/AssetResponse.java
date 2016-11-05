//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public final class AssetResponse extends BaseResponse {

    @XmlElement(name="asset")
    @XmlElementWrapper(name="assets")
    private List<AssetDTO> assets;


    public List<AssetDTO> getAssets() {
        return assets;
    }


    public void setAssets(List<AssetDTO> assets) {
        this.assets = assets;
    }

    public void addAssets(AssetDTO asset) {
        if (this.assets == null) {
            this.assets = new ArrayList<AssetDTO>();
        }
        this.assets.add(asset);
    }

}
