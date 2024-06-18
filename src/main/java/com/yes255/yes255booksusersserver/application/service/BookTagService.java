package com.yes255.yes255booksusersserver.application.service;

import com.yes255.yes255booksusersserver.presentation.dto.response.BookTagResponse;

import java.util.List;

public interface BookTagService {

    List<BookTagResponse> findBookTagByBookId(Long bookId);
    BookTagResponse createBookTag(Long bookId, Long tagId);
    void deleteBookTag(Long bookTagId);
}