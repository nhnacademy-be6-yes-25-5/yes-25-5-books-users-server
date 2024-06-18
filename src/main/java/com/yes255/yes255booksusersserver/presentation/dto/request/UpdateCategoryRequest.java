package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(
        @NotNull
        long categoryId,

        @NotBlank
        String categoryName,

        Category parentCategory
) {

    public Category toEntity() {

        return Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .parentCategory(parentCategory)
                .build();
    }
}
