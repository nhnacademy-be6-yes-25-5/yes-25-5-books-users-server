package com.yes255.yes255booksusersserver.presentation.dto.request.user;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateUserRequest(String userName, String userPhone, LocalDate userBirth, String oldUserPassword,
                                String userPassword, String userConfirmPassword) {
}
