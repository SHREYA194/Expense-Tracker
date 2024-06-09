package com.slsb.expense.tracker.dto.paymentMode;

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
public class PaymentModeRequestDto {

    private Long paymentModeId;

    @NotBlank(message = "Payment Mode required.")
    @NotNull(message = "Invalid Payment Mode : Payment Mode is NULL")
    private String paymentModeName;
}
