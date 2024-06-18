package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

public record UpdateBookRequest(
        @NotNull Long bookId,

        @NotBlank
        @Size(min=10, max = 13)
        String bookIsbn,

        @NotBlank
        String bookName,

        String bookDescription,

        String bookAuthor,

        String bookPublisher,

        @PastOrPresent
        Date bookPublishDate,

        @NotNull
        BigDecimal bookPrice,

        @NotNull
        BigDecimal bookSellingPrice,

        String bookImage,

        @NotNull
        Integer quantity,

        Integer reviewCount,

        Integer hitsCount,

        Integer searchCount
) {
    public Book toEntity() {
        return Book.builder()
                .bookId(bookId)
                .bookIsbn(bookIsbn)
                .bookName(bookName)
                .bookDescription(bookDescription)
                .bookAuthor(bookAuthor)
                .bookPublisher(bookPublisher)
                .bookPublishDate(bookPublishDate)
                .bookPrice(bookPrice)
                .bookSellingPrice(bookSellingPrice)
                .bookImage(bookImage)
                .quantity(quantity)
                .reviewCount(reviewCount)
                .hitsCount(hitsCount)
                .searchCount(searchCount)
                .build();
    }
}
