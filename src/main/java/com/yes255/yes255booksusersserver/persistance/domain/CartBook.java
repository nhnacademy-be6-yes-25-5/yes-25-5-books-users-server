package com.yes255.yes255booksusersserver.persistance.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartBookId;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "cart_id")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "book_id")
    private Book book;

    @NotNull(message = "도서 수량은 필수입니다.")
    @Column(nullable = false)
    private int bookQuantity;

    @NotNull(message = "장바구니 도서 추가일은 필수입니다.")
    @Column(nullable = false)
    private LocalDateTime cartBookCreatedAt;

    @Builder
    public CartBook(Long cartBookId, Cart cart, Book book, int bookQuantity, LocalDateTime cartBookCreatedAt) {
        this.cartBookId = cartBookId;
        this.cart = cart;
        this.book = book;
        this.bookQuantity = bookQuantity;
        this.cartBookCreatedAt = cartBookCreatedAt;
    }

    // 장바구니 도서 수량 수정
    public void updateCartBookQuantity(int bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public void addQuantity(int quantity) {
        this.bookQuantity = quantity;
    }
}
