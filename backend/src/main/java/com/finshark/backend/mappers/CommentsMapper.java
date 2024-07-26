package com.finshark.backend.mappers;
import java.util.List;

import org.mapstruct.Mapper;

import com.finshark.backend.dtos.comment.CommentDto;
import com.finshark.backend.dtos.comment.CreateCommentDto;
import com.finshark.backend.entities.Comment;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    static Comment toCommentFromCreate(CreateCommentDto commentDto, String appUserUsername) {
        Comment comment = new Comment();
        comment.setTitle(commentDto.getTitle());
        comment.setContent(commentDto.getContent());
        comment.setAppUserUsername(appUserUsername);
        return comment;
    }

    static CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setTitle(comment.getTitle());  
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedOn(comment.getCreatedOn());
        commentDto.setAppUserUsername(comment.getAppUserUsername());
        commentDto.setStockId(comment.getStock().getId());
        return commentDto;
    }

    static List<CommentDto> toCommentDtos(List<Comment> comments) {
        return comments.stream().map(CommentsMapper::toCommentDto).toList();
    }
}