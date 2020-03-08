package com.jj.vreden.service;

import com.jj.vreden.configuration.SecurityConfiguration;
import com.jj.vreden.model.data.BoardCategory;
import com.jj.vreden.repository.BoardCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BoardCategoryService extends UserService {

    @Autowired
    private BoardCategoryService proxy;

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    public List<BoardCategory> get() {
        return boardCategoryRepository.findAllByUser(getUser());
    }

    @PostAuthorize(SecurityConfiguration.PRIVATE_USER_RESOURCE)
    public BoardCategory get(long id) {
        BoardCategory boardCategoryOptional = boardCategoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));
        return boardCategoryOptional;
    }

    public BoardCategory post(String name) {
        BoardCategory boardCategory = new BoardCategory();
        boardCategory.setName(name);
        boardCategory.setUser(getUser());

        return boardCategoryRepository.save(boardCategory);
    }

    public BoardCategory put(long id, String name) {
        BoardCategory boardCategory = proxy.get(id);
        boardCategory.setName(name);

        return boardCategoryRepository.save(boardCategory);
    }

    public void delete(long id) {
        BoardCategory boardCategory = proxy.get(id);
        boardCategoryRepository.delete(boardCategory);
    }
}
