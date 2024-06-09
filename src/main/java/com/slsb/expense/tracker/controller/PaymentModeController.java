package com.slsb.expense.tracker.controller;

import com.slsb.expense.tracker.dto.paymentMode.PaymentModeRequestDto;
import com.slsb.expense.tracker.dto.paymentMode.PaymentModeResponseDto;
import com.slsb.expense.tracker.service.PaymentModeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/paymentMode")
@RequiredArgsConstructor
public class PaymentModeController {

    private final PaymentModeService paymentModeService;

    @PostMapping("/save")
    public ResponseEntity<PaymentModeResponseDto> savePaymentMode(
            HttpServletRequest request,
            @Valid @RequestBody PaymentModeRequestDto paymentModeRequestDto,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(paymentModeService.savePaymentMode(request, paymentModeRequestDto, cashbookId));
    }
    @GetMapping("/getAllByCashBook")
    public ResponseEntity<List<PaymentModeResponseDto>> getAllCPaymentModeCashBook(
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(paymentModeService.getAllPaymentModeByCashBook(cashbookId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PaymentModeResponseDto> getPaymentModeById(
            @PathVariable("id") Long paymentModeId,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(paymentModeService.getPaymentModeById(paymentModeId, cashbookId));
    }

    @PutMapping("/update")
    public ResponseEntity<PaymentModeResponseDto> updatePaymentMode(
            HttpServletRequest request,
            @Valid @RequestBody PaymentModeRequestDto paymentModeRequestDto,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(paymentModeService.updatePaymentMode(request, paymentModeRequestDto, cashbookId));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteCPaymentModeById(
            @PathVariable("id") Long paymentModeId,
            @RequestParam Long cashbookId
    ) throws AccessDeniedException {
        return ResponseEntity.ok(paymentModeService.deletePaymentModeById(paymentModeId, cashbookId));
    }

    @GetMapping("/searchPaymentMode")
    public ResponseEntity<List<PaymentModeResponseDto>> searchCPaymentModeByName(
            @RequestParam String paymentModeName,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(paymentModeService.searchPaymentModeByName(paymentModeName, cashbookId));
    }
}
