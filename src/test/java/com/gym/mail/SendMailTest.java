package com.gym.mail;

import org.junit.Test;

/**
 * Created by Julia on 15.10.2016.
 */
public class SendMailTest {
    @Test
    public void testEmail() throws Exception {
        SendMail.generateAndSendEmail();
    }
}