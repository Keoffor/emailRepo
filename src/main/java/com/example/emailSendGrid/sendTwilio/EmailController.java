package com.example.emailSendGrid.sendTwilio;

import com.example.emailSendGrid.Employee;
import com.example.emailSendGrid.EmployeeResponse;
import com.example.emailSendGrid.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final Logger logger = LoggerFactory.getLogger(EmailController.class);
    @Autowired
    EmailService emailService;
    private static final String subject = "Approval Response Request Status";

//    @PostMapping(value = "/sendmail")
//    public ResponseEntity sendMail(@RequestBody ApprovalResponse request){
//        Response response = emailService.sendMail(request);
//        if(response !=null) {
//            return ResponseEntity.status(HttpStatus.OK).body("send successfully");
//        }else {
//           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("failed to send");
//        }
//    }
    @PostMapping(value = "/send")
    public ResponseEntity sendMail(@RequestBody String email) {
        emailService.sendMail(subject, email, "Hi "+ email + "\n"+
                "Your request has been sent and it is currently under review");
        return ResponseEntity.accepted().build();

    }

    @PostMapping(value = "/addEmployee",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEmail(@RequestBody Employee em) {
        EmployeeResponse response = new EmployeeResponse();
        try {
            if(em!=null){
                logger.info("Request to add new employee is proceeding to create");
                response = emailService.addEmployee(em);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                        buildAndExpand(response.getId()).toUri();
                logger.info("Request created new employee " + response.getFirstName());
            }else {
                logger.error("Create Customer: Response is null " + response);
            }
        } catch (Exception e){
            logger.error("Exception: failed to create customer", e);
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(response.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(value = "/approval")
    public ResponseEntity updateMail(@RequestBody ApprovalResponse res) {
        if (res.getStatus().equals(Status.APPROVED)) {
            emailService.sendMail(subject, res.getEmail(), "Hi " + res.getEmployeeName() + ", \n" +
                    "your request has been approved.");
            return ResponseEntity.accepted().build();
        } else if (res.getStatus().equals(Status.DENIED)) {
            emailService.sendMail(subject,res.getEmail(),"Hi " + res.getEmployeeName() + ", \n" +
                    "your request has been denied.");
            System.out.println(res.getEmail());
            return ResponseEntity.accepted().build();
        }
        else if(res.getStatus().equals(Status.REASSIGNED)){
            emailService.sendMail(subject, res.getEmail(),"Hi "+res.getEmployeeName()+", \n" +
                    "your request has been reassigned to another manager - " +res.getManagerFullname()+" for further action." );

            return ResponseEntity.accepted().build();

        }
        else{
            emailService.sendMail(subject,res.getEmail(),"Hi "+ res.getEmployeeName() + "\n"+
                    "Your request has been sent and it is currently under review");
            return ResponseEntity.accepted().build();
        }
    }
}
