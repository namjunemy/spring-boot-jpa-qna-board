# Spring-Boot, JPA로 만드는 QnA Board

> [javajigi(박재성)님의 SLiPP 강의](https://www.slipp.net/wiki/pages/viewpage.action?pageId=25529113)

## 반복주기 1

### 1-1. Spring Boot 로컬 개발 환경 세팅

* STS를 통해서 Spring Boot 프로젝트 생성
  * web/mustache/dev-tools dependencies
* src/main/resources/static에 index.html 추가
* chrome extension - live reload 설치
  * 크롬 익스텐션 live reload와 Spring Boot가 연동되는 이유는 프로젝트를 생성할 때, DevTools를 Dependencies를 추가 했기 때문이다.
  * live reload를 사용하려면 spring-boot-devtools Dependencies가 있어야 한다.

### 1-2. HTML 페이지 개발

* bootstrap start html 추가
* bootstrap css 라이브러리 추가
* jquery javascript 라이브러리 추가
* index.html => navigation bar 추가
* 회원가입 페이지 개발

### 1-3. github에 소스 코드 추가

* sourcetree에 저장소 추가
  * git을 통해서 관리할 코드와 관리하지 않을 코드를 분리하는 과정(ignore)
* github에 소스 코드 업로드(commit & push)

### 1-4. 원격 서버(개발 서버 또는 실 서버)에 소스 코드 배포하기 1

* AWS EC2(ubuntu 16.04)

* ssh로 서버 접속
* 각 계정별 한글 인코딩 설정
* 계정 추가 및 sudo 권한 부여

### 1-5. 원격 서버(개발 서버 또는 실 서버)에 소스 코드 배포하기 2

* java, git 설치
  * wget을 통해서 수동으로 설치 가능
* git clone 후 빌드
  * `$ ./mvnw clean package`
* 서버 시작
  * `$ java -jar [project jar file] &`

## 반복주기 2

### 학습목표

* 동적인 HTML 웹 페이지 개발
* Spring MVC의 Model, View, Controller 기반 개발

### 2-1. Controller 추가 및 mustache에 인자 전달

* pom.xml에 mustache 의존성 추가 확인
* controller -> return시 templates directory에 있는 html파일 매핑
* Mustache template engine을 이용하여 동적 처리

### 2-2. 회원가입 페이지 구현

### 2-3. 사용자 목록 페이지 구현

### 2-4. 원격 서버에 소스 코드 배포

### 2-5. 이전 상태로 원복 후 반복 구현

