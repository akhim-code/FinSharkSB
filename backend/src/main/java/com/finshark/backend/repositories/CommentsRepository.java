package com.finshark.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finshark.backend.entities.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long>{
    
}