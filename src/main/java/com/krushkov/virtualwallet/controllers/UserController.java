package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.mappers.UserMapper;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserFilterOptions;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserUpdateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserShortResponse;
import com.krushkov.virtualwallet.services.contacts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{targetUserId}")
    public UserLongResponse getById(@PathVariable Long targetUserId) {
        return userMapper.toLong(userService.getById(targetUserId));
    }

    @GetMapping
    public Page<UserShortResponse> search(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) boolean isBlocked,
            @RequestParam(required = false) LocalDateTime createdFrom,
            @RequestParam(required = false) LocalDateTime createdTo,
            Pageable pageable
    ) {
        UserFilterOptions filters = new UserFilterOptions(
                username, firstName, lastName,
                email, phoneNumber, isBlocked,
                createdFrom, createdTo
        );

        return userService.search(filters, pageable)
                .map(userMapper::toShort);
    }

    @PutMapping
    public UserLongResponse update(@Valid @RequestBody UserUpdateRequest request) {
        return userMapper.toLong(userService.update(request));
    }

    @PatchMapping("/{targetUserId}/block")
    public void block(@PathVariable Long targetUserId) {
        userService.block(targetUserId);
    }

    @PatchMapping("/{targetUserId}/unblock")
    public void unblock(@PathVariable Long targetUserId) {
        userService.unblock(targetUserId);
    }
}
