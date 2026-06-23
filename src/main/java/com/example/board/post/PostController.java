package com.example.board.post;

import com.example.board.post.dto.PostForm;
import com.example.board.post.dto.PostView;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 목록 (+ 검색)
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        boolean searching = keyword != null && !keyword.isBlank();
        model.addAttribute("posts", searching ? postService.search(keyword) : postService.findAll());
        model.addAttribute("keyword", keyword);
        return "posts/list";
    }

    // 글쓰기 화면 (로그인 필요)
    @GetMapping("/new")
    public String writeForm(HttpSession session, Model model) {
        if (loginId(session) == null) return "redirect:/login";
        model.addAttribute("postForm", new PostForm());
        return "posts/form";
    }

    @PostMapping
    public String write(@Valid @ModelAttribute PostForm postForm, BindingResult result, HttpSession session) {
        Long memberId = loginId(session);
        if (memberId == null) return "redirect:/login";
        if (result.hasErrors()) return "posts/form";
        Long id = postService.create(postForm, memberId);
        return "redirect:/posts/" + id;
    }

    // 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        PostView post = postService.findOne(id);
        Long memberId = loginId(session);
        model.addAttribute("post", post);
        model.addAttribute("isWriter", memberId != null && memberId.equals(post.writerId()));
        return "posts/detail";
    }

    // 수정 화면 (작성자만)
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        PostView post = postService.findOne(id);
        if (!isWriter(session, post)) return "redirect:/posts/" + id;
        PostForm form = new PostForm();
        form.setTitle(post.title());
        form.setContent(post.content());
        model.addAttribute("postForm", form);
        model.addAttribute("postId", id);
        return "posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Valid @ModelAttribute PostForm postForm,
                       BindingResult result, HttpSession session, Model model) {
        PostView post = postService.findOne(id);
        if (!isWriter(session, post)) return "redirect:/posts/" + id;
        if (result.hasErrors()) {
            model.addAttribute("postId", id);
            return "posts/edit";
        }
        postService.update(id, postForm);
        return "redirect:/posts/" + id;
    }

    // 삭제 (작성자만)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        PostView post = postService.findOne(id);
        if (isWriter(session, post)) postService.delete(id);
        return "redirect:/posts";
    }

    private Long loginId(HttpSession session) {
        return (Long) session.getAttribute("loginMemberId");
    }

    private boolean isWriter(HttpSession session, PostView post) {
        Long memberId = loginId(session);
        return memberId != null && memberId.equals(post.writerId());
    }
}
