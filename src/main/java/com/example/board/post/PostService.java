package com.example.board.post;

import com.example.board.member.Member;
import com.example.board.member.MemberRepository;
import com.example.board.post.dto.PostForm;
import com.example.board.post.dto.PostView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(PostForm form, Long memberId) {
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        Post post = new Post(form.getTitle(), form.getContent(), writer);
        return postRepository.save(post).getId();
    }

    public List<PostView> findAll() {
        return postRepository.findAllByOrderByIdDesc().stream().map(PostView::from).toList();
    }

    public List<PostView> search(String keyword) {
        return postRepository.search(keyword).stream().map(PostView::from).toList();
    }

    public PostView findOne(Long id) {
        return PostView.from(getPost(id));
    }

    @Transactional
    public void update(Long id, PostForm form) {
        getPost(id).update(form.getTitle(), form.getContent());
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다. id=" + id));
    }
}
