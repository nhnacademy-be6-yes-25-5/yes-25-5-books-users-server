package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.PointLogService;
import com.yes255.yes255booksusersserver.persistance.domain.PointLog;
import com.yes255.yes255booksusersserver.persistance.repository.JpaPointLogRepository;
import com.yes255.yes255booksusersserver.presentation.dto.response.PointLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointLogServiceImpl implements PointLogService {

    private final JpaPointLogRepository pointLogRepository;

    @Override
    public List<PointLogResponse> findAllPointLogsByUserId(Long userId, Pageable pageable) {

        List<PointLog> pointLogs = pointLogRepository.findByPoint_User_UserId(userId, pageable);

        return pointLogs.stream().map(pointLog -> PointLogResponse.builder()
                .pointLogUpdatedAt(pointLog.getPointLogUpdatedAt())
                .pointLogUsed(pointLog.getPointLogUsed())
                .pointLogUpdatedType(pointLog.getPointLogUpdatedType())
                .pointLogAmount(pointLog.getPointLogAmount())
                .pointCurrent(pointLog.getPoint().getPointCurrent())
                .build()).collect(Collectors.toList());
    }
}
