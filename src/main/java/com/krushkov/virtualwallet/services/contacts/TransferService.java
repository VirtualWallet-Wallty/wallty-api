package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.requests.TransferRequest;

public interface TransferService {
    Transaction transfer(TransferRequest request);
}
