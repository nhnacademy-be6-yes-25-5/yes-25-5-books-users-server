package com.yes255.yes255booksusersserver.presentation.dto.response;

import com.yes255.yes255booksusersserver.persistence.domain.Category;
import lombok.Builder;

@Builder
public record CategoryResponse(long categoryId, String categoryName, Category parentCategory) {
}
