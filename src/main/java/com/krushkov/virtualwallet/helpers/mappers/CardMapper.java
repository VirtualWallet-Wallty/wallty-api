package com.krushkov.virtualwallet.helpers.mappers;

import com.krushkov.virtualwallet.models.Card;
import com.krushkov.virtualwallet.models.dtos.requests.card.CardCreateRequest;
import com.krushkov.virtualwallet.models.dtos.responses.card.CardLongResponse;
import com.krushkov.virtualwallet.models.dtos.responses.card.CardShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(
            target = "cardSuffix", expression =
            "java(request.cardNumber().substring(request.cardNumber().length() - 4))"
    )
    Card fromCreate(CardCreateRequest request);

    @Mapping(target = "ownerId", source = "user.id")
    CardShortResponse toShort(Card card);

     CardLongResponse toLong(Card card);
}
