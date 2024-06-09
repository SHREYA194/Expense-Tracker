package com.slsb.expense.tracker.controller;

import com.slsb.expense.tracker.dto.cashbook.CashBookRequestDto;
import com.slsb.expense.tracker.dto.cashbook.CashBookResponseDto;
import com.slsb.expense.tracker.service.CashBookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/cashbook")
@RequiredArgsConstructor
public class CashBookController {
    private final CashBookService cashBookService;

    @PostMapping("/save")
    public ResponseEntity<CashBookResponseDto> saveCashBook(
            HttpServletRequest request,
            @Valid @RequestBody CashBookRequestDto cashBookRequestDto,
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(cashBookService.saveCashBook(request, cashBookRequestDto, userId));
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<List<CashBookResponseDto>> getAllCashBook(
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(cashBookService.getAllByUserCashBook(userId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CashBookResponseDto> getCashBookById(
            @PathVariable("id") Long cashbookId,
            @RequestParam Long userId
    ) throws AccessDeniedException {
        return ResponseEntity.ok(cashBookService.getCashBookById(cashbookId, userId));
    }

    @PutMapping("/update")
    public ResponseEntity<CashBookResponseDto> updateCashBook(
            HttpServletRequest request,
            @Valid @RequestBody CashBookRequestDto cashBookRequestDto,
            @RequestParam Long userId
    ) throws AccessDeniedException {
        return ResponseEntity.ok(cashBookService.updateCashBook(request, cashBookRequestDto, userId));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteCashBookById(
            @PathVariable("id") Long cashbookId,
            @RequestParam Long userId
    ) throws AccessDeniedException {
        return ResponseEntity.ok(cashBookService.deleteCashBookById(cashbookId, userId));
    }
}
