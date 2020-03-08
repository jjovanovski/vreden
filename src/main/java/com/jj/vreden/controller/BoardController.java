package com.jj.vreden.controller;

import com.jj.vreden.configuration.SecurityConfiguration;
import com.jj.vreden.model.data.Board;
import com.jj.vreden.repository.BoardRepository;
import com.jj.vreden.repository.UserRepository;
import com.jj.vreden.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public List<Board> getBoards() {
        return boardService.get();
    }

    @GetMapping(path = "/{id}")
    public Board getBoard(@PathVariable long id) {
        return boardService.get(id);
    }

    @PostMapping
    public Board postBoard(String name, long categoryId) {
        return boardService.post(name, categoryId);
    }

    @PutMapping(path = "/{id}")
    public Board putBoard(@PathVariable long id, String name, long categoryId) {
        return boardService.put(id, name, categoryId);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBoard(@PathVariable long id) {
        boardService.delete(id);
    }

}
