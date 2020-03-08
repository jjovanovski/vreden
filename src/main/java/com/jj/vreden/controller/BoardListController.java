package com.jj.vreden.controller;

import com.jj.vreden.model.data.BoardList;
import com.jj.vreden.service.BoardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/boardlist")
public class BoardListController {

    @Autowired
    private BoardListService boardListService;

    @GetMapping
    public List<BoardList> getBoardListsByBoard(@RequestParam long boardId) {
        return boardListService.getByBoard(boardId);
    }

    @PostMapping
    public BoardList postBoardList(@RequestParam String name, @RequestParam long boardId) {
        return boardListService.post(name, boardId);
    }

    @PutMapping(path = "/{id}")
    public BoardList putBoardList(@PathVariable long id, @RequestParam String name) {
        return boardListService.put(id, name);
    }

    @DeleteMapping(path = "{id}")
    public void deleteBoardList(@PathVariable long id) {
        boardListService.delete(id);
    }

    @PutMapping("/reorder/{id}")
    public void reorderBoardList(
            @PathVariable long id,
            @RequestParam(defaultValue = "-1") long afterId,
            @RequestParam(defaultValue = "-1") long beforeId
    ) {
        boardListService.reorder(id, afterId, beforeId);
    }

}
