package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.Likes;
import com.yes255.yes255booksusersserver.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaLikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByBook(Book book);
    List<Likes> findByUser(User user);
    Optional<Likes> findByUserAndBook(User user, Book book);
}
