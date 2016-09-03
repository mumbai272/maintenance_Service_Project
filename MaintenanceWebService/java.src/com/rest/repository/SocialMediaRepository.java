package com.rest.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.rest.entity.SocialMediaImpl;
@Repository
public interface SocialMediaRepository extends CrudRepository<SocialMediaImpl, Integer> {

}
