package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Category;
import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(

        @NotBlank(message = "카테고리 이름은 필수 입력 항목입니다.")
        String categoryName,

        Category parentCategory
) {

    public Category toEntity() {

        return Category.builder()
                .categoryId(null)
                .categoryName(categoryName)
                .parentCategory(parentCategory)
                .build();
    }

}
