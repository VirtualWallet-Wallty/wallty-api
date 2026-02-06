package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserFilterOptions;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getById(Long targetUserId);

    User getByUsername(String targetUsername);

    User getByEmail(String targetEmail);

    Page<User> search(UserFilterOptions filters, Pageable pageable);

    User create(User user);

    User update(UserUpdateRequest request);

    void block(Long targetUserId);

    void unblock(Long targetUserId);

    long count();
}
