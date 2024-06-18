package com.yes255.yes255booksusersserver.presentation.dto.request;

import com.yes255.yes255booksusersserver.persistence.domain.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTagRequest(

        @NotNull
        Long tagId,

        @NotBlank
        String tagName
) {
    public Tag toEntity() {
        return Tag.builder()
                .tagId(tagId)
                .tagName(tagName)
                .build();
    }
}
