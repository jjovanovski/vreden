package com.jj.vreden.controller;

import com.jj.vreden.model.BoardCategory;
import com.jj.vreden.model.data.User;
import com.jj.vreden.repository.BoardCategoryRepository;
import com.jj.vreden.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/boardcategory")
public class BoardCategoryController {

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<BoardCategory> getBoardCategories(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        return boardCategoryRepository.findAllByUser(user);
    }

    @GetMapping(path = "/{id}")
    public BoardCategory getBoardCategory(Principal principal, @PathVariable long id) {
        Optional<BoardCategory> boardCategoryOptional = boardCategoryRepository.findById(id);
        boardCategoryOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));

        BoardCategory boardCategory = boardCategoryOptional.get();
        User user = userRepository.findByUsername(principal.getName());

        if(boardCategory.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, null);
        }

        return boardCategory;
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
    public BoardCategory putBoardCategory(Principal principal, @PathVariable long id, @RequestParam String name) {
        Optional<BoardCategory> boardCategoryOptional = boardCategoryRepository.findById(id);
        boardCategoryOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));

        BoardCategory boardCategory = boardCategoryOptional.get();
        User user = userRepository.findByUsername(principal.getName());

        if(boardCategory.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, null);
        }

        boardCategory.setName(name);
        return boardCategoryRepository.save(boardCategory);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteBoardCategory(Principal principal, @PathVariable long id) {
        Optional<BoardCategory> boardCategoryOptional = boardCategoryRepository.findById(id);
        boardCategoryOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));

        BoardCategory boardCategory = boardCategoryOptional.get();
        User user = userRepository.findByUsername(principal.getName());

        if(boardCategory.getUser() != user) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, null);
        }

        boardCategoryRepository.deleteById(id);
    }

}
