package com.example.bankcards.dto.mapper;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardMapper {

    public CardResponse toResponse(Card card) {
        if (card == null) return null;

        return CardResponse.builder()
                .id(card.getId())
                .user(card.getUser())
                .number(card.getNumber())
                .date(card.getDate())
                .status(card.getStatus().toString())
                .balance(card.getBalance())
                .build();
    }

    public Card toEntity(CardRequest request) {
        if (request == null) return null;

        return Card.builder()
                .user(request.getUser())
                .number(request.getNumber())
                .date(request.getDate())
                .build();
    }

    public List<CardResponse> toResponse(List<Card> cards){
        if (cards.isEmpty()) return null;
        return cards.stream().map(this::toResponse).collect(Collectors.toList());
    }

}
