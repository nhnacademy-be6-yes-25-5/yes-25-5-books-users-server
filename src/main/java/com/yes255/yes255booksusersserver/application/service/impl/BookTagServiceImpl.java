package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.BookTagService;
import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.BookTag;
import com.yes255.yes255booksusersserver.persistence.domain.Tag;
import com.yes255.yes255booksusersserver.persistence.repository.JpaBookRepository;
import com.yes255.yes255booksusersserver.persistence.repository.JpaBookTagRepository;
import com.yes255.yes255booksusersserver.persistence.repository.JpaTagRepository;
import com.yes255.yes255booksusersserver.presentation.dto.response.BookTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookTagServiceImpl implements BookTagService {

    private final JpaBookTagRepository jpaBookTagRepository;
    private final JpaBookRepository jpaBookRepository;
    private final JpaTagRepository jpaTagRepository;

    public BookTagResponse toResponse(BookTag bookTag) {
        return BookTagResponse.builder()
                .bookTagId(bookTag.getBookTagId())
                .bookId(bookTag.getBook().getBookId())
                .tagId(bookTag.getTag().getTagId())
                .build();
    }

    @Transactional
    @Override
    public List<BookTagResponse> findBookTagByBookId(Long bookId) {
        return jpaBookTagRepository.findByBook(jpaBookRepository.findById(bookId).orElse(null)).stream().map(this::toResponse).toList();
    }

    @Transactional
    @Override
    public BookTagResponse createBookTag(Long bookId, Long tagId) {

        Book book = jpaBookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Tag tag = jpaTagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Tag not found"));

        BookTag bookTag = BookTag.builder()
                .bookTagId(null)
                .book(book)
                .tag(tag)
                .build();

        return toResponse(jpaBookTagRepository.save(bookTag));

    }

    @Transactional
    @Override
    public void deleteBookTag(Long bookTagId) {

        if(jpaBookTagRepository.existsById(bookTagId)) {
            throw new IllegalArgumentException("알맞지 않은 bookTagId 입니다.");
        }

        jpaBookTagRepository.deleteById(bookTagId);
    }
}
