package com.slsb.expense.tracker.repository;

import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.entity.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findByCashBook(CashBook cashBook);
}
