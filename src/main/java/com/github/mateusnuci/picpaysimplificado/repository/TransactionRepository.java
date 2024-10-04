package com.github.mateusnuci.picpaysimplificado.repository;

import com.github.mateusnuci.picpaysimplificado.domain.transactional.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
