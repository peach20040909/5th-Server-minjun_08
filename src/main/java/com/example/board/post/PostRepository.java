package com.example.board.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 쿼리 메서드: 최신 글이 위로 오도록 id 내림차순 정렬
    List<Post> findAllByOrderByIdDesc();

    // JPQL: 제목 또는 내용에 키워드가 들어간 글 검색
    @Query("select p from Post p where p.title like concat('%', :keyword, '%') " +
           "or p.content like concat('%', :keyword, '%') order by p.id desc")
    List<Post> search(@Param("keyword") String keyword);
}
