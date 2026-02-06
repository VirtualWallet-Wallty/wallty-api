package com.krushkov.virtualwallet.security.auth;

import com.krushkov.virtualwallet.models.Role;
import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.enums.RoleType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;
    private final RoleType role;
    private final boolean isBlocked;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole().getName();
        this.isBlocked = user.isBlocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }
}
