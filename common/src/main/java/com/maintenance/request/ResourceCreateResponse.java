//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResourceCreateResponse extends BaseResponse {

    private Long id;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

}
