package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.BookCategory;
import com.yes255.yes255booksusersserver.persistence.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBookCategoryRepository extends JpaRepository<BookCategory, Long> {

    List<BookCategory> findByBook(Book book);
    List<BookCategory> findByCategory(Category category);
}
