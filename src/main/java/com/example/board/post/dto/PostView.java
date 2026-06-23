package com.example.board.post.dto;

import com.example.board.post.Post;
import java.time.LocalDateTime;

// 화면에 보여줄 게시글 정보 (writerId는 본인 글인지 확인용)
public record PostView(
        Long id,
        String title,
        String content,
        String writerNickname,
        Long writerId,
        LocalDateTime createdAt
) {
    public static PostView from(Post p) {
        return new PostView(
                p.getId(), p.getTitle(), p.getContent(),
                p.getWriter().getNickname(), p.getWriter().getId(), p.getCreatedAt());
    }
}
