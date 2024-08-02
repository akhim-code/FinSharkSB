package com.finshark.backend.services.impls;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.finshark.backend.dtos.comment.CommentDto;
import com.finshark.backend.dtos.comment.CreateCommentDto;
import com.finshark.backend.dtos.comment.UpdateCommentDto;
import com.finshark.backend.dtos.stock.StockDto;
import com.finshark.backend.entities.AppUser;
import com.finshark.backend.entities.Comment;
import com.finshark.backend.entities.Stock;
import com.finshark.backend.exceptions.AppException;
import com.finshark.backend.mappers.CommentsMapper;
import com.finshark.backend.mappers.StocksMapper;
import com.finshark.backend.repositories.AppUserRepository;
import com.finshark.backend.repositories.CommentsRepository;
import com.finshark.backend.repositories.StocksRepository;
import com.finshark.backend.services.CommentsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService{

    private final CommentsRepository commentsRepository;
    private final StocksRepository stocksRepository;
    private final AppUserRepository appUserRepository;
    
    @Override
    public List<CommentDto> findAllComments() {
        return commentsRepository.findAll().stream().map(CommentsMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findCommentsbySymbol(String symbol) {
        Stock stock = stocksRepository.findStockBySymbol(symbol);
        if (stock == null){
            throw new AppException("Stock not found", HttpStatus.NOT_FOUND);
        }
        StockDto stockDto = StocksMapper.toStockDto(stock);
        return stockDto.getCommentDtos();
    }

    @Override
    public CommentDto findByCommentId(Long commentId) {
        return CommentsMapper.toCommentDto(commentsRepository.findById(commentId).orElseThrow(() -> new AppException("Comment not found", HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<CommentDto> findCommentsbyStockSymbol(String stockSymbol) {
        Stock stock = stocksRepository.findStockBySymbol(stockSymbol);
        if (stock == null){
            throw new AppException("Stock not found", HttpStatus.NOT_FOUND);
        }
        StockDto stockDto = StocksMapper.toStockDto(stock);
        return stockDto.getCommentDtos();
    }

    @Override
    public CommentDto createComment(Long appUserId, String symbol, CreateCommentDto createCommentDto) {
        Stock stock  = stocksRepository.findStockBySymbol(symbol);
        if (stock == null){
            throw new AppException("Stock not found", HttpStatus.NOT_FOUND);
        }
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new AppException("AppUser not found", HttpStatus.NOT_FOUND));
        
        Comment comment = CommentsMapper.toCommentFromCreate(createCommentDto, appUser.getUsername());
        comment.setStock(stock);
        commentsRepository.save(comment);
        return CommentsMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(Long commentId, UpdateCommentDto updateCommentDto) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(() -> new AppException("Comment not found", HttpStatus.NOT_FOUND));
        comment.setTitle(updateCommentDto.getTitle());
        comment.setContent(updateCommentDto.getContent());
        commentsRepository.save(comment);
        return CommentsMapper.toCommentDto(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentsRepository.findById(commentId).orElseThrow(() -> new AppException("Comment not found", HttpStatus.NOT_FOUND));
        commentsRepository.delete(comment);
    }

}