package com.finshark.backend.dtos.comment;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdOn;
    private String appUserUsername;
    private Long stockId;
}
