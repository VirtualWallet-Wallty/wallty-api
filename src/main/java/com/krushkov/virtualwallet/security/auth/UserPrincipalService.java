package com.krushkov.virtualwallet.security.auth;

import com.krushkov.virtualwallet.helpers.NormalizationHelper;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUserByUsername(String identifier) {
        final String normalized = identifier.contains("@")
                ? NormalizationHelper.normalizeStringToLower(identifier)
                : identifier;

        User user = userRepository.findByUsernameAndIsDeletedFalse(normalized)
                .orElseGet(() -> userRepository.findByEmailAndIsDeletedFalse(normalized)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + identifier)));

        return new UserPrincipal(user);
    }
}
