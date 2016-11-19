//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.job;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class JobResponse extends BaseResponse {

    @XmlElement
    private List<JobBO> jobs;


    public List<JobBO> getJobs() {
        return jobs;
    }


    public void setJobs(List<JobBO> jobs) {
        this.jobs = jobs;
    }

}
