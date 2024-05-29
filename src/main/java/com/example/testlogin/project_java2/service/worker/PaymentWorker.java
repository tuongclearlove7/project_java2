package com.example.testlogin.project_java2.service.worker;
import com.example.testlogin.project_java2.dto.PaymentDto;
import com.example.testlogin.project_java2.dto.UploadDto;
import com.example.testlogin.project_java2.mapper.PaymentMapper;
import com.example.testlogin.project_java2.model.BankAccount;
import com.example.testlogin.project_java2.model.Payment;
import com.example.testlogin.project_java2.model.Upload;
import com.example.testlogin.project_java2.model.UserAccount;
import com.example.testlogin.project_java2.repo.BankAccountRepo;
import com.example.testlogin.project_java2.repo.PaymentRepo;
import com.example.testlogin.project_java2.repo.UploadRepo;
import com.example.testlogin.project_java2.repo.UserRepo;
import com.example.testlogin.project_java2.security.Security;
import com.example.testlogin.project_java2.service.PaymentService;
import com.example.testlogin.project_java2.service.UploadService;
import net.minidev.json.JSONObject;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PaymentWorker implements PaymentService {


    private final UploadRepo uploadRepo;
    private final UserRepo userRepo;
    private final BankAccountRepo bankAccountRepo;
    private final PaymentRepo paymentRepo;
    private final OcrWorker ocrWorker;
    private final BankAccountWorker bankAccountWorker;
    private final UploadService uploadService;

    @Autowired
    public PaymentWorker(UploadRepo uploadRepo, UserRepo userRepo, BankAccountRepo bankAccountRepo, PaymentRepo paymentRepo, OcrWorker ocrWorker, BankAccountWorker bankAccountWorker, UploadService uploadService) {
        this.uploadRepo = uploadRepo;
        this.userRepo = userRepo;
        this.bankAccountRepo = bankAccountRepo;
        this.paymentRepo = paymentRepo;
        this.ocrWorker = ocrWorker;
        this.bankAccountWorker = bankAccountWorker;
        this.uploadService = uploadService;
    }

    @Override
    public List<PaymentDto> listPaymentApi() {

        List<Payment> payments = paymentRepo.findAll();

        return  payments.stream()
            .map(PaymentMapper::mapToPaymentApiDto)
            .collect(Collectors.toList());
    }


    @Override
    public PaymentDto create(File file, HttpSession session, JSONObject object,
                             UploadDto uploadDto, BankAccount bankAccountUser
    ) throws TesseractException {

        String dataScan = ocrWorker.getImageString(file);

        String account_number_sent_to = find_number_send(dataScan);
        String inherited_account_number = find_Inherited_account_number(dataScan);
        double amount = find_amount(dataScan);
        String content = find_content(dataScan);
        PaymentDto newPaymentDto = new PaymentDto();

        if(bankAccountUser == null){
            newPaymentDto.setId(null);
            newPaymentDto.setUser_bank_code(account_number_sent_to);
            newPaymentDto.setDeposit_amount(amount);
            newPaymentDto.setPayment_content(content);
            newPaymentDto.setBankAccount(null);
            newPaymentDto.setStatus_check_inherited_account_number(false);
            newPaymentDto.setStatus_check_content(false);
            newPaymentDto.setStatus_increase_amount(false);
            object.put("payment_status",false);
            object.put("error","Bạn chưa mở ví tiền vui lòng mở ví tiền để nạp tiền!");

            return newPaymentDto;
        }
        System.out.println("bankAccountUser: " + bankAccountUser.getId());
        System.out.println("account_number_sent_to: " + account_number_sent_to);
        System.out.println("inherited_account_number: " + inherited_account_number);
        System.out.println("amount: " + amount);
        System.out.println("content: " + content);

        if(bankAccountWorker.increase_amount_bank_account(amount,
            account_number_sent_to, bankAccountUser)){

            Payment userPayment = new Payment();
            Optional<Upload> uploadNew = uploadRepo.findById(uploadDto.getId());

            userPayment.setUser_bank_code(account_number_sent_to);
            userPayment.setDeposit_amount(amount);
            userPayment.setPayment_content(content);
            userPayment.setBankAccount(bankAccountUser);
            uploadNew.ifPresent(userPayment::setUpload);
            Payment newPaymentByUser = paymentRepo.save(userPayment);

            newPaymentDto.setId(newPaymentByUser.getId());
            newPaymentDto.setUser_bank_code(newPaymentByUser.getUser_bank_code());
            newPaymentDto.setStart_time(newPaymentByUser.getStart_time());
            newPaymentDto.setDeposit_amount(newPaymentByUser.getDeposit_amount());
            newPaymentDto.setPayment_content(newPaymentByUser.getPayment_content());
            Optional<BankAccount> bankAccountUpdated = bankAccountRepo.findById(bankAccountUser.getId());
            bankAccountUpdated.ifPresent(newPaymentDto::setBankAccount);

            newPaymentDto.setStatus_check_inherited_account_number(true);
            newPaymentDto.setStatus_check_content(true);
            newPaymentDto.setStatus_increase_amount(true);
            object.put("payment_status",true);
            return newPaymentDto;
        }

        newPaymentDto.setId(null);
        newPaymentDto.setUser_bank_code(account_number_sent_to);
        newPaymentDto.setDeposit_amount(amount);
        newPaymentDto.setPayment_content(content);
        newPaymentDto.setBankAccount(bankAccountUser);
        newPaymentDto.setStatus_check_inherited_account_number(false);
        newPaymentDto.setStatus_check_content(false);
        newPaymentDto.setStatus_increase_amount(false);
        object.put("error","Lỗi số tiền chưa được cộng vào tài khoản của bạn chúng tôi sẽ xem xét!");
        object.put("payment_status",false);

        return newPaymentDto;
    }

    public boolean check_inherited_account_number(String inherited_account_number){

        return Objects.equals(inherited_account_number, "104234245541");//104234245541
    }

    public boolean check_content(String content, HttpSession session){

        String token = (String) session.getAttribute("paymentToken");

        return Objects.equals(content, token);
    }

    @Override
    public String generate_token() {

        return "Product234234fsfsf3r3";//Product234234fsfsf3r3
    }

    private String find_number_send(String data) {
        Pattern pattern = Pattern.compile("Account number sent to:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public String find_Inherited_account_number(String data) {
        Pattern pattern = Pattern.compile("Inherited account number:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private double find_amount(String data) {
        Pattern pattern = Pattern.compile("Amount:\\s*([\\d.,]+)\\s*VND");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            String amountStr = matcher.group(1).replaceAll("[,.]", "");
            return Double.parseDouble(amountStr);
        }
        return 0.0;
    }

    public String find_content(String data) {
        Pattern pattern = Pattern.compile("Content:\\s*(.+)");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

}













