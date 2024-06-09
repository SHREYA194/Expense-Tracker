package com.slsb.expense.tracker.dto.paymentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentModeResponseDto {
    private Long paymentModeId;
    private String paymentModeName;
}
