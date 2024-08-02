package com.finshark.backend.services;

import java.util.List;

import com.finshark.backend.dtos.comment.CommentDto;
import com.finshark.backend.dtos.comment.CreateCommentDto;
import com.finshark.backend.dtos.comment.UpdateCommentDto;

public interface CommentsService {
    List<CommentDto> findAllComments();
    List<CommentDto> findCommentsbySymbol(String symbol);
    CommentDto findByCommentId(Long commentId);
    List<CommentDto> findCommentsbyStockSymbol(String stockSymbol);
    CommentDto createComment(Long appUserId, String symbol, CreateCommentDto createCommentDto);
    CommentDto updateComment(Long commentId, UpdateCommentDto updateCommentDto);
    void deleteComment(Long commentId);
}