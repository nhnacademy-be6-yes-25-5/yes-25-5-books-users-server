package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBookTagRepository extends JpaRepository<BookTag, Long> {
    List<BookTag> findByBook(Book book);
}
