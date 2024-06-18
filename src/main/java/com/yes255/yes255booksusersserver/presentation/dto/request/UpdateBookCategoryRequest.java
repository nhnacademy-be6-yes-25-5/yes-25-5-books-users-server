package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.BookCategory;
import com.yes255.yes255booksusersserver.persistence.domain.Category;
import jakarta.validation.constraints.NotNull;

public record UpdateBookCategoryRequest(
        @NotNull
        Long bookCategoryId,

        @NotNull
        Book book,

        @NotNull
        Category category) {

    public BookCategory toEntity() {
        return BookCategory.builder()
                .bookCategoryId(bookCategoryId)
                .book(book)
                .category(category)
                .build();
    }
}
