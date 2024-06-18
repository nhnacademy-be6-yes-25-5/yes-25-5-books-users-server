package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserAddressRepository extends JpaRepository <UserAddress, Long> {
}
