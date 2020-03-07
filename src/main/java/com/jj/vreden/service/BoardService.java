package com.jj.vreden.service;

import com.jj.vreden.configuration.SecurityConfiguration;
import com.jj.vreden.model.data.Board;
import com.jj.vreden.model.data.BoardCategory;
import com.jj.vreden.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BoardService extends UserService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCategoryService boardCategoryService;

    public List<Board> get() {
        return boardRepository.findByUser(getUser());
    }

    @PostAuthorize(SecurityConfiguration.PRIVATE_USER_RESOURCE)
    public Board get(long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));
        return board;
    }

    public Board post(String name, long categoryId) {
        BoardCategory boardCategory = boardCategoryService.get(categoryId);

        Board board = new Board();
        board.setName(name);
        board.setBoardCategory(boardCategory);
        board.setUser(getUser());

        return boardRepository.save(board);
    }

    public Board put(long id, String name, long categoryId) {
        BoardCategory boardCategory = boardCategoryService.get(categoryId);

        Board board = get(id);
        board.setName(name);
        board.setBoardCategory(boardCategory);

        return boardRepository.save(board);
    }

    public void delete(long id) {
        Board board = get(id);
        boardRepository.delete(board);
    }

}
