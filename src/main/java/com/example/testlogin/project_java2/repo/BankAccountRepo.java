package com.example.testlogin.project_java2.repo;

import com.example.testlogin.project_java2.model.BankAccount;
import com.example.testlogin.project_java2.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount,String> {


}
