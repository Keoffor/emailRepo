package com.example.emailSendGrid.service;

import com.example.emailSendGrid.Employee;
import com.example.emailSendGrid.EmployeeResponse;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    RestTemplate restTemplate;
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

    public EmployeeResponse addEmployee(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        try {


            String url = "http://localhost:8080/reimbursement/addEmployee";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Request Successful " + response.getStatusCode());
                employeeResponse.setEmail(employee.getEmail());
                employeeResponse.setFirstName(employee.getFirstName());
                response.getHeaders().getLocation();

            }else {
                logger.error("Request failed" + response.getStatusCode());
            }
        }catch (Exception e){
            logger.error("Unable to create employee " + e);
        }
        return employeeResponse;

    }

}
