package com.jj.vreden.controller;

import com.jj.vreden.model.BoardCategory;
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
    private BoardCategoryRepository boardCategoryRepository;

    @Autowired
    private BoardCategoryService boardCategoryService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<BoardCategory> getBoardCategories(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return boardCategoryRepository.findAllByUser(user);
    }

    @GetMapping(path = "/{id}")
    public BoardCategory getBoardCategory(@PathVariable long id) {
        return boardCategoryService.getBoardCategory(id);
    }

    @PostMapping
    public BoardCategory postBoardCategory(Principal principal, @RequestParam String name) {
        User user = userRepository.findByUsername(principal.getName());
        BoardCategory boardCategory = new BoardCategory();
        boardCategory.setName(name);
        boardCategory.setUser(user);

        return boardCategoryRepository.save(boardCategory);
    }

    @PutMapping(path = "/{id}")
    public BoardCategory putBoardCategory(@PathVariable long id, @RequestParam String name) {
        BoardCategory boardCategory = boardCategoryService.getBoardCategory(id);
        boardCategory.setName(name);

        return boardCategoryRepository.save(boardCategory);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBoardCategory(Principal principal, @PathVariable long id) {
        BoardCategory boardCategory = boardCategoryService.getBoardCategory(id);
        boardCategoryRepository.delete(boardCategory);
    }

}
