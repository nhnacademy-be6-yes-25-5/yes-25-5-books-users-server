package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.BookTag;
import com.yes255.yes255booksusersserver.persistence.domain.Tag;
import jakarta.validation.constraints.NotNull;

public record CreateBookTagRequest(
        @NotNull
        Book book,

        @NotNull
        Tag tag
) {
    public BookTag toEntity() {
        return BookTag.builder()
                .book(book)
                .tag(tag)
                .build();
    }
}
