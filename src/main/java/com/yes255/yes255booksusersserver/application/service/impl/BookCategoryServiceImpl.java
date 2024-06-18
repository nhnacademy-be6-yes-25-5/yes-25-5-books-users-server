package com.yes255.yes255booksusersserver.application.service.impl;

import com.yes255.yes255booksusersserver.application.service.BookCategoryService;
import com.yes255.yes255booksusersserver.persistance.domain.Book;
import com.yes255.yes255booksusersserver.persistance.domain.BookCategory;
import com.yes255.yes255booksusersserver.persistance.domain.Category;
import com.yes255.yes255booksusersserver.common.exception.BookCategoryNotFoundException;
import com.yes255.yes255booksusersserver.persistance.repository.JpaBookCategoryRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaBookRepository;
import com.yes255.yes255booksusersserver.persistance.repository.JpaCategoryRepository;
import com.yes255.yes255booksusersserver.presentation.dto.request.UpdateBookCategoryRequest;
import com.yes255.yes255booksusersserver.presentation.dto.response.BookCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

    private final JpaBookCategoryRepository jpaBookCategoryRepository;
    private final JpaBookRepository jpaBookRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    public BookCategoryResponse toResponse(BookCategory bookCategory) {
        return BookCategoryResponse.builder()
                .bookCategoryId(bookCategory.getBookCategoryId())
                .bookId(bookCategory.getBook().getBookId())
                .categoryId(bookCategory.getCategory().getCategoryId())
                .build();
    }

    @Transactional
    @Override
    public BookCategoryResponse createBookCategory(Long bookId, Long categoryId) {

        Book book = jpaBookRepository.findById(bookId).orElse(null);
        Category category = jpaCategoryRepository.findById(categoryId).orElse(null);

        if(book == null || category == null) {
            throw new IllegalArgumentException();
        }

        BookCategory bookCategory = BookCategory.builder()
                        .bookCategoryId(null)
                        .book(book)
                        .category(category)
                        .build();

        return toResponse(jpaBookCategoryRepository.save(bookCategory));
    }

    @Override
    @Transactional(readOnly = true)
    public BookCategoryResponse findBookCategory(Long bookCategoryId) {

        BookCategory bookCategory = jpaBookCategoryRepository.findById(bookCategoryId).orElse(null);

        if(bookCategory == null) {
            throw new BookCategoryNotFoundException();
        }

        return toResponse(bookCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCategoryResponse> findBookCategoryByBookId(Long bookId) {

        return jpaBookCategoryRepository.findByBook(jpaBookRepository.findById(bookId).orElse(null)).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCategoryResponse> findBookCategoryByCategoryId(Long categoryId) {

        return jpaBookCategoryRepository.findByCategory(jpaCategoryRepository.findById(categoryId).orElse(null)).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookCategoryResponse> findAllBookCategories() {

        return jpaBookCategoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    @Override
    public BookCategoryResponse updateBookCategory(UpdateBookCategoryRequest request) {

        if(Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }

        if(!jpaBookCategoryRepository.existsById(request.bookCategoryId())) {
            throw new IllegalArgumentException();
        }

        return toResponse(jpaBookCategoryRepository.save(request.toEntity()));
    }

    @Transactional
    @Override
    public void deleteBookCategory(Long bookCategoryId) {

        if(!jpaBookCategoryRepository.existsById(bookCategoryId)) {
            throw new IllegalArgumentException();
        }

        jpaBookCategoryRepository.deleteById(bookCategoryId);
    }
}
