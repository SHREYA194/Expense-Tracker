package com.slsb.expense.tracker.service;

import com.slsb.expense.tracker.dto.cashbook.CashBookResponseDto;
import com.slsb.expense.tracker.dto.category.CategoryRequestDto;
import com.slsb.expense.tracker.dto.category.CategoryResponseDto;
import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.entity.Category;
import com.slsb.expense.tracker.jwtAuth.user.User;
import com.slsb.expense.tracker.repository.CashBookRepository;
import com.slsb.expense.tracker.repository.CategoryRepository;
import com.slsb.expense.tracker.util.constant.CashBookConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CashBookRepository cashBookRepository;

    public CategoryResponseDto saveCategory(HttpServletRequest request, CategoryRequestDto cashBookRequestDto, Long cashbookId) {
        CashBook cashBook = cashBookRepository.findById(cashbookId).get();

        var category = Category.builder()
                .categoryName(cashBookRequestDto.getCategoryName())
                .cashBook(cashBook)
                .activeFlag(CashBookConstants.active_flag_active)
                .createdDate(new Date())
                .createdByIp(request.getRemoteAddr())
                .build();

        Category resultCategory = categoryRepository.save(category);

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        BeanUtils.copyProperties(resultCategory, categoryResponseDto);

        return categoryResponseDto;
    }

    public List<CategoryResponseDto> getAllCategoryByCashBook(Long cashbookId) {
        List<Category> categoryList = categoryRepository.findByCashBook(cashBookRepository.findById(cashbookId).get());

        List<CategoryResponseDto> categoryResponseDtoList = categoryList
                .stream()
                .map(category -> {
                    CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
                    BeanUtils.copyProperties(category,categoryResponseDto);
                    return categoryResponseDto;
                })
                .collect(Collectors.toList());

        return categoryResponseDtoList;
    }

    public CategoryResponseDto getCategoryById(Long categoryId, Long cashbookId) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        Category category = categoryRepository.findById(categoryId).get();
        BeanUtils.copyProperties(category, categoryResponseDto);
        return categoryResponseDto;
    }

    public CategoryResponseDto updateCategory(HttpServletRequest request, CategoryRequestDto categoryRequestDto, Long cashbookId) {
        CashBook cashBook = cashBookRepository.findById(cashbookId).get();
        Category category = categoryRepository.findById(categoryRequestDto.getCategoryId()).get();

        category.setCategoryName(categoryRequestDto.getCategoryName());
        category.setUpdatedDate(new Date());
        category.setUpdatedByIp(request.getRemoteAddr());

        Category resultCategory = categoryRepository.save(category);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        BeanUtils.copyProperties(resultCategory, categoryResponseDto);

        return categoryResponseDto;
    }

    public String deleteCategoryById(Long categoryId, Long cashbookId) {
        String categoryName = categoryRepository.findById(categoryId).get().getCategoryName();
        categoryRepository.deleteById(categoryId);
        return "Category " + categoryName + " successfully deleted.";
    }


    public List<CategoryResponseDto> searchCategoryByName(String categoryName, Long cashbookId) {
        List<Category> categoryList = categoryRepository.findByCategoryName(categoryName);

        List<CategoryResponseDto> categoryResponseDtoList = categoryList
                .stream()
                .filter(category -> category.getCashBook().getCashbookId() == cashbookId)
                .map(category -> {
                    CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
                    BeanUtils.copyProperties(category,categoryResponseDto);
                    return categoryResponseDto;
                })
                .collect(Collectors.toList());

        return categoryResponseDtoList;

    }
}
