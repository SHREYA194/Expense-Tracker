package com.slsb.expense.tracker.dto.expense;

import com.slsb.expense.tracker.dto.cashbook.CashBookResponseDto;
import com.slsb.expense.tracker.dto.category.CategoryRequestDto;
import com.slsb.expense.tracker.dto.category.CategoryResponseDto;
import com.slsb.expense.tracker.dto.paymentMode.PaymentModeRequestDto;
import com.slsb.expense.tracker.dto.paymentMode.PaymentModeResponseDto;
import com.slsb.expense.tracker.util.expenseTrackerEnum.EntryType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponseDto {
    private Long expenseId;
    private String expenseName;
    private EntryType entryType;
    private Date expenseDateTime;
    private BigDecimal amount;
    private String remarks;
    private CategoryResponseDto categoryResponseDto;
    private PaymentModeResponseDto paymentModeResponseDto;
    private CashBookResponseDto cashBookResponseDto;
}
