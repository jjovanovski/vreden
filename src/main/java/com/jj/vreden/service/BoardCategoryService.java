package com.jj.vreden.service;

import com.jj.vreden.configuration.SecurityConfiguration;
import com.jj.vreden.model.BoardCategory;
import com.jj.vreden.repository.BoardCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class BoardCategoryService {

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @PostAuthorize(SecurityConfiguration.PRIVATE_USER_RESOURCE)
    public BoardCategory getBoardCategory(long id) {
        Optional<BoardCategory> boardCategoryOptional = boardCategoryRepository.findById(id);
        boardCategoryOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));
        return boardCategoryOptional.get();
    }

}
