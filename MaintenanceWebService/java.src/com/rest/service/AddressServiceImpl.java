//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.repository.AddressRepository;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 4, 2016
 */
@Component
public class AddressServiceImpl {

    @Autowired
    private AddressRepository addressRepository;
}
