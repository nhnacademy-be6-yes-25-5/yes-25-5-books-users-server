package com.yes255.yes255booksusersserver.application.service;

import com.yes255.yes255booksusersserver.persistence.domain.UserAddress;

import java.util.List;

public interface UserAddressService {
    UserAddress createAddress(UserAddress address);
    UserAddress updateAddress(Long addressId, UserAddress address);
    UserAddress getAddressById(Long addressId);
    List<UserAddress> getAllAddresses();
    void deleteAddress(Long addressId);
}
