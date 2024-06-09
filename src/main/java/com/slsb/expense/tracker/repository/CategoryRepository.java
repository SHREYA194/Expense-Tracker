package com.slsb.expense.tracker.repository;

import com.slsb.expense.tracker.entity.CashBook;
import com.slsb.expense.tracker.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findByCashBook(CashBook cashBook);

    List<Category> findByCategoryName(String categoryName);
}
