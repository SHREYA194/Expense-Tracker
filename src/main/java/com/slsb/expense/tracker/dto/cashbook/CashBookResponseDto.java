package com.slsb.expense.tracker.dto.cashbook;

import com.slsb.expense.tracker.dto.user.UserDetailsDto;
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
public class CashBookResponseDto {
    private Long cashbookId;
    private String cashbookName;
}
