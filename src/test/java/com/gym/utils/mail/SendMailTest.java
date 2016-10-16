package com.gym.utils.mail;

import com.gym.user.User;
import org.junit.Test;

/**
 * Created by Julia on 15.10.2016.
 */
public class SendMailTest {
    @Test
    public void testEmail() throws Exception {
        SendMail sendMail =new SendMail();
        sendMail.generateAndSendEmail(new User("Julia", "yandex@yandex.ru"), "google.com");
    }
}