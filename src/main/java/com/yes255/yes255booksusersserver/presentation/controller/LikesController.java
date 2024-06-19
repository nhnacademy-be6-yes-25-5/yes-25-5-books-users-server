package com.yes255.yes255booksusersserver.presentation.controller;

import com.yes255.yes255booksusersserver.application.service.LikesService;
import com.yes255.yes255booksusersserver.presentation.dto.request.CreateLikesRequest;
import com.yes255.yes255booksusersserver.presentation.dto.request.UpdateLikesRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.LikesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자의 좋아요 관련 작업을 처리하는 RestController
 */
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    /**
     * 특정 사용자가 좋아요한 항목들을 조회합니다.
     *
     * @param userId 조회할 사용자의 ID
     * @return ResponseEntity<List<LikesResponse>> 형식의 좋아요 목록
     */
    @GetMapping("/likes/users/{userId}")
    public ResponseEntity<List<LikesResponse>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(likesService.findLikeByUserId(userId));
    }

    /**
     * 특정 책에 대한 좋아요 정보를 조회합니다.
     *
     * @param bookId 조회할 책의 ID
     * @return ResponseEntity<List<LikesResponse>> 형식의 책에 대한 좋아요 목록
     */
    @GetMapping("/likes/books/{bookId}")
    public ResponseEntity<List<LikesResponse>> findByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(likesService.findLikeByBookId(bookId));
    }

    /**
     * 좋아요 상태를 업데이트합니다.
     *
     * @param request 업데이트할 좋아요 정보를 담은 UpdateLikesRequest 객체
     * @return ResponseEntity<LikesResponse> 형식의 업데이트된 좋아요 정보
     */
    @PutMapping("/likes")
    public ResponseEntity<LikesResponse> update(@RequestBody UpdateLikesRequest request) {
        return ResponseEntity.ok(likesService.updateLikeStatus(request));
    }

    /**
     * 새로운 좋아요를 생성합니다.
     *
     * @param request 생성할 좋아요 정보를 담은 CreateLikesRequest 객체
     * @return ResponseEntity<LikesResponse> 형식의 생성된 좋아요 정보
     */
    @PostMapping("/likes")
    public ResponseEntity<LikesResponse> create(@RequestBody CreateLikesRequest request) {
        return ResponseEntity.ok(likesService.createLike(request));
    }

}
