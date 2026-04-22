package com.krushkov.virtualwallet.services.contracts;

import com.krushkov.virtualwallet.models.Transaction;
import com.krushkov.virtualwallet.models.dtos.requests.TopUpRequest;

public interface TopUpService {
    Transaction topUp(TopUpRequest request);
}
