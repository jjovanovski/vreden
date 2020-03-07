package com.jj.vreden.controller;

import com.jj.vreden.model.data.BoardCategory;
import com.jj.vreden.model.data.User;
import com.jj.vreden.repository.BoardCategoryRepository;
import com.jj.vreden.repository.UserRepository;
import com.jj.vreden.service.BoardCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/boardcategory")
public class BoardCategoryController {

    @Autowired
    private BoardCategoryService boardCategoryService;

    @GetMapping
    public List<BoardCategory> getBoardCategories() {
        return boardCategoryService.get();
    }

    @GetMapping(path = "/{id}")
    public BoardCategory getBoardCategory(@PathVariable long id) {
        return boardCategoryService.get(id);
    }

    @PostMapping
    public BoardCategory postBoardCategory(@RequestParam String name) {
        return boardCategoryService.post(name);
    }

    @PutMapping(path = "/{id}")
    public BoardCategory putBoardCategory(@PathVariable long id, @RequestParam String name) {
        return boardCategoryService.put(id, name);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBoardCategory(@PathVariable long id) {
        boardCategoryService.delete(id);
    }

}
