# Board (HTML + 로그인 + 페이징) - 8주차

스프링 부트 + JPA + Thymeleaf로 만든 **로그인 기능이 있는 게시판**입니다.
6주차의 API 게시판을, 실제 화면(HTML)과 회원/로그인까지 갖춘 웹 게시판으로 발전시켰습니다.

## 기술 스택
Java 17 · Spring Boot 3.3 · Spring Data JPA · Thymeleaf · H2 · BCrypt · Pagination

## 실행
1. IntelliJ로 폴더 열고 `BoardApplication` 실행
2. 브라우저에서 `http://localhost:8080/posts` 접속
3. H2 콘솔: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/board`)

## 주요 기능
- 회원가입 / 로그인 / 로그아웃 (세션 기반)
- 비밀번호 BCrypt 암호화, 입력값 validation
- 게시글 작성·조회·수정·삭제 (로그인한 사용자만 작성, 작성자만 수정/삭제)
- 제목/내용 키워드 검색 (JPQL)

## 화면(URL) 명세
| 기능 | 메서드 | URL | 권한 |
|------|--------|-----|------|
| 회원가입 화면/처리 | GET/POST | /signup | 누구나 |
| 로그인 화면/처리 | GET/POST | /login | 누구나 |
| 로그아웃 | POST | /logout | 로그인 |
| 목록(+검색) | GET | /posts?keyword= | 누구나 |
| 글쓰기 화면/처리 | GET/POST | /posts/new, /posts | 로그인 |
| 상세 | GET | /posts/{id} | 누구나 |
| 수정 화면/처리 | GET/POST | /posts/{id}/edit | 작성자 |
| 삭제 | POST | /posts/{id}/delete | 작성자 |

## 패키지 구조 (도메인형)
- `member` : 회원/로그인 관련
- `post` : 게시글 관련
- `global` : 공통 설정(PasswordEncoder)

## ERD
회원(MEMBER) 1 : N 게시글(POST). POST가 member_id(FK)로 MEMBER를 참조.

## 8주차 추가 기능 
- 게시글 목록 페이징 (한 페이지 10개, Spring Data JPA의 Page/Pageable)
- 브랜치 전략(feature 브랜치)로 작업 후 merge
