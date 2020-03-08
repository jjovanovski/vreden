package com.jj.vreden.repository;

import com.jj.vreden.model.data.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
