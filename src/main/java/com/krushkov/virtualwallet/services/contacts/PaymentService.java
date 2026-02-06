package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.requests.PaymentRequest;

public interface PaymentService {
    Transaction pay(PaymentRequest request);
}
