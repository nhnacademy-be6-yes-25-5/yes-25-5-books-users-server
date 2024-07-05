package com.yes255.yes255booksusersserver.application.service.scheduler;

import com.yes255.yes255booksusersserver.application.service.UserService;
import com.yes255.yes255booksusersserver.application.service.queue.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayCouponScheduler {

    private final UserService userService;
    private final MessageProducer messageProducer;

    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 00:00에 실행
    public void sendBirthdayCoupons() {
        List<Long> userIdsWithBirthdays = userService.findUserIdsWithBirthdaysInCurrentMonth();
        for (Long userId : userIdsWithBirthdays) {
            messageProducer.sendBirthdayCouponMessage(userId);
        }
        log.info("Birthday coupons sent to users: {}", userIdsWithBirthdays);
    }
}