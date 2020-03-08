package com.jj.vreden.controller;

import com.jj.vreden.model.data.Card;
import com.jj.vreden.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public Card postCard(String name, String description, long boardListId) {
        return cardService.post(name, description, boardListId);
    }

    @PutMapping("/{id}")
    public Card putCard(@PathVariable long id, String name, String description) {
        return cardService.put(id, name, description);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable long id) {
        cardService.delete(id);
    }

    @PutMapping("/reorder/{id}")
    public void reorderCard(
            @PathVariable long id,
            @RequestParam(defaultValue = "-1") long idAfter,
            @RequestParam(defaultValue = "-1") long idBefore,
            @RequestParam(defaultValue = "-1") long boardListId
    ) {
        cardService.reorder(id, idAfter, idBefore, boardListId);
    }

}
