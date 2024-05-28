package com.example.testlogin.project_java2.model;


import com.example.testlogin.project_java2.constant.EnumConstant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bank_account")
@Getter
@Setter
public class BankAccount {


    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EnumConstant status;
    private double amount;

    @OneToOne
    @JoinColumn(name = "user_id")
    //annotion này giúp gỡ lỗi lặp vô hạn khi mapper qua tất cả các BankAccounts
    @JsonBackReference
    private UserAccount userAccount;


}
