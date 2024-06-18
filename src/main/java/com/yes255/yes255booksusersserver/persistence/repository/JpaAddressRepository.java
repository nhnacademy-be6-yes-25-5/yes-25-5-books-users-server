package com.yes255.yes255booksusersserver.persistence.repository;

import com.yes255.yes255booksusersserver.persistence.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
public interface JpaAddressRepository extends JpaRepository<Address, Long>{
}
