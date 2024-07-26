package com.finshark.backend.services;

import java.util.List;

import com.finshark.backend.dtos.comment.CommentDto;
import com.finshark.backend.dtos.comment.CreateCommentDto;
import com.finshark.backend.dtos.comment.UpdateCommentDto;

public interface CommentsService {
    List<CommentDto> findAllComments();
    CommentDto findByCommentId(Long commentId);
    CommentDto createComment(Long appUserId, String symbol, CreateCommentDto createCommentDto);
    CommentDto updateComment(Long commentId, UpdateCommentDto updateCommentDto);
    void deleteComment(Long commentId);
}