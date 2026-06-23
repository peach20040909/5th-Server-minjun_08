package com.example.board.post;

import com.example.board.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)   // 회원 1 : 게시글 N
    @JoinColumn(name = "member_id")
    private Member writer;

    private LocalDateTime createdAt;

    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
