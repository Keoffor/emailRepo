package com.example.emailSendGrid.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    SendGrid sendGrid;

    public Response sendMail(String subject, String email, String message){
        Mail mail = new Mail(new Email("revaturedesk@gmail.com"),subject,new Email(email),
                new Content("text/plain",message));
        mail.setReplyTo(new Email(email));
        Request request = new Request();
        Response response =null;
            try {
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());
                response= this.sendGrid.api(request);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return response;
    }

}
