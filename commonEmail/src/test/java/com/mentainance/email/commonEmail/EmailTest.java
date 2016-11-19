package com.mentainance.email.commonEmail;



import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.maintenance.email.EmailType;
import com.maintenance.email.sender.EmailContent;
import com.maintenance.email.sender.EmailSender;

/**
 * Unit test for simple App.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/email-spring-config.xml"})
public class EmailTest{
    
    @Autowired
    EmailSender emailSender;
    
    @Test
    public void testEmail(){
        EmailContent emailContent=new EmailContent();
        emailContent.addTo("vinayak.s.mumbai@gmail.com");
        emailContent.setFrom("vinayak.s.mumbai@gmail.com");
        emailContent.setSubject("Testing mail");
        emailContent.setEmailType(EmailType.ADD_USER_HTML_EMAIL);
        emailContent.addModel("name", "testUser");
        emailContent.addModel("username", "testUsername");
        emailContent.addModel("password", "testUserpwd");
        emailSender.sendMail(emailContent);
    }
}
