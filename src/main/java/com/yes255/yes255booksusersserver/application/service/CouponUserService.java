package com.yes255.yes255booksusersserver.application.service;

import com.yes255.yes255booksusersserver.persistance.domain.CouponUser;
import com.yes255.yes255booksusersserver.presentation.dto.request.couponuser.UpdateCouponRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.couponuser.CouponBoxResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.couponuser.ReadMaximumDiscountCouponResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.couponuser.ReadUserCouponResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponUserService {

    void createCouponUser(Long couponId, Long userId);

    Page<CouponBoxResponse> getUserCoupons(Long userId, Pageable pageable);

    Page<CouponBoxResponse> getStateUserCoupons(Long userId, CouponUser.UserCouponStatus userCouponStatus, Pageable pageable);

    void updateCouponState(Long useId, List<UpdateCouponRequest> couponRequests);

    void checkExpiredCoupon();

    void deleteExpiredCoupons();

    void createCouponUserForBirthday(Long userId);

    void createCouponUserForWelcome(Long userId);

    List<ReadUserCouponResponse> getAllUserCouponsByUserId(Long userId);

    ReadMaximumDiscountCouponResponse getMaximumDiscountCouponByUserId(Long userId, Integer couponRequest);
}
