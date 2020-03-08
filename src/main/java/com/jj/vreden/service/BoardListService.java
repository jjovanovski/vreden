package com.jj.vreden.service;

import com.jj.vreden.model.data.Board;
import com.jj.vreden.model.data.BoardList;
import com.jj.vreden.repository.BoardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BoardListService {

    @Autowired
    private BoardListService proxy;

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private BoardService boardService;

    public List<BoardList> getByBoard(long boardId) {
        return boardListRepository.findAllByBoardOrderByOrderInBoard(boardService.get(boardId));
    }

    //@PostAuthorize("returnObject.board.user == authentication.name")
    public BoardList get(long id) {
        BoardList boardList = boardListRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, null));
        return boardList;
    }

    public BoardList post(String name, long boardId) {
        List<BoardList> listsInBoard = proxy.getByBoard(boardId);
        float lastOrderInBoard = 0.0f;
        if(listsInBoard.size() > 0) {
            lastOrderInBoard = listsInBoard.get(listsInBoard.size() - 1).getOrderInBoard();
        }
        float orderInBoard = lastOrderInBoard + 1.0f;

        BoardList boardList = new BoardList();
        boardList.setName(name);
        boardList.setBoard(boardService.get(boardId));
        boardList.setOrderInBoard(orderInBoard);

        return boardListRepository.save(boardList);
    }

    public BoardList put(long id, String name) {
        BoardList boardList = proxy.get(id);
        boardList.setName(name);

        return boardListRepository.save(boardList);
    }

    public void delete(long id) {
        BoardList boardList = proxy.get(id);
        boardListRepository.delete(boardList);
    }

    public void reorder(long id, long idAfter, long idBefore) {
        BoardList boardList = proxy.get(id);
        BoardList boardListAfter = null;
        BoardList boardListBefore = null;

        if(idAfter > 0) {
            boardListAfter = proxy.get(idAfter);
        }

        if(idBefore > 0) {
            boardListBefore = proxy.get(idBefore);
        }

        float newOrderInBoard = boardList.getOrderInBoard();
        if(boardListAfter != null && boardListBefore != null) {
            newOrderInBoard = (boardListAfter.getOrderInBoard() + boardListBefore.getOrderInBoard()) / 2.0f;
        } else if(boardListAfter != null) {
            newOrderInBoard = boardListAfter.getOrderInBoard() + 1.0f;
        } else if(boardListBefore != null) {
            newOrderInBoard = boardListBefore.getOrderInBoard() - 1.0f;
        }

        boardList.setOrderInBoard(newOrderInBoard);
        boardListRepository.save(boardList);
    }

}
