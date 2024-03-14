package com.example.demo.src.comment;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.board.BoardRepository;
import com.example.demo.src.board.entity.Board;
import com.example.demo.src.comment.entity.Comment;
import com.example.demo.src.comment.model.PostCommentReq;
import com.example.demo.src.comment.model.PostCommentRes;
import com.example.demo.src.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.common.code.status.ErrorStatus.NOT_FIND_BOARD;
import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public PostCommentRes createdComment(User user, Long boardId, PostCommentReq postCommentReq) {
        Board board = boardRepository.findByIdAndState(boardId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_BOARD));
        Comment comment = CommentConverter.toComment(user, board, postCommentReq);
        return CommentConverter.toPostCommentRes(commentRepository.save(comment).getId());
    }
}