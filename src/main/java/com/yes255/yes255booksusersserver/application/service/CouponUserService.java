package com.yes255.yes255booksusersserver.application.service;

public interface CouponUserService {

    void createCouponUser(Long couponId, Long userId);
    void createCouponUserForBirthday(Long userId);
    void createCouponUserForWelcome(Long userId);
}
