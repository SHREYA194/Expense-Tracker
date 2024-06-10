package com.slsb.expense.tracker.dto.expense;

import com.slsb.expense.tracker.dto.category.CategoryRequestDto;
import com.slsb.expense.tracker.dto.paymentMode.PaymentModeRequestDto;
import com.slsb.expense.tracker.util.expenseTrackerEnum.EntryType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
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
public class ExpenseRequestDto {

    private Long expenseId;

    @NotBlank(message = "Expense Name required.")
    @NotNull(message = "Invalid Expense Name: Expense Name is NULL")
    private String expenseName;

    @NotNull(message = "Invalid Entry Type: Entry Type is NULL")
    @Enumerated(EnumType.STRING)
    private EntryType entryType;

    @NotNull(message = "Invalid Expense Date: Expense Date is NULL")
    private Date expenseDateTime;

    @NotNull(message = "Invalid Amount: Expense Amount is NULL")
    private BigDecimal amount;

    private String remarks;

    @NotNull(message = "Category required.")
    private CategoryRequestDto categoryRequestDto;

    @NotNull(message = "Payment Mode required.")
    private PaymentModeRequestDto paymentModeRequestDto;
}
