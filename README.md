# Han-geureut Backend

> 음식 사진 기반 AI 분석, 앨범/리뷰/검색 기능을 제공하는 Spring Boot 백엔드 서버입니다.

---

## 📌 프로젝트 개요

Han-geureut Backend는 사용자 인증(JWT/OAuth2), 음식 앨범 관리, 리뷰 작성/조회, 장소 검색 및 지도 연동 기능을 제공합니다.
사진 업로드 및 메타데이터 처리, 외부 API 연동(Google Maps/Places, Vision, S3)을 통해 프론트엔드에 필요한 데이터를 통합 제공합니다.

---

## ✨ 주요 기능

- **인증/회원**
  - 일반 로그인 및 회원가입
  - OAuth2 로그인(카카오, 구글, 네이버)
  - JWT Access/Refresh Token 발급 및 검증
- **앨범**
  - 앨범 생성/삭제/상세 조회
  - 앨범 좋아요/좋아요 취소
  - 정렬 기준 기반 페이징 조회(최신순, 인기순 등)
- **리뷰**
  - 리뷰 생성/조회/삭제
  - 장소 기반 리뷰 조회
- **검색/장소**
  - 키워드 검색
  - 장소 상세 조회 및 지도 정보 연동
- **미디어 처리**
  - 사진 메타데이터 추출
  - Google Vision 기반 이미지 분석
  - S3 업로드 및 URL 관리

---

## 🛠 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 3.2.1
- **Build Tool**: Gradle
- **Database**: MySQL, Spring Data JPA
- **Security**: Spring Security, OAuth2 Client, JWT
- **Cloud/External**: AWS S3, Google Maps/Places API, Google Vision API
- **Etc**: Lombok, Validation, WebFlux, Log4j2

---

## 📂 프로젝트 구조

```text
src/main/java/hangeureut
├─ domain
│  ├─ album
│  ├─ photo
│  ├─ review
│  ├─ search
│  └─ user
└─ global
   ├─ config
   ├─ security
   ├─ exception
   ├─ response
   ├─ aws
   └─ gcp
```

---

## 🔐 환경변수 설정

`application.yml`은 공통 설정, `application-secret.yml`은 민감정보를 관리합니다.

### 1) `application.yml` 예시 (공통)

```yaml
spring:
  profiles:
    include: secret
  datasource:
    url: jdbc:mysql://localhost:3306/capstone_db
    username: root
    password: 1234
  jpa:
    hibernate:
      ddl-auto: update
  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 200MB
```

### 2) `application-secret.yml` 예시 (민감정보)

```yaml
spring:
  security:
    jwt:
      secretKey: your_jwt_secret_key
      access:
        expiration: 3600000
      refresh:
        expiration: 1209600000
    oauth2:
      client:
        registration:
          kakao:
            client-id: your_kakao_client_id
            client-secret: your_kakao_client_secret
          google:
            client-id: your_google_client_id
            client-secret: your_google_client_secret
  cloud:
    aws:
      credentials:
        access-key: your_access_key
        secret-key: your_secret_key
      s3:
        bucket: your_bucket_name
gcp:
  vision:
    credentials-path: src/main/resources/gcp/vision-api.json
```

> `application-secret.yml`은 Git에 커밋하지 않습니다.
> 민감정보는 로컬/배포 환경에서만 관리하세요.

---

## 📡 주요 API 엔드포인트

| 기능 | 메서드 | 경로 |
| --- | --- | --- |
| 로그인 | `POST` | `/login` |
| 회원가입 | `POST` | `/api/user/signup` |
| 내 프로필 조회 | `GET` | `/api/user/profile` |
| 특정 유저 프로필 조회 | `GET` | `/api/user/profile/{userId}` |
| 팔로우 / 언팔로우 | `POST` / `DELETE` | `/api/user/follow/{userId}` |
| 앨범 생성 | `POST` | `/api/album` |
| 앨범 목록 조회 | `GET` | `/api/album?sortStatus=&page=&pageCount=` |
| 앨범 상세 조회 | `GET` | `/api/album/{albumId}` |
| 앨범 삭제 | `DELETE` | `/api/album/{albumId}` |
| 앨범 좋아요 / 취소 | `POST` / `DELETE` | `/api/album/like/{albumId}` |
| 리뷰 생성 | `POST` | `/api/reviews` |
| 리뷰 목록 조회 | `GET` | `/api/reviews?page=&pageCount=` |
| 리뷰 상세 조회 | `GET` | `/api/reviews/{reviewId}` |
| 리뷰 삭제 | `DELETE` | `/api/reviews/{reviewId}` |
| 검색 키워드 | `GET` | `/search/keyword?keyword=` |
| 장소 상세 | `GET` | `/search/placeDetail/{placeId}` |

---

## 📦 API 응답 예시

### 공통 성공 응답 형식

```json
{
  "isSuccess": true,
  "code": "COMMON200",
  "message": "요청에 성공했습니다.",
  "result": {
    "albumId": 12,
    "createdAt": "2026-05-28T00:00:00"
  }
}
```

### 공통 실패 응답 형식

```json
{
  "isSuccess": false,
  "code": "ALBUM4005",
  "message": "존재하지 않는 앨범입니다.",
  "result": null
}
```

### 검증 실패 응답 예시

```json
{
  "isSuccess": false,
  "code": "COMMON400",
  "message": "잘못된 요청입니다.",
  "result": {
    "page": "page값은 필수입니다.",
    "sortStatus": "정렬 기준은 필수입니다."
  }
}
```

---

## 🚀 설치 및 실행

### 1) 레포지토리 클론

```bash
git clone https://github.com/Han-Geureut/Han-Geureut-Back
cd Han-Geureut-Back
```

### 2) 의존성 설치 및 빌드

```bash
./gradlew build
```

### 3) 애플리케이션 실행

```bash
./gradlew bootRun
```

Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

---

## ✅ 테스트

```bash
./gradlew test
```

---

## 🤝 협업 규칙

### 💻 Code Convention

- [NAVER Java Code Convention](https://naver.github.io/hackday-conventions-java/)

### ✉️ Commit Convention

| 태그 | 설명 |
| --- | --- |
| `[chore]` | 코드 수정, 내부 파일 수정 |
| `[feat]` | 새로운 기능 구현 |
| `[add]` | 기능 외 코드/라이브러리/파일 추가 |
| `[hotfix]` | 긴급 버그 수정 |
| `[fix]` | 버그, 오류 해결 |
| `[del]` | 불필요 코드 삭제 |
| `[docs]` | README/WIKI 문서 수정 |
| `[correct]` | 문법 오류/타입/이름 수정 |
| `[move]` | 파일/코드 이동 |
| `[rename]` | 파일명 변경 |
| `[improve]` | 성능/품질 개선 |
| `[refactor]` | 리팩터링 |
| `[test]` | 테스트 코드 추가 |

- 커밋 메시지 예시: `feat: 로그인 기능 추가`
- 작은 단위로 커밋(micro commit)을 권장합니다.

### 💡 Git Working Process

1. 이슈를 등록합니다.
2. 컨벤션에 맞춰 브랜치를 생성합니다.
3. Add - Commit - Push - Pull Request 순서로 작업합니다.
4. CI 통과 후 리뷰를 요청합니다.
5. 리뷰 반영 후 Approve를 받으면 Merge합니다.
6. 머지된 브랜치를 삭제하고 이슈/PR 상태를 정리합니다.

### 🌴 브랜치 규칙

- 브랜치 단위 = 이슈 단위 = PR 단위
- 브랜치명: `feature/#이슈번호-기능요약`
- 예시: `feature/#1-login`
