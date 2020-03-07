package com.jj.vreden.repository;

import com.jj.vreden.model.data.Board;
import com.jj.vreden.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByUser(User user);

}
