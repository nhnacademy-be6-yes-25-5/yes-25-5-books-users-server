package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(

        @NotNull(message = "카테고리 아이디는 필수 입력 항목입니다.")
        long categoryId,

        @NotBlank(message = "카테고리 이름은 필수 입력 항목입니다.")
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
