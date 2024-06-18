package com.yes255.yes255booksusersserver.presentation.controller;

import com.yes255.yes255booksusersserver.application.service.LikesService;
import com.yes255.yes255booksusersserver.common.exception.ValidationFailedException;
import com.yes255.yes255booksusersserver.presentation.dto.request.CreateLikesRequest;
import com.yes255.yes255booksusersserver.presentation.dto.request.UpdateLikesRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.LikesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/likes/users/{userId}")
    public ResponseEntity<List<LikesResponse>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(likesService.findLikeByUserId(userId));
    }

    @GetMapping("/likes/books/{bookId}")
    public ResponseEntity<List<LikesResponse>> findByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(likesService.findLikeByBookId(bookId));
    }

    @PutMapping("/likes")
    public ResponseEntity<LikesResponse> update(@RequestBody @Valid UpdateLikesRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.ok(likesService.updateLikeStatus(request));
    }

    @PostMapping("/likes")
    public ResponseEntity<LikesResponse> create(@RequestBody @Valid CreateLikesRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return ResponseEntity.ok(likesService.createLike(request));
    }

}
