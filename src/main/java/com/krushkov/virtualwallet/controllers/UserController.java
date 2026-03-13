package com.krushkov.virtualwallet.controllers;

import com.krushkov.virtualwallet.helpers.factories.ApiResponseFactory;
import com.krushkov.virtualwallet.helpers.mappers.UserMapper;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserFilterOptions;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserUpdateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.api.ApiResponse;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserShortResponse;
import com.krushkov.virtualwallet.services.contacts.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{targetUserId}")
    public ResponseEntity<ApiResponse<UserLongResponse>> getById(@PathVariable Long targetUserId) {
        UserLongResponse userLongResponse = userMapper.toLong(userService.getById(targetUserId));
        return ApiResponseFactory.ok(userLongResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserShortResponse>>> search(
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

        Page<UserShortResponse> userShortResponsePage = userService.search(filters, pageable)
                .map(userMapper::toShort);

        return ApiResponseFactory.ok(userShortResponsePage);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserLongResponse>> update(@Valid @RequestBody UserUpdateRequest request) {
        UserLongResponse userLongResponse = userMapper.toLong(userService.update(request));
        return ApiResponseFactory.ok("User updated successfully.", userLongResponse);
    }

    @PatchMapping("/{targetUserId}/block")
    public ResponseEntity<ApiResponse<Void>> block(@PathVariable Long targetUserId) {
        userService.block(targetUserId);
        return ApiResponseFactory.noContent("User blocked successfully.");
    }

    @PatchMapping("/{targetUserId}/unblock")
    public ResponseEntity<ApiResponse<Void>> unblock(@PathVariable Long targetUserId) {
        userService.unblock(targetUserId);
        return ApiResponseFactory.noContent("User unblocked successfully.");
    }
}
