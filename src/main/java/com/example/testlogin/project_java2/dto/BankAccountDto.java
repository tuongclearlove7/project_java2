package com.example.testlogin.project_java2.dto;


import com.example.testlogin.project_java2.constant.EnumConstant;
import com.example.testlogin.project_java2.model.UserAccount;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BankAccountDto {


    private String id;
    @NotEmpty(message = "amount should not be empty!")
    private double amount;
    @NotEmpty(message = "status should not be empty!")
    private EnumConstant status;

    private UserAccount userAccount;
}
