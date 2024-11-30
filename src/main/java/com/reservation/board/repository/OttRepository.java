package com.reservation.board.repository;

import com.reservation.board.model.Ott;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OttRepository extends JpaRepository<Ott, Long> {
  Optional<Ott> findByName(String name);
}