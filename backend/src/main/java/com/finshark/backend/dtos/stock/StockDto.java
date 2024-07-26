package com.finshark.backend.dtos.stock;

import java.util.List;

import com.finshark.backend.dtos.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockDto {

    private Long id;
    private String symbol; 
    private double purchase;
    private double dividend;
    private String industry;
    private Long marketCap;
    private String companyName;
    private List<CommentDto> commentDtos;
}
