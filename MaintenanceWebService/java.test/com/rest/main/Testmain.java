package com.rest.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rest.entity.Address;
import com.rest.entity.UserImpl;
import com.rest.repository.UserRepository;

public class Testmain {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		UserRepository repository=(UserRepository) context.getBean("userRepository");
//		UserImpl newUser=new UserImpl();
//		newUser.setUserName("vmumbai");
//		Address address1=new Address("bangalore",newUser);
//		Address address2=new Address("bijapur",newUser);
//		newUser.getAddress().add(address1);
//		newUser.getAddress().add(address2);
//		newUser=repository.save(newUser);
//		System.out.println(newUser);
		UserImpl user=repository.findOne(1);
		System.out.println(user);
	}

}
