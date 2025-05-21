## Spring을 이용한 쇼핑몰 웹사이트
Spring Boot를 공부하며, 여러 기능을 담은 Spring Boot 기반의 쇼핑몰 웹사이트 제작 및 배포

배포 주소 : http://yoonyoulcodingapplespring1-env.eba-bpzregrh.ap-northeast-2.elasticbeanstalk.com/

---

## 🚀 프로젝트 개요

- **Spring Boot 3.4**, **Java 17**, **Spring Security 6**와 **JWT**를 이용한 인증/인가  
- 서버사이드 렌더링을 위한 **Thymeleaf**  
- 데이터 영속화: **Spring Data JPA** + **Hibernate**  
- 이미지 저장: **AWS S3** → Presigned URL 방식  
- RESTful API + HTML 뷰 동시 지원  

---

## 🌟 주요 기능

### 1. 사용자 관리 (member 패키지)
- 회원 가입 / 로그인  
- **JWT** 발급·검증 (`JwtUtil`, `JwtFilter`)  
- 비밀번호 암호화 (BCrypt)  
- `CustomUser` / `MyUserDetailsService`로 Spring Security 연동  
- `MemberController`를 통한 프로필 조회, 사용자 정보 수정  

### 2. 상품 관리 (item 패키지)
- 상품 등록 / 조회 / 수정 / 삭제  
- 작성자(user)·관리자(admin) 권한 기반 접근 제어  
- **페이징** (`PageRequest`) 및 **검색** 기능  
- 이미지 업로드: 프런트에서 S3 Presigned URL 요청 → 직접 PUT  
- `S3Service`로 Presigned URL 생성  
- 상세 페이지, 목록 페이지, 검색 결과 페이지 제공  

### 3. 댓글 시스템 (comment 패키지)
- 특정 상품에 대한 댓글 CRUD  
- **페이지네이션** 지원  
- `CommentController` / `CommentService` → 댓글 등록·삭제·조회  

### 4. 판매 이력 관리 (sales 패키지)
- 판매 내역 기록 (상품, 수량, 판매가 등)  
- 사용자별/상품별 판매 통계 조회  
- `SalesDto`를 통한 데이터 전송  
- `SalesController` / `SalesService` → RESTful 판매 API  

---

<!--
### 구현한 기능
- pagnation
- 검색 기능 (Full Text Index)
- 세션 로그인
- JWT 로그인
- 테스트 코드
-->

## 📂 패키지 구조
com.apple.shop

├── member // 사용자(JWT, 인증/인가, 프로필)

├── item // 상품(CRUD, 이미지 업로드, 검색, 페이징)

├── comment // 댓글(쓰기, 조회, 삭제, 대댓글, 페이징)

└── sales // 판매 이력(기록, 조회, 통계)

---

## 🛠 기술 스택

- **Spring Boot 3.4**  
- **Spring Security 6** + **JWT**  
- **Thymeleaf** (Spring MVC 템플릿)  
- **Spring Data JPA** (Hibernate)  
- **MySQL** (또는 H2)  
- **AWS S3** (프라이빗 버킷 + Presigned URL)  
- **Lombok**, **MapStruct** 등  

---

### 사용 언어
- Java
- MYSQL

### Database
- Microsoft Azure

### 배포 환경
- AWS Elastic Beanstalk
