package com.yes255.yes255booksusersserver.presentation.dto.response;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ReadPurePriceResponse(BigDecimal purePrice, LocalDate recordedAt) {
}
