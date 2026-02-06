package com.krushkov.virtualwallet.services.contacts;

import com.krushkov.virtualwallet.models.Card;

import java.util.List;

public interface CardService {

    Card getById(Long targetCardId);

    List<Card> getAllByUserId(Long targetCardId);

    List<Card> getAllMyCards();

    Card add(Card card);

    void activate(Long targetCardId);

    void deactivate(Long targetCardId);

    void remove(Long targetCardId);

}
