package com.example.bankcards.service;

import com.example.bankcards.dto.mapper.CardMapper;
import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.enums.Status;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.EncryptionService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CardService {
    private final CardRepository cardRepository;
    private final UserService userService;
    private final CardMapper cardMapper;
    private final EncryptionService encryptionService;

    @Autowired
    public CardService(CardRepository cardRepository,
                       UserService userService,
                       CardMapper cardMapper,
                       EncryptionService encryptionService) {
        this.cardRepository = cardRepository;
        this.userService = userService;
        this.cardMapper = cardMapper;
        this.encryptionService = encryptionService;
    }

    @Transactional(readOnly = true)
    public List<CardResponse> getCards() {
        List<Card> cards = cardRepository.findAll();
        checkExpiryDate(cards);
        return cardMapper.toResponse(cards);
    }

    @Transactional(readOnly = true)
    public CardResponse getCardById(Long id) {
        Card card = getCardEntity(id);
        checkExpiryDate(card);
        return cardMapper.toResponse(card);
    }

    public CardResponse createCard(CardRequest request) {
        User user = userService.getUserEntity(request.getUserId());

        String number = encryptionService.encrypt(request.getNumber());

        if (cardRepository.existsByNumber(number)) {
            throw new IllegalArgumentException("Card already exist");
        }

        Card card = Card.builder()
                .user(user)
                .number(number)
                .date(request.getDate())
                .build();

        return cardMapper.toResponse(card);
    }

    public void deleteCard(Long id){
        Card card = getCardEntity(id);

        cardRepository.delete(card);
    }

    public CardResponse blockCard(Long id){
        Card card = getCardEntity(id);
        if (card.getStatus()==Status.EXPIRED){
            throw new IllegalStateException("Card cannot be blocked, date expired");
        }
        card.setStatus(Status.BLOCKED);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    public CardResponse unblockCard(Long id){
        Card card = getCardEntity(id);
        if (card.getStatus()==Status.EXPIRED){
            throw new IllegalStateException("Card cannot be activated, date expired");
        }
        card.setStatus(Status.ACTIVE);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Transactional(readOnly = true)
    public Card getCardEntity(Long id) {
        return cardRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Card entity not found: " + id)
        );
    }

    private void checkExpiryDate(List<Card> cards) {
        cards.forEach(this::checkExpiryDate);
    }

    public void checkExpiryDate(Card card) {
        if (card.getDate().isBefore(LocalDate.now()) && card.getStatus() != Status.EXPIRED) {
            card.setStatus(Status.EXPIRED);
            cardRepository.save(card);
        }
    }
}
