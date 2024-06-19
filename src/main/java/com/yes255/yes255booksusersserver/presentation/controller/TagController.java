package com.yes255.yes255booksusersserver.presentation.controller;

import com.yes255.yes255booksusersserver.application.service.TagService;
import com.yes255.yes255booksusersserver.common.exception.ValidationFailedException;
import com.yes255.yes255booksusersserver.presentation.dto.request.CreateTagRequest;
import com.yes255.yes255booksusersserver.presentation.dto.request.UpdateTagRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.TagResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 책의 태그 관련 작업을 처리하는 RestController
 */
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * 모든 태그 목록을 조회합니다.
     *
     * @return ResponseEntity<List<TagResponse>> 형식의 모든 태그 목록
     */
    @GetMapping("/tags")
    public ResponseEntity<List<TagResponse>> findAll() {
        return ResponseEntity.ok(tagService.findAllTags());
    }

    /**
     * 특정 태그를 조회합니다.
     *
     * @param tagId 조회할 태그의 ID
     * @return ResponseEntity<TagResponse> 형식의 특정 태그 정보
     */
    @GetMapping("/tags/{tagId}")
    public ResponseEntity<TagResponse> find(@PathVariable Long tagId) {
        return ResponseEntity.ok(tagService.findTag(tagId));
    }

    /**
     * 새로운 태그를 생성합니다.
     *
     * @param createTagRequest 생성할 태그 정보를 담은 CreateTagRequest 객체
     * @return ResponseEntity<TagResponse> 형식의 생성된 태그 정보
     */
    @PostMapping("/tags")
    public ResponseEntity<TagResponse> create(@RequestBody @Valid CreateTagRequest createTagRequest) {
        return ResponseEntity.ok(tagService.createTag(createTagRequest));
    }

    /**
     * 기존의 태그 정보를 업데이트합니다.
     *
     * @author 김준서
     * @param updateTagRequest 업데이트할 태그 정보를 담은 UpdateTagRequest 객체
     * @param bindingResult    데이터 유효성 검사 결과를 담은 BindingResult 객체
     * @return ResponseEntity<TagResponse> 형식의 업데이트된 태그 정보
     */
    @PutMapping("/tags")
    public ResponseEntity<TagResponse> update(@RequestBody @Valid UpdateTagRequest updateTagRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.ok(tagService.updateTag(updateTagRequest));
    }

    /**
     * 특정 태그를 삭제합니다.
     *
     * @param tagId 삭제할 태그의 ID
     * @return ResponseEntity<Void> 형식의 응답 (콘텐츠 없음)
     */
    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> delete(@PathVariable Long tagId) {

        tagService.deleteTag(tagId);

        return ResponseEntity.noContent().build();
    }
}