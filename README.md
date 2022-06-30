# project-sharing-server
## Overview
당근마켓과 같은 중고품 거래를 넘어 일회성으로 물건을 빌려주고 빌리는 플랫폼을 지원하는 서비스, lender는 사용하지 않는 물건으로 용돈을 벌고 borrower는 일회성 사용으로 구매가 필요없는 제품 체험의 기회 및 값싼 이용이 가능하다는 장점([소스코드](https://github.com/YHW-LTH/project-sharing-server/tree/release/1.0.0))

## Role
- 웹 프론트와 통신을 위한 API 스펙 설계 및 문서화
- 스프링 시큐리티와 연동한 JWT를 통해 회원가입, 로그인, 로그아웃, 탈퇴 기능 구현
- 아이템, 리뷰, 공유신청, 공유수락, 보증금 및 게시판 등 필요한 Entity 설계 및 CRUD 기능 구현
- HTTP 요청에 맞는 `Controller`, `Service`, `Repository` 설계

## Database Architecture

![database](https://user-images.githubusercontent.com/78265252/176076925-6b82ac6f-63ed-4a81-809d-e1c624661898.png)

## Server Architecture

![server](https://user-images.githubusercontent.com/78265252/176176510-28b1808d-ce48-416f-8565-106370aa5724.png)

## Tech Stack
### Spring Security
- JWT Filter를 Spring security의 `UsernamePasswordAuthenticationFilter` 앞에 추가하여 Filter chain을 customizing한다. HTTP 요청 헤더에 JWT가 존재한다면 JWT를 파싱 하여 인증된 `UsernamePasswordAuthenticationToken`을 `SecurityContext`에 담는다. 이때 메소드 시큐리티 적용시 `userId` 비교가 필요하므로 `UserService`의 `loadByUsername` 메소드를 이용하여 유저 객체를 필드로 담는 `UserContext(User 상속)`를 `principal(UserDetails 구현체)`에 담는다.
- `AccessDeniedException` 또는 `AuthenticationException` 발생 시 `ExceptionTranslationFilter`에서 실행될 `AuthenticationEntryPoint`, `AccessDeniedHandler`를 customizing 한다.
- 로그아웃 기능을 구현하기 위해 (만료 시각 - 로그아웃 시각) 기간만큼 토큰을 저장하는 Redis를 이용한다.
- `AuthenticationEntryPoint`, `AccessDeniedHandler`에서는 요청 URI에 따라 Redirect 시켜 API 스펙에 맞게 예외를 처리한다.
- 프론트와의 협업에서 발생하는 CORS문제 해결을 위해 `CorsFilter`를 활성화 시키고 허용 가능한 `Origin`, `Method`, `Header`를 설정한다.
- 커스텀한 `@CurrentUser` 애노테이션을 통해 `ArgumentResolver`에서 컨트롤러에게 `user` 객체를 넘겨 받는다.
- 탈퇴와 같이 특정 유저만 접근 가능한 메소드의 경우 `userId`를 이용하여 애노테이션 기반 AOP 메소드 인증 / 인가 검증을 한다.

### Spring Data JPA & Querydsl
- 서비스 운용에 필요한 Entity를 설계하고 객체를 관계형 데이터베이스와 매핑하기 위해 자바 진영의 ORM인 JPA를 이용한다.
- 동적인 JPQL 생성을 위해 JPQL builder인 Querydsl를 이용한다. 게시판 리스트, 리뷰 리스트, 개인정보 리스트(보유중인 아이템 리스트, 공유신청 리스트 등)등 여러 테이블을 조인하여 DTO와 검색조건에 맞는 데이터를 선택 및 정렬하여 리소소를 제공한다.
- 지연 로딩과 페치조인을 통해 `1 + N` 문제를 해결하고 컬렉션 조인의 경우 `BatchSize` 를 통해 `1 + 1` 문제로 치환한다.

### Spring Web
- 예외 및 검증 에러 발생시 처리 책임을 `@RestControllerAdvice`로 단일화한다.
- `String` -> `LocalDate`로 변환을 하기 위해 `JavaTimeModule`를 등록한 커스텀 `ObjectMapper`를 빈으로 등록한다.
- 이미지 파일 등록 및 수정을 위해 `MediaType.MULTIPART_FORM_DATA_VALUE` 형태로 데이터를 받고 `MultipartFile`를 이용하여 파일을 저장한다.

### Jenkins
- CI&CD 기능 구현을 위해 `github webhook`를 통한 `jar`빌드 후 셸 스크립트를 이용하여 도커 이미지를 빌드하고 `docker hub`에 푸쉬한다.
- 서버 디스크 볼륨을 증가시키고 원활한 빌드를 위해 `swap` 기능을 구현한다.

### Docker
- 이미지를 이용한 컨테이너 배포를 위해 `docker-compose`를 이용한다.
- 서버에 저장된 이미지 파일을 영속적으로 보관하기 위해 볼륨을 마운트한다.
- 컨테이너 안에 저장된 spring log 파일을 서버 볼륨에 마운트하여 서버 모니터링이 가능하게 한다.

### Swagger
- 웹 프론트와의 협업을 위해 API 스펙을 문서화하여 제공한다.
- `Pathvariable`, `Queryparam`, `RueqestBody` 등 필요한 데이터를 명시하고 응답 코드 및 응답 데이터를 명시한다.

![swagger](https://user-images.githubusercontent.com/78265252/176076989-b5180341-3e7a-4199-b6fb-e06b16749fb1.png)

### Git
- 프로젝트 버전별, 기능별 형상관리를 위해 git을 이용한다.
- 프론트와 하나의 프로젝트로 관리하기 위해 `Organization`을 생성하고 `upstream`, `origin`, `local` 순으로 레포지토리를 만든다.
- 로컬에서 개발 후 `orgin`으로 푸쉬를 하고 `upstream`에 `Pull Request` 생성 후 `Squash and Merge`를 한다.
