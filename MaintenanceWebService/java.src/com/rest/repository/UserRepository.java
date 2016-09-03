package com.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.rest.entity.UserImpl;
@Component
public interface UserRepository extends CrudRepository<UserImpl, Integer> {

}
