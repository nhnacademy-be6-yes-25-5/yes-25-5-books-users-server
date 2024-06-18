package com.yes255.yes255booksusersserver.presentation.dto.response;

import com.yes255.yes255booksusersserver.persistence.domain.Book;
import com.yes255.yes255booksusersserver.persistence.domain.User;
import lombok.Builder;

@Builder
public record LikesResponse (Long likesId, Book book, User user, boolean likesStatus) {
}
