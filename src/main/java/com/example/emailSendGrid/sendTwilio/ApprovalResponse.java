package com.example.emailSendGrid.sendTwilio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApprovalResponse {
    private Status status;
    private String email;
    private String managerFullname;

    private String employeeName;
    }


