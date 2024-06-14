package com.slsb.expense.tracker.service;

import com.slsb.expense.tracker.dto.cashbook.CashBookResponseDto;
import com.slsb.expense.tracker.dto.category.CategoryResponseDto;
import com.slsb.expense.tracker.dto.expense.ExpenseRequestDto;
import com.slsb.expense.tracker.dto.expense.ExpenseResponseDto;
import com.slsb.expense.tracker.dto.paymentMode.PaymentModeResponseDto;
import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.entity.Category;
import com.slsb.expense.tracker.entity.Expense;
import com.slsb.expense.tracker.entity.PaymentMode;
import com.slsb.expense.tracker.repository.CashBookRepository;
import com.slsb.expense.tracker.repository.CategoryRepository;
import com.slsb.expense.tracker.repository.ExpenseRepository;
import com.slsb.expense.tracker.repository.PaymentModeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CashBookRepository cashBookRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentModeRepository paymentModeRepository;
    private final CategoryService categoryService;

    public ExpenseResponseDto saveExpense(HttpServletRequest request, ExpenseRequestDto expenseRequestDto, Long cashbookId) {

        CashBook cashBook = cashBookRepository.findById(cashbookId).get();

        Category category;
        List<Category> categoryList = categoryRepository.findByCategoryName(expenseRequestDto.getCategoryRequestDto().getCategoryName());
        if (categoryList != null && categoryList.size() > 0) {
            category = categoryList.get(0);
        } else {
            category = new Category();
            BeanUtils.copyProperties(expenseRequestDto.getCategoryRequestDto(), category);
            category.setCreatedDate(new Date());
            category.setCreatedByIp(request.getRemoteAddr());
            category.setCashBook(cashBook);
        }

        PaymentMode paymentMode;
        List<PaymentMode> paymentModeList = paymentModeRepository.findByPaymentModeName(expenseRequestDto.getPaymentModeRequestDto().getPaymentModeName());
        if (paymentModeList != null && paymentModeList.size() > 0) {
            paymentMode = paymentModeList.get(0);
        } else {
            paymentMode = new PaymentMode();
            BeanUtils.copyProperties(expenseRequestDto.getPaymentModeRequestDto(), paymentMode);
            paymentMode.setCreatedDate(new Date());
            paymentMode.setCreatedByIp(request.getRemoteAddr());
            paymentMode.setCashBook(cashBook);
        }



        Expense expense = Expense
                .builder()
                .expenseName(expenseRequestDto.getExpenseName())
                .entryType(expenseRequestDto.getEntryType())
                .expenseDateTime(expenseRequestDto.getExpenseDateTime())
                .amount(expenseRequestDto.getAmount())
                .remarks(expenseRequestDto.getRemarks())
                .createdDate(new Date())
                .createdByIp(request.getRemoteAddr())
                .category(category)
                .paymentMode(paymentMode)
                .cashBook(cashBook)
                .build();

        Expense resultExpense = expenseRepository.save(expense);

        ExpenseResponseDto responseExpenseDto = new ExpenseResponseDto();

        convertAllChildEntityToDto(resultExpense, responseExpenseDto);

        return responseExpenseDto;
    }

    public List<ExpenseResponseDto> getAllExpenseByCashBook(Long cashbookId) {
        List<Expense> expenseList = expenseRepository.findByCashBook(cashBookRepository.findById(cashbookId).get());

        List<ExpenseResponseDto> expenseResponseDtoList = expenseList
                .stream()
                .map(expense -> {
                    ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
                    convertAllChildEntityToDto(expense, expenseResponseDto);
                    return expenseResponseDto;
                })
                .collect(Collectors.toList());

        return expenseResponseDtoList;
    }

    public ExpenseResponseDto getExpenseById(Long expenseId, Long cashbookId) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        Expense expense = expenseRepository.findById(expenseId).get();
        convertAllChildEntityToDto(expense, expenseResponseDto);
        return expenseResponseDto;
    }

    private ExpenseResponseDto convertAllChildEntityToDto(Expense expense, ExpenseResponseDto expenseResponseDto) {
        BeanUtils.copyProperties(expense, expenseResponseDto);

        CategoryResponseDto resultCategoryResponseDto = new CategoryResponseDto();
        BeanUtils.copyProperties(expense.getCategory(), resultCategoryResponseDto);
        expenseResponseDto.setCategoryResponseDto(resultCategoryResponseDto);

        PaymentModeResponseDto resultPaymentModeResponseDto = new PaymentModeResponseDto();
        BeanUtils.copyProperties(expense.getPaymentMode(), resultPaymentModeResponseDto);
        expenseResponseDto.setPaymentModeResponseDto(resultPaymentModeResponseDto);

        CashBookResponseDto resultCashBookResponseDto = new CashBookResponseDto();
        BeanUtils.copyProperties(expense.getCashBook(), resultCashBookResponseDto);
        expenseResponseDto.setCashBookResponseDto(resultCashBookResponseDto);

        return expenseResponseDto;
    }

    public ExpenseResponseDto updateExpense(HttpServletRequest request, ExpenseRequestDto expenseRequestDto, Long cashbookId) {

        Category category = categoryRepository.findById(expenseRequestDto.getCategoryRequestDto().getCategoryId()).get();
        PaymentMode paymentMode = paymentModeRepository.findById(expenseRequestDto.getPaymentModeRequestDto().getPaymentModeId()).get();
        CashBook cashBook = cashBookRepository.findById(cashbookId).get();
        Expense expense = expenseRepository.findById(expenseRequestDto.getExpenseId()).get();

        expense.setExpenseName(expenseRequestDto.getExpenseName());
        expense.setExpenseDateTime(expenseRequestDto.getExpenseDateTime());
        expense.setEntryType(expenseRequestDto.getEntryType());
        expense.setExpenseDateTime(expenseRequestDto.getExpenseDateTime());
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setRemarks(expenseRequestDto.getRemarks());
        expense.setCategory(category);
        expense.setPaymentMode(paymentMode);
        expense.setCashBook(cashBook);
        expense.setUpdatedDate(new Date());
        expense.setUpdatedByIp(request.getRemoteAddr());

        Expense resultExpense = expenseRepository.save(expense);
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
//        BeanUtils.copyProperties(resultExpense, expenseResponseDto);

        convertAllChildEntityToDto(resultExpense, expenseResponseDto);

        return expenseResponseDto;
    }

    public String deleteExpenseById(Long expenseId, Long cashbookId) {
        String expenseName = expenseRepository.findById(expenseId).get().getExpenseName();
        expenseRepository.deleteById(expenseId);
        return "Category " + expenseName + " successfully deleted.";
    }
}
