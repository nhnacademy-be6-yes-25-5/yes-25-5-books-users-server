package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Tag;
import jakarta.validation.constraints.NotBlank;

public record CreateTagRequest(
        @NotBlank
        String tagName
) {
    public Tag toEntity() {
        return Tag.builder()
                .tagId(null)
                .tagName(tagName)
                .build();
    }
}
