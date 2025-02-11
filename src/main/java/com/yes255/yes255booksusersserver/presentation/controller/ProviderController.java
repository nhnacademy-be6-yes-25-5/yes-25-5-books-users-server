package com.yes255.yes255booksusersserver.presentation.controller;

import com.yes255.yes255booksusersserver.application.service.ProviderService;
import com.yes255.yes255booksusersserver.presentation.dto.request.provider.CreateProviderRequest;
import com.yes255.yes255booksusersserver.presentation.dto.request.provider.UpdateProviderRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.provider.CreateProviderResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.provider.ProviderResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.provider.UpdateProviderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 제공자 관련 API를 제공하는 ProviderController
 */

@Tag(name = "제공자 API", description = "제공자 관련 API 입니다.")
@RequestMapping("/admin/providers")
@RequiredArgsConstructor
@RestController
public class ProviderController {

    private final ProviderService providerService;

    /**
     * 제공자를 생성합니다.
     *
     * @param providerRequest 제공자 생성 요청 데이터
     * @return 생성된 제공자 응답 데이터와 상태 코드 200(OK)
     */
    @Operation(summary = "제공자 생성", description = "제공자를 생성합니다.")
    @PostMapping
    public ResponseEntity<CreateProviderResponse> createProvider(@RequestBody CreateProviderRequest providerRequest) {
        return ResponseEntity.ok(providerService.createProvider(providerRequest));
    }

    /**
     * 특정 제공자를 수정합니다.
     *
     * @param providerId      수정할 제공자 ID
     * @param providerRequest 제공자 수정 요청 데이터
     * @return 수정된 제공자 응답 데이터와 상태 코드 200(OK)
     */
    @Operation(summary = "제공자 수정", description = "특정 제공자를 수정합니다.")
    @PutMapping("/{providerId}")
    public ResponseEntity<UpdateProviderResponse> updateProvider(@PathVariable Long providerId, @RequestBody UpdateProviderRequest providerRequest) {
        return ResponseEntity.ok(providerService.updateProvider(providerId, providerRequest));
    }

    /**
     * 특정 제공자를 조회합니다.
     *
     * @param providerId 조회할 제공자 ID
     * @return 조회된 제공자 응답 데이터와 상태 코드 200(OK)
     */
    @Operation(summary = "제공자 조회", description = "특정 제공자를 조회합니다.")
    @GetMapping("/{providerId}")
    public ResponseEntity<ProviderResponse> getProviderById(@PathVariable Long providerId) {
        return ResponseEntity.ok(providerService.findProviderById(providerId));
    }

    /**
     * 모든 제공자 목록을 조회합니다.
     *
     * @return 제공자 목록 응답 데이터와 상태 코드 200(OK)
     */
    @Operation(summary = "제공자 목록 조회", description = "모든 제공자 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProviderResponse>> getAllProviders() {
        return ResponseEntity.ok(providerService.findAllProviders());
    }

    /**
     * 특정 제공자를 삭제합니다.
     *
     * @param providerId 삭제할 제공자 ID
     * @return 상태 코드 204(NO CONTENT)
     */
    @Operation(summary = "제공자 삭제", description = "특정 제공자를 삭제합니다.")
    @DeleteMapping("/{providerId}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long providerId) {

        providerService.deleteProvider(providerId);

        return ResponseEntity.noContent().build();
    }
}
