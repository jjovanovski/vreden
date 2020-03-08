package com.jj.vreden.repository;

import com.jj.vreden.model.data.Board;
import com.jj.vreden.model.data.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    List<BoardList> findAllByBoardOrderByOrderInBoard(Board board);

}
