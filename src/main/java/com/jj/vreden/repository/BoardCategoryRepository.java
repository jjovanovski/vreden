package com.jj.vreden.repository;

import com.jj.vreden.model.data.BoardCategory;
import com.jj.vreden.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {

    List<BoardCategory> findAllByUser(User user);

}
