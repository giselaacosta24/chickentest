package com.accenture.chickentest.repository;

import com.accenture.chickentest.domain.dao.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
}
