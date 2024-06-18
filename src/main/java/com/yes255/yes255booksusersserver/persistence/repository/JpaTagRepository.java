package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends JpaRepository<Tag, Long> {
}
