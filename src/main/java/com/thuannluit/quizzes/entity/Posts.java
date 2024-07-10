package com.thuannluit.quizzes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table
@Entity(name = "posts")
public class Posts {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "title_post")
    private String title;

    @Column(name = "created_at")
    private Date created_at;

    public Posts(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public Posts() {

    }
}
