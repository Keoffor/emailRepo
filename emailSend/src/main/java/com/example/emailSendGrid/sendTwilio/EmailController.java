package com.example.emailSendGrid.sendTwilio;

import com.example.emailSendGrid.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
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
