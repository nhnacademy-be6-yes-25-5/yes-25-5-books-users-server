package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.CartBookService;
import com.yes255.yes255booksusersserver.common.exception.*;
import com.yes255.yes255booksusersserver.common.exception.payload.ErrorStatus;
import com.yes255.yes255booksusersserver.persistance.domain.Book;
import com.yes255.yes255booksusersserver.persistance.domain.Cart;
import com.yes255.yes255booksusersserver.persistance.domain.CartBook;
import com.yes255.yes255booksusersserver.persistance.domain.User;
import com.yes255.yes255booksusersserver.persistance.repository.JpaBookRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaCartBookRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaCartRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaUserRepository;
import com.yes255.yes255booksusersserver.presentation.dto.request.CreateCartBookRequest;
import com.yes255.yes255booksusersserver.presentation.dto.request.UpdateCartBookRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.CartBookResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.CreateCartBookResponse;
import com.yes255.yes255booksusersserver.presentation.dto.response.UpdateCartBookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartBookServiceImpl implements CartBookService {

    private final JpaCartRepository cartRepository;
    private final JpaCartBookRepository cartBookRepository;
    private final JpaBookRepository bookRepository;
    private final JpaUserRepository userRepository;

    // 장바구니에 도서 추가
    @Override
    public CreateCartBookResponse createCartBookByUserId(Long userId, CreateCartBookRequest request) {


        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new BookNotFoundException(ErrorStatus.toErrorStatus("알맞은 책을 찾을 수 없습니다.", 400, LocalDateTime.now())));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorStatus.toErrorStatus("유저가 존재하지 않습니다.", 400, LocalDateTime.now())));

        Cart cart = cartRepository.findByUser_UserId(userId);

        if (Objects.isNull(cart)) {
            throw new CartNotFoundException(ErrorStatus.toErrorStatus("카트가 존재하지 않습니다.", 400, LocalDateTime.now()));
        }

        // 장바구니에 도서 중복 확인
        CartBook oldCartBook = cartBookRepository.findByCart_CartIdAndBook_BookId(cart.getCartId(), request.bookId());

        if (Objects.nonNull(oldCartBook)) {
            throw new CartBookAlreadyExistedException(ErrorStatus.toErrorStatus("장바구니에 같은 도서가 이미 존재합니다.", 400, LocalDateTime.now()));
        }

        CartBook cartBook = cartBookRepository.save(CartBook.builder()
                        .book(book)
                        .bookQuantity(request.bookQuantity())
                        .cart(cart)
                        .cartBookCreatedAt(LocalDateTime.now())
                        .build());

        return CreateCartBookResponse.builder()
                .cartBookId(cartBook.getCartBookId())
                .bookQuantity(request.bookQuantity())
                .build();
    }

    // 장바구니에 도서 수정 (수량 조절)
    @Override
    public UpdateCartBookResponse updateCartBookByUserId(Long userId, UpdateCartBookRequest request) {

        Cart cart = cartRepository.findByUser_UserId(userId);

        if (Objects.isNull(cart)) {
            throw new CartNotFoundException(ErrorStatus.toErrorStatus("카트가 존재하지 않습니다.", 400, LocalDateTime.now()));
        }

        CartBook cartBook = cartBookRepository.findByCartBookIdAndCart_CartId(request.cartBookId(), cart.getCartId());

        if (Objects.isNull(cartBook)) {
            throw new CartBookNotFoundException(ErrorStatus.toErrorStatus("장바구니 도서가 존재하지 않습니다.", 400, LocalDateTime.now()));
        }

        cartBook.updateCartBookQuantity(request.bookQuantity());

        cartBookRepository.save(cartBook);

        return UpdateCartBookResponse.builder()
                .cartBookId(cartBook.getCartBookId())
                .bookQuantity(request.bookQuantity())
                .build();
    }

    // 장바구니에서 도서 삭제
    @Override
    public void deleteCartBookByUserIdByCartBookId(Long userId, Long cartBookId) {
        cartBookRepository.deleteById(cartBookId);
    }

    // 장바구니 도서 목록 조회
    @Override
    public List<CartBookResponse> findAllCartBookById(Long userId) {

        Cart cart = cartRepository.findByUser_UserId(userId);

        if (Objects.isNull(cart)) {
            throw new CartNotFoundException(ErrorStatus.toErrorStatus("카트가 존재하지 않습니다.", 400, LocalDateTime.now()));
        }

        List<CartBook> cartBooks = cartBookRepository.findByCart_CartIdOrderByCartBookCreatedAtDesc(cart.getCartId());


        return cartBooks.stream()
                .map(cartBook -> new CartBookResponse(cartBook.getCartBookId(), cartBook.getBook().getBookId(),
                        cartBook.getBook().getBookName(), cartBook.getBook().getBookPrice(), cartBook.getBookQuantity()))
                .collect(Collectors.toList());
    }
}