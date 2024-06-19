package com.yes255.yes255booksusersserver.application.service;

import com.yes255.yes255booksusersserver.presentation.dto.request.PointPolicyRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.PointPolicyResponse;

import java.util.List;

public interface PointPolicyService {

    // todo : 포인트 정책 관련 서비스 작성

    // 포인트 정책 작성 (적립 금액)
    PointPolicyResponse createPointPolicy(PointPolicyRequest policyRequest);

    // 포인트 정책 단건 조회
    PointPolicyResponse findPointPolicyById(Long pointPolicyId);

    // 포인트 정책 목록 조회
    List<PointPolicyResponse> findAllPointPolicies();

    // 포인트 정책 수정 (적립 금액)
    PointPolicyResponse updatePointPolicyById(Long pointPolicyId, PointPolicyRequest policyRequest);

    // 포인트 정책 삭제
    void deletePointPolicyById(Long pointPolicyId);
}
