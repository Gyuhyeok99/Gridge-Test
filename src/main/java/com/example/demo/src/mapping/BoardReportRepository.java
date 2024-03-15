package com.example.demo.src.mapping;

import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.src.board.entity.Board;
import com.example.demo.src.mapping.entity.BoardReport;
import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {

    Optional<BoardReport> findByBoardAndUserAndState(Board board, User user, State state);
}