package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.PointLogService;
import com.yes255.yes255booksusersserver.persistance.domain.PointLog;
import com.yes255.yes255booksusersserver.persistance.repository.JpaPointLogRepository;
import com.yes255.yes255booksusersserver.presentation.dto.response.pointlog.PointLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointLogServiceImpl implements PointLogService {

    private final JpaPointLogRepository pointLogRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<PointLogResponse> findAllPointLogsByUserId(Long userId, Pageable pageable) {

        Page<PointLog> pointLogs = pointLogRepository.findByPoint_User_UserIdOrderByPointLogUpdatedAtDesc(userId, pageable);

        return pointLogs.map(pointLog -> PointLogResponse.builder()
                .pointLogUpdatedAt(pointLog.getPointLogUpdatedAt())
                .pointLogUpdatedType(pointLog.getPointLogUpdatedType())
                .pointLogAmount(pointLog.getPointLogAmount())
                .pointCurrent(pointLog.getPoint().getPointCurrent())
                .build());
    }
}
