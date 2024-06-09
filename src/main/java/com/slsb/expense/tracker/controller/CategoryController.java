package com.slsb.expense.tracker.controller;

import com.slsb.expense.tracker.dto.cashbook.CashBookRequestDto;
import com.slsb.expense.tracker.dto.cashbook.CashBookResponseDto;
import com.slsb.expense.tracker.dto.category.CategoryRequestDto;
import com.slsb.expense.tracker.dto.category.CategoryResponseDto;
import com.slsb.expense.tracker.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<CategoryResponseDto> saveCategory(
            HttpServletRequest request,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(categoryService.saveCategory(request, categoryRequestDto, cashbookId));
    }
    @GetMapping("/getAllByCashBook")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategoryByCashBook(
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(categoryService.getAllCategoryByCashBook(cashbookId));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(
            @PathVariable("id") Long categoryId,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId, cashbookId));
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryResponseDto> updateCategory(
            HttpServletRequest request,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(categoryService.updateCategory(request, categoryRequestDto, cashbookId));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteCategoryById(
            @PathVariable("id") Long categoryId,
            @RequestParam Long cashbookId
    ) throws AccessDeniedException {
        return ResponseEntity.ok(categoryService.deleteCategoryById(categoryId, cashbookId));
    }

    @GetMapping("/searchCategory")
    public ResponseEntity<List<CategoryResponseDto>> searchCategoryByName(
            @RequestParam String categoryName,
            @RequestParam Long cashbookId
    ) {
        return ResponseEntity.ok(categoryService.searchCategoryByName(categoryName, cashbookId));
    }
}
