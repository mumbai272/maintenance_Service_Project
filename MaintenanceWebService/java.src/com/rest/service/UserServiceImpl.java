//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.Common.RoleType;
import com.maintenance.login.requestResponse.UserDTO;
import com.rest.entity.UserImpl;
import com.rest.repository.UserRepository;


/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 16, 2016
 */
@Component
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    public UserDTO buildUserDTO(UserImpl user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        if (user.getRoleTypeId() != null) {
            userDTO.setRole(RoleType.getRoleType(user.getRoleTypeId()).getRole());
        }
        return userDTO;
    }

}
