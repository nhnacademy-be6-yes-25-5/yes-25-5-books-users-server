package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBookRepository extends JpaRepository<Book, Long> {
}
