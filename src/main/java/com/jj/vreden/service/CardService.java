package com.jj.vreden.service;

import com.jj.vreden.configuration.SecurityConfiguration;
import com.jj.vreden.model.data.BoardList;
import com.jj.vreden.model.data.Card;
import com.jj.vreden.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CardService extends UserService {

    @Autowired
    private CardService proxy;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private BoardListService boardListService;

    //@PostAuthorize("returnObject.boardList.board.user == authentication.user")
    public Card get(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));
        return card;
    }

    public Card post(String name, String description, long boardListId) {
        BoardList boardList = boardListService.get(boardListId);

        float lastOrderInList = 0.0f;
        if(boardList.getCards().size() > 0) {
            lastOrderInList = boardList.getCards().get(boardList.getCards().size() - 1).getOrderInBoardList();
        }
        float orderInList = lastOrderInList + 1.0f;

        Card card = new Card();
        card.setName(name);
        card.setDescription(description);
        card.setBoardList(boardList);
        card.setOrderInBoardList(orderInList);

        return cardRepository.save(card);
    }

    public Card put(long id, String name, String description) {
        Card card = proxy.get(id);
        card.setName(name);
        card.setDescription(description);

        return cardRepository.save(card);
    }

    public void delete(long id) {
        Card card = proxy.get(id);
        cardRepository.delete(card);
    }

    public void reorder(long id, long idAfter, long idBefore, long boardListId) {
        Card card = proxy.get(id);
        Card cardAfter = null;
        Card cardBefore = null;
        BoardList boardList = null;

        if(idAfter > 0) {
            cardAfter = proxy.get(idAfter);
        }

        if(idBefore > 0) {
            cardBefore = proxy.get(idBefore);
        }

        if(boardListId > 0) {
            boardList = boardListService.get(boardListId);
            card.setBoardList(boardList);
        }

        float newOrderInList = card.getOrderInBoardList();
        if(cardAfter != null && cardBefore != null) {
            newOrderInList = (cardAfter.getOrderInBoardList() + cardBefore.getOrderInBoardList()) / 2.0f;
        } else if(cardAfter != null) {
            newOrderInList = cardAfter.getOrderInBoardList() + 1.0f;
        } else if(cardBefore != null) {
            newOrderInList = cardBefore.getOrderInBoardList() - 1.0f;
        }

        card.setOrderInBoardList(newOrderInList);
        cardRepository.save(card);
    }

}
