package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.requests.transaction.TransactionFilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Transaction getById(Long targetTransactionId);

    Page<Transaction> search(TransactionFilterOptions filters, Pageable pageable);

    Transaction create(Transaction transaction);
}
