package com.slsb.expense.tracker.repository;

import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.entity.PaymentMode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentModeRepository extends CrudRepository<PaymentMode, Long> {
    List<PaymentMode> findByCashBook(CashBook cashBook);

    List<PaymentMode> findByPaymentModeName(String paymentModeName);
}
