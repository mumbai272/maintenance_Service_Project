//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email.sender;



import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 22, 2016
 */
@Component
public class EmailSenderImpl implements EmailSender {

    private static final Logger logger = Logger.getLogger(EmailSenderImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * 
     * @param emailContent
     */
    public void sendMail(final EmailContent emailContent) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(emailContent.toArray(emailContent.getTo()));
                message.setFrom(emailContent.getFrom());
                message.setSubject(emailContent.getSubject());
                if (CollectionUtils.isNotEmpty(emailContent.getBcc())) {
                    message.setBcc(emailContent.toArray(emailContent.getBcc()));
                }
                if (CollectionUtils.isNotEmpty(emailContent.getCc())) {
                    message.setCc(emailContent.toArray(emailContent.getCc()));
                }
                String body =
                    VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, emailContent
                            .getEmailType().getVelocityTempletFile(), "UTF-8", emailContent
                            .getModel());

                logger.info("body={}" + body);
                if (emailContent.getEmailType().getType().equalsIgnoreCase("html")) {
                    message.setText(body, true);
                } else {
                    message.setText(body);
                }
            }


        };

        mailSender.send(preparator);
    }

    /**
     * 
     * @param emailContents
     */
    public void sendMail(List<EmailContent> emailContents) {
        for (EmailContent emailContent : emailContents) {
            sendMail(emailContent);
        }
    }
    
   @Async
    public void sendMailAsync(List<EmailContent> emailContents) {
        for (EmailContent emailContent : emailContents) {
            sendMail(emailContent);
        }
    }
   @Async
    public void sendMailAsync(EmailContent emailContent) {        
            sendMail(emailContent);
    }
    
}
