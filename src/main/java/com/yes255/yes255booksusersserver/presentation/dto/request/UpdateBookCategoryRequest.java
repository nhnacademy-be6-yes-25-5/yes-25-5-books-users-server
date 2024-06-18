package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.BookCategory;
import com.yes255.yes255booksusersserver.persistence.domain.Category;
import jakarta.validation.constraints.NotNull;

public record UpdateBookCategoryRequest(

        @NotNull(message = "북 카테고리 아이디는 필수 입력 항목입니다.")
        Long bookCategoryId,

        @NotNull(message = "업데이트 할 책은 필수 입력 항목입니다.")
        Book book,

        @NotNull(message = "업데이트 할 카테고리는 필수 입력 항목입니다.")
        Category category) {

    public BookCategory toEntity() {
        return BookCategory.builder()
                .bookCategoryId(bookCategoryId)
                .book(book)
                .category(category)
                .build();
    }
}
