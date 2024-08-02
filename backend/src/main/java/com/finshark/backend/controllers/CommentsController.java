package com.finshark.backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finshark.backend.dtos.comment.CommentDto;
import com.finshark.backend.dtos.comment.CreateCommentDto;
import com.finshark.backend.dtos.comment.UpdateCommentDto;
import com.finshark.backend.services.CommentsService;
import com.finshark.backend.services.FMPService;
import com.finshark.backend.dtos.user.AppUserDto;
import com.finshark.backend.entities.Stock;
import com.finshark.backend.repositories.StocksRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;
    private final StocksRepository stocksRepository;
    private final FMPService fmpService;

    @GetMapping("/api/comment/")
    public ResponseEntity<List<CommentDto>> findAllComments() {
        return ResponseEntity.ok(commentsService.findAllComments());
    }

    @GetMapping("/api/comment/search")
    public ResponseEntity<List<CommentDto>> findCommentsBySymbol(@RequestParam(value = "symbol") String symbol) {
        return ResponseEntity.ok(commentsService.findCommentsbySymbol(symbol));
    }

    @GetMapping("/api/comment/{commentId}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentsService.findByCommentId(commentId));
    }

    @PostMapping("/api/comment/{symbol}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("symbol") String symbol, @RequestBody CreateCommentDto createCommentDto) {
        symbol = symbol.toUpperCase();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Stock stock = stocksRepository.findStockBySymbol(symbol);
        if (stock == null)
        {
            stock = fmpService.findStockBySymbol(symbol).join();
            if (stock == null) {
                return ResponseEntity.notFound().build();
            }
            else {
                stocksRepository.save(stock);
            }
        }

        return ResponseEntity.ok(commentsService.createComment(((AppUserDto) authentication.getPrincipal()).getId(), symbol, createCommentDto));
    }

    @PutMapping("/api/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateCommentDto updateCommentDto) {
        return ResponseEntity.ok(commentsService.updateComment(commentId, updateCommentDto));
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) {
        commentsService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
    
}
