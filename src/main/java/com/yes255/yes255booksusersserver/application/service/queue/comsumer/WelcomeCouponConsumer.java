package com.yes255.yes255booksusersserver.application.service.queue.comsumer;

import com.yes255.yes255booksusersserver.application.service.CouponUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WelcomeCouponConsumer {

    private final CouponUserService couponUserService;

    @RabbitListener(queues = "welcomeCouponQueue")
    public void handleWelcomeCouponMessage(Long userId) {
        couponUserService.createCouponUserForWelcome(userId);
    }
}