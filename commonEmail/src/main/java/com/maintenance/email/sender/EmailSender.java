//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email.sender;



import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 22, 2016
 */
@Component
public interface EmailSender {

    /**
     * 
     * @param emailContent
     */
    public void sendMail(final EmailContent emailContent);

    /**
     * 
     * @param emailContents
     */
    public void sendMail(List<EmailContent> emailContents);

    /**
     * @param emailContents
     */
    @Async
    public void sendMailAsync(List<EmailContent> emailContents);

    /**
     * @param emailContent
     */
    @Async
    public void sendMailAsync(EmailContent emailContent);
}
