package com.example.board.member;

import com.example.board.member.dto.LoginForm;
import com.example.board.member.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignUpForm form) {
        if (memberRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        String encoded = passwordEncoder.encode(form.getPassword());
        Member member = new Member(form.getEmail(), encoded, form.getNickname());
        return memberRepository.save(member).getId();
    }

    // 로그인 성공 시 Member 반환, 실패 시 예외
    public Member login(LoginForm form) {
        Member member = memberRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(form.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return member;
    }
}
