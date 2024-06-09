package com.slsb.expense.tracker.service;

import com.slsb.expense.tracker.dto.cashbook.CashBookRequestDto;
import com.slsb.expense.tracker.dto.cashbook.CashBookResponseDto;
import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.jwtAuth.user.User;
import com.slsb.expense.tracker.jwtAuth.user.UserRepository;
import com.slsb.expense.tracker.repository.CashBookRepository;
import com.slsb.expense.tracker.util.constant.CashBookConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashBookService {

    private final UserRepository userRepository;
    private final CashBookRepository cashBookRepository;

    public CashBookResponseDto saveCashBook(HttpServletRequest request, CashBookRequestDto cashBookRequestDto, Long userId) {
        User user = userRepository.findById(userId).get();

        var cashbook = CashBook.builder()
                .cashbookName(cashBookRequestDto.getCashbookName())
                .user(user)
                .activeFlag(CashBookConstants.active_flag_active)
                .createdDate(new Date())
                .createdByIp(request.getRemoteAddr())
                .build();

        CashBook resultCashBook = cashBookRepository.save(cashbook);
        CashBookResponseDto cashBookResponseDto = new CashBookResponseDto();
        BeanUtils.copyProperties(resultCashBook, cashBookResponseDto);

        return cashBookResponseDto;
    }

    public List<CashBookResponseDto> getAllByUserCashBook(Long userId) {
        List<CashBook> cashBookList = cashBookRepository.findByUser(userRepository.findById(userId).get());
        List<CashBookResponseDto> cashBookResponseDtoList = new ArrayList<>();

        CashBookResponseDto cashBookResponseDto;
        for (CashBook cashBook : cashBookList) {
            cashBookResponseDto = new CashBookResponseDto();
            BeanUtils.copyProperties(cashBook, cashBookResponseDto);
            cashBookResponseDtoList.add(cashBookResponseDto);
        }

        return cashBookResponseDtoList;
    }

    private void validateCashBookUser(Long cashbookId, Long userId) throws AccessDeniedException {
        CashBook cashBook = cashBookRepository.findById(cashbookId).get();
        if (cashBook.getUser().getUserId() != userId) {
            throw new AccessDeniedException("You don't have access of this Cash Book.");
        }
    }

    public CashBookResponseDto getCashBookById(Long cashbookId, Long userId) throws AccessDeniedException {
        CashBookResponseDto cashBookResponseDto = new CashBookResponseDto();

        CashBook cashBook = cashBookRepository.findById(cashbookId).get();

        validateCashBookUser(cashbookId, userId);

        BeanUtils.copyProperties(cashBook, cashBookResponseDto);
        return cashBookResponseDto;
    }

    public CashBookResponseDto updateCashBook(HttpServletRequest request, CashBookRequestDto cashBookRequestDto, Long userId) throws AccessDeniedException {
        User user = userRepository.findById(userId).get();

        CashBook cashBook = cashBookRepository.findById(cashBookRequestDto.getCashookId()).get();

        validateCashBookUser(cashBookRequestDto.getCashookId(), userId);

        cashBook.setCashbookName(cashBookRequestDto.getCashbookName());
        cashBook.setUpdatedDate(new Date());
        cashBook.setUpdatedByIp(request.getRemoteAddr());

        CashBook resultCashBook = cashBookRepository.save(cashBook);
        CashBookResponseDto cashBookResponseDto = new CashBookResponseDto();
        BeanUtils.copyProperties(resultCashBook, cashBookResponseDto);

        return cashBookResponseDto;
    }

    public String deleteCashBookById(Long cashbookId, Long userId) throws AccessDeniedException {
        String cashBookName = cashBookRepository.findById(cashbookId).get().getCashbookName();
        validateCashBookUser(cashbookId, userId);
        cashBookRepository.deleteById(cashbookId);
        return "Cash Book " + cashBookName + " successfully deleted.";
    }
}
