package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.PointService;
import com.yes255.yes255booksusersserver.common.exception.PointException;
import com.yes255.yes255booksusersserver.common.exception.UserException;
import com.yes255.yes255booksusersserver.common.exception.payload.ErrorStatus;
import com.yes255.yes255booksusersserver.persistance.domain.Point;
import com.yes255.yes255booksusersserver.persistance.domain.PointLog;
import com.yes255.yes255booksusersserver.persistance.domain.User;
import com.yes255.yes255booksusersserver.persistance.repository.JpaPointLogRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaPointPolicyRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaPointRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaUserGradeRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaUserRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaUserTotalPureAmountRepository;
import com.yes255.yes255booksusersserver.presentation.dto.request.UpdateRefundRequest;
import com.yes255.yes255booksusersserver.presentation.dto.request.point.UpdatePointRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.point.PointResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.point.UpdatePointResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final JpaUserRepository userRepository;
    private final JpaUserGradeRepository userGradeRepository;
    private final JpaPointRepository pointRepository;
    private final JpaPointPolicyRepository pointPolicyRepository;
    private final JpaPointLogRepository pointLogRepository;
    private final JpaUserTotalPureAmountRepository totalAmountRepository;

    // 포인트 조회
    @Transactional(readOnly = true)
    public PointResponse findPointByUserId(Long userId) {

        Point point = pointRepository.findByUser_UserId(userId);

        if (Objects.isNull(point)) {
            throw new PointException(ErrorStatus.toErrorStatus("포인트가 존재하지 않습니다.", 400, LocalDateTime.now()));
        }

        return PointResponse.builder()
                .point(point.getPointCurrent())
                .build();
    }


    // 포인트 사용 및 적립
    @Override
    public UpdatePointResponse updatePointByUserId(Long userId, UpdatePointRequest pointRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.toErrorStatus("회원이 존재하지 않습니다.", 400, LocalDateTime.now())));

        // 입력 값 검증 및 치환
        BigDecimal usePoints = pointRequest.usePoints() != null && pointRequest.usePoints().compareTo(BigDecimal.ZERO) > 0
                ? pointRequest.usePoints()
                : BigDecimal.ZERO;

        BigDecimal amount = pointRequest.amount() != null && pointRequest.amount().compareTo(BigDecimal.ZERO) > 0
                ? pointRequest.amount()
                : BigDecimal.ZERO;

        Point point = pointRepository.findByUser_UserId(userId);

        // 순수 금액만큼 포인트 적립 및 사용 포인트 차감
        if (pointRequest.operationType().equals("use")) {

            BigDecimal tempPoint = point.getPointCurrent()
                    .add(amount.multiply(user.getUserGrade().getPointPolicy().getPointPolicyRate()))
                    .subtract(usePoints);

            if (tempPoint.compareTo(BigDecimal.ZERO) < 0) {
                throw new PointException(ErrorStatus.toErrorStatus("포인트가 부족합니다.", 400, LocalDateTime.now()));
            }

            point.updatePointCurrent(tempPoint);
            pointRepository.save(point);

            // 포인트로 구매 시 포인트 이력 추가
            if (usePoints.compareTo(BigDecimal.ZERO) > 0) {

                pointLogRepository.save(PointLog.builder()
                        .pointLogUpdatedAt(LocalDateTime.now())
                        .pointLogUpdatedType("사용")
                        .pointLogAmount(usePoints)
                        .point(point)
                        .build());
            }

            // 구매 시 구매 금액에 따른 포인트 적립 이력 추가
            if (amount.compareTo(BigDecimal.ZERO) > 0) {

                pointLogRepository.save(PointLog.builder()
                        .pointLogUpdatedAt(LocalDateTime.now())
                        .pointLogUpdatedType("적립")
                        .pointLogAmount(amount.multiply(user.getUserGrade().getPointPolicy().getPointPolicyRate()))
                        .point(point)
                        .build());
            }
        }
        // 사용한 포인트 적립 및 순수 금액만큼 포인트 차감
        else if (pointRequest.operationType().equals("rollback")) {

            BigDecimal tempPoint = point.getPointCurrent()
                    .subtract(amount.multiply(user.getUserGrade().getPointPolicy().getPointPolicyRate()))
                    .add(usePoints);

            if (tempPoint.compareTo(BigDecimal.ZERO) < 0) {
                throw new PointException(ErrorStatus.toErrorStatus("포인트가 부족합니다.", 400, LocalDateTime.now()));
            }

            point.updatePointCurrent(tempPoint);
            pointRepository.save(point);

            // 구매 시 사용한 포인트 적립
            if (usePoints.compareTo(BigDecimal.ZERO) > 0) {

                pointLogRepository.save(PointLog.builder()
                        .pointLogUpdatedAt(LocalDateTime.now())
                        .pointLogUpdatedType("취소 - 사용 포인트 적립")
                        .pointLogAmount(usePoints)
                        .point(point)
                        .build());
            }

            // 구매 시 적립한 포인트 차감
            if (amount.compareTo(BigDecimal.ZERO) > 0) {

                pointLogRepository.save(PointLog.builder()
                        .pointLogUpdatedAt(LocalDateTime.now())
                        .pointLogUpdatedType("취소 - 적립 포인트 차감")
                        .pointLogAmount(amount.multiply(user.getUserGrade().getPointPolicy().getPointPolicyRate()))
                        .point(point)
                        .build());
            }
        }

        return UpdatePointResponse.builder()
                .point(point.getPointCurrent())
                .build();
    }

    // 반품 포인트 적립
    @Override
    public void updatePointByRefund(Long userId, UpdateRefundRequest updateRefundRequest) {

        Point point = pointRepository.findByUser_UserId(userId);

        if (Objects.isNull(point)) {
            throw new PointException(ErrorStatus.toErrorStatus("포인트가 존재하지 않습니다.", 400, LocalDateTime.now()));
        }

        point.updatePointCurrent(point.getPointCurrent().add(updateRefundRequest.refundAmount()));

        pointRepository.save(point);

        // 포인트 이력 추가
        pointLogRepository.save(PointLog.builder()
                .pointLogUpdatedAt(LocalDateTime.now())
                .pointLogUpdatedType("반품 금액 적립")
                .pointLogAmount(updateRefundRequest.refundAmount())
                .point(point)
                .build());
    }
}
