package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.CouponUserService;
import com.yes255.yes255booksusersserver.common.exception.UserException;
import com.yes255.yes255booksusersserver.common.exception.payload.ErrorStatus;
import com.yes255.yes255booksusersserver.infrastructure.adaptor.CouponAdaptor;
import com.yes255.yes255booksusersserver.persistance.domain.CouponUser;
import com.yes255.yes255booksusersserver.persistance.domain.User;
import com.yes255.yes255booksusersserver.persistance.repository.JpaCouponUserRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaUserRepository;
import com.yes255.yes255booksusersserver.presentation.dto.response.couponuser.ExpiredCouponUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@Service
@RequiredArgsConstructor
public class CouponUserServiceImpl implements CouponUserService {

    private final JpaUserRepository userRepository;
    private final JpaCouponUserRepository couponUserRepository;
    private final CouponAdaptor couponAdaptor;

    @Override
    public void createCouponUser(Long couponId, Long userId) {  // todo : 중복 제외 처리

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.toErrorStatus("회원이 존재하지 않습니다.", 400, LocalDateTime.now())));

        ExpiredCouponUserResponse couponUserResponse = couponAdaptor.getCouponExpiredDate(couponId);

        couponUserRepository.save(CouponUser.builder()
                        .userCouponType("일반")
                        .userCouponStatus(CouponUser.UserCouponStatus.ACTIVE)
                        .couponExpiredAt(couponUserResponse.couponExpiredAt())
                        .couponId(couponId)
                        .user(user)
                        .build());
    }

    @Override
    public void createCouponUserForBirthday(Long userId) {
        // 생일 쿠폰 정책 ID를 하드코딩하거나 설정 파일에서 불러옵니다.
        Long birthdayCouponPolicyId = 1L;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.toErrorStatus("회원이 존재하지 않습니다.", 400, LocalDateTime.now())));

        ExpiredCouponUserResponse couponUserResponse = couponAdaptor.getCouponExpiredDate(birthdayCouponPolicyId);

        couponUserRepository.save(CouponUser.builder()
                .userCouponType("생일")
                .userCouponStatus(CouponUser.UserCouponStatus.ACTIVE)
                .couponExpiredAt(couponUserResponse.couponExpiredAt())
                .couponId(birthdayCouponPolicyId)
                .user(user)
                .build());
    }

    @Override
    public void createCouponUserForWelcome(Long userId) {
        // 웰컴 쿠폰 정책 ID를 하드코딩하거나 설정 파일에서 불러옵니다.
        Long welcomeCouponPolicyId = 2L;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.toErrorStatus("회원이 존재하지 않습니다.", 400, LocalDateTime.now())));

        ExpiredCouponUserResponse couponUserResponse = couponAdaptor.getCouponExpiredDate(welcomeCouponPolicyId);

        couponUserRepository.save(CouponUser.builder()
                .userCouponType("웰컴")
                .userCouponStatus(CouponUser.UserCouponStatus.ACTIVE)
                .couponExpiredAt(couponUserResponse.couponExpiredAt())
                .couponId(welcomeCouponPolicyId)
                .user(user)
                .build());
    }

}
