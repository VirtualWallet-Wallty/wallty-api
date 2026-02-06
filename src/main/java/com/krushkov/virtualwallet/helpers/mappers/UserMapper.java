package com.krushkov.virtualwallet.helpers.mappers;

import com.krushkov.virtualwallet.models.User;
import com.krushkov.virtualwallet.models.dtos.requests.auth.RegisterRequest;
import com.krushkov.virtualwallet.models.dtos.requests.user.UserUpdateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.user.UserShortResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {WalletMapper.class, CardMapper.class}
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "wallets", ignore = true)
    @Mapping(target = "cards", ignore = true)
    @Mapping(target = "blocked", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User fromRegister(RegisterRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    void update(@MappingTarget User user, UserUpdateRequest request);

    UserShortResponse toShort(User user);

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "isBlocked", source = "blocked")
    UserLongResponse toLong(User user);
}
