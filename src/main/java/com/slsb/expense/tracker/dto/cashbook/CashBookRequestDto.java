package com.slsb.expense.tracker.dto.cashbook;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashBookRequestDto {

    private Long cashookId;

    @NotBlank(message = "Cash Book required.")
    @NotNull(message = "Invalid Cash Book Name: Cash Book Name is NULL")
    private String cashbookName;
}
