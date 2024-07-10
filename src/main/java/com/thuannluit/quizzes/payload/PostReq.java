package com.thuannluit.quizzes.payload;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PostReq {
    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;
}
