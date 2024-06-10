package com.slsb.expense.tracker.controller;

import com.slsb.expense.tracker.dto.category.CategoryRequestDto;
import com.slsb.expense.tracker.dto.category.CategoryResponseDto;
import com.slsb.expense.tracker.dto.expense.ExpenseRequestDto;
import com.slsb.expense.tracker.dto.expense.ExpenseResponseDto;
import com.slsb.expense.tracker.service.ExpenseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping("/save")
    public ResponseEntity<ExpenseResponseDto> saveExpense(
            HttpServletRequest request,
            @Valid @RequestBody ExpenseRequestDto expenseRequestDto,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(expenseService.saveExpense(request, expenseRequestDto, cashbookId));
    }

    @GetMapping("/getAllByCashBook")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenseByCashBook(
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(expenseService.getAllExpenseByCashBook(cashbookId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(
            @PathVariable("id") Long expenseId,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(expenseService.getExpenseById(expenseId, cashbookId));
    }

}
