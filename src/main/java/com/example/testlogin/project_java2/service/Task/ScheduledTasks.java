package com.example.testlogin.project_java2.service.Task;

import com.example.testlogin.project_java2.model.UserAccount;
import com.example.testlogin.project_java2.security.Security;
import com.example.testlogin.project_java2.service.TemporaryService;
import com.example.testlogin.project_java2.service.UploadService;
import com.example.testlogin.project_java2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@EnableScheduling
@Component
public class ScheduledTasks {

    private final TemporaryService temporaryService;
    public ScheduledTasks(TemporaryService temporaryService) {
        this.temporaryService = temporaryService;
    }

    // Thực thi mỗi phút
    @Scheduled(fixedRate = 60000)
    public void deleteExpiredPaymentToken() {
        if(temporaryService.count() > 0){
            LocalDateTime timeLimit = LocalDateTime.now().minusMinutes(5);
            temporaryService.deleteOldPaymentToken(timeLimit);
        }
    }
}
