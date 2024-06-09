package com.slsb.expense.tracker.repository;

import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.jwtAuth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashBookRepository extends CrudRepository<CashBook, Long> {
    List<CashBook> findByUser(User user);
}
