package com.example.testlogin.project_java2.service.worker;

import com.example.testlogin.project_java2.constant.EnumConstant;
import com.example.testlogin.project_java2.dto.BankAccountDto;
import com.example.testlogin.project_java2.mapper.BankAccountMapper;
import com.example.testlogin.project_java2.model.BankAccount;
import com.example.testlogin.project_java2.model.UserAccount;
import com.example.testlogin.project_java2.repo.BankAccountRepo;
import com.example.testlogin.project_java2.repo.UserRepo;
import com.example.testlogin.project_java2.security.Security;
import com.example.testlogin.project_java2.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankAccountWorker implements BankAccountService {


    private BankAccountRepo bankAccountRepo;
    private UserRepo userRepo;


    @Override
    public BankAccountDto active_bank_account(UserAccount user) {

        Optional<UserAccount> optionalUser = userRepo.findById(user.getId());

        if (optionalUser.isPresent()) {

            UserAccount existingUser = optionalUser.get();
            System.out.println("User id: " + existingUser.getId());
            System.out.println("User active Bank account: " + existingUser.getEmail());

            BankAccountDto bankAccountDto = new BankAccountDto();
            BankAccount bankAccount = new BankAccount();
            bankAccount.setId(existingUser.getId());
            bankAccount.setAmount(0);
            bankAccount.setStatus(EnumConstant.isACTIVE);
            bankAccount.setUserAccount(existingUser);
            BankAccount newBankAccount = bankAccountRepo.save(bankAccount);

            bankAccountDto.setId(newBankAccount.getId());
            bankAccountDto.setAmount(newBankAccount.getAmount());
            bankAccountDto.setStatus(newBankAccount.getStatus());
            bankAccountDto.setUserAccount(existingUser);
            System.out.println("New BankAccount: " + bankAccountDto);

            return bankAccountDto;
        }
        return  null;
    }

    @Override
    public List<BankAccountDto> listBankAccountApi() {

        List<BankAccount> bankAccountDtoList = bankAccountRepo.findAll();

        return bankAccountDtoList.stream().map(
                BankAccountMapper::mapToBankAccountApiDto
        ).collect(Collectors.toList());
    }
}
