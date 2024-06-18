package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserStateRepository extends JpaRepository<UserState, Long> {
}
