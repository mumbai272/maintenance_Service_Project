//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;


public abstract class BaseRestServiceImpl {
    
    @Context 
    protected MessageContext context;
}
