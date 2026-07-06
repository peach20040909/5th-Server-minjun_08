package com.example.board.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 페이징 조회 (최신순)
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    // 검색도 페이징
    @Query("select p from Post p where p.title like concat('%', :keyword, '%') " +
            "or p.content like concat('%', :keyword, '%') order by p.id desc")
    Page<Post> search(@Param("keyword") String keyword, Pageable pageable);
}