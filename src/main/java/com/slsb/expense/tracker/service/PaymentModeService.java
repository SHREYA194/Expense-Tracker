package com.slsb.expense.tracker.service;

import com.slsb.expense.tracker.dto.paymentMode.PaymentModeRequestDto;
import com.slsb.expense.tracker.dto.paymentMode.PaymentModeResponseDto;
import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.entity.PaymentMode;
import com.slsb.expense.tracker.repository.CashBookRepository;
import com.slsb.expense.tracker.repository.PaymentModeRepository;
import com.slsb.expense.tracker.util.constant.CashBookConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentModeService {
    private final PaymentModeRepository paymentModeRepository;
    private final CashBookRepository cashBookRepository;

    public PaymentModeResponseDto savePaymentMode(HttpServletRequest request, PaymentModeRequestDto paymentModeRequestDto, Long cashbookId) {
        CashBook cashBook = cashBookRepository.findById(cashbookId).get();

        var paymentMode = PaymentMode.builder()
                .paymentModeName(paymentModeRequestDto.getPaymentModeName())
                .cashBook(cashBook)
                .activeFlag(CashBookConstants.active_flag_active)
                .createdDate(new Date())
                .createdByIp(request.getRemoteAddr())
                .build();

        PaymentMode resultPaymentMode = paymentModeRepository.save(paymentMode);

        PaymentModeResponseDto paymentModeResponseDto = new PaymentModeResponseDto();
        BeanUtils.copyProperties(resultPaymentMode, paymentModeResponseDto);

        return paymentModeResponseDto;
    }

    public List<PaymentModeResponseDto> getAllPaymentModeByCashBook(Long cashbookId) {
        List<PaymentMode> paymentModeList = paymentModeRepository.findByCashBook(cashBookRepository.findById(cashbookId).get());

        List<PaymentModeResponseDto> paymentModeResponseDtoList = paymentModeList
                .stream()
                .map(paymentMode -> {
                    PaymentModeResponseDto paymentModeResponseDto = new PaymentModeResponseDto();
                    BeanUtils.copyProperties(paymentMode,paymentModeResponseDto);
                    return paymentModeResponseDto;
                })
                .collect(Collectors.toList());

        return paymentModeResponseDtoList;
    }

    public PaymentModeResponseDto getPaymentModeById(Long paymentModeId, Long cashbookId) {
        PaymentModeResponseDto paymentModeResponseDto = new PaymentModeResponseDto();
        PaymentMode paymentMode = paymentModeRepository.findById(paymentModeId).get();
        BeanUtils.copyProperties(paymentMode, paymentModeResponseDto);
        return paymentModeResponseDto;
    }

    public PaymentModeResponseDto updatePaymentMode(HttpServletRequest request, PaymentModeRequestDto paymentModeRequestDto, Long cashbookId) {
        CashBook cashBook = cashBookRepository.findById(cashbookId).get();
        PaymentMode paymentMode = paymentModeRepository.findById(paymentModeRequestDto.getPaymentModeId()).get();

        paymentMode.setPaymentModeName(paymentModeRequestDto.getPaymentModeName());
        paymentMode.setUpdatedDate(new Date());
        paymentMode.setUpdatedByIp(request.getRemoteAddr());

        PaymentMode resultPaymentMode = paymentModeRepository.save(paymentMode);
        PaymentModeResponseDto paymentModeResponseDto = new PaymentModeResponseDto();
        BeanUtils.copyProperties(resultPaymentMode, paymentModeResponseDto);

        return paymentModeResponseDto;
    }

    public String deletePaymentModeById(Long paymentModeId, Long cashbookId) {
        String paymentModeName = paymentModeRepository.findById(paymentModeId).get().getPaymentModeName();
        paymentModeRepository.deleteById(paymentModeId);
        return "Payment Mode " + paymentModeId + " successfully deleted.";
    }


    public List<PaymentModeResponseDto> searchPaymentModeByName(String paymentModeName, Long cashbookId) {
        List<PaymentMode> paymentModeList = paymentModeRepository.findByPaymentModeName(paymentModeName);

        List<PaymentModeResponseDto> paymentModeResponseDtoList = paymentModeList
                .stream()
                .filter(paymentMode -> paymentMode.getCashBook().getCashbookId() == cashbookId)
                .map(paymentMode -> {
                    PaymentModeResponseDto paymentModeResponseDto = new PaymentModeResponseDto();
                    BeanUtils.copyProperties(paymentMode,paymentModeResponseDto);
                    return paymentModeResponseDto;
                })
                .collect(Collectors.toList());

        return paymentModeResponseDtoList;

    }
}
