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

### 2-2. 회원가입 기능 구현

* GET/POST
* User 클래스와 setter를 통한 자동 매핑

### 2-3. 사용자 목록 페이지 구현

* List 컬렉션과 mustache 반복문을 이용해서 사용자 목록 출력

### 2-4. 원격 서버에 소스 코드 배포

- `$ git pull`
- `$ ./mvnw clean package`
- `$ java -jar [project jar file] &`

### 2-5. 이전 상태로 원복 후 반복 구현

* sourcetree -> git reset 이용하여 이전 상태로 원복 가능

## 반복주기 3

### 학습목표

- 데이터베이스에 사용자 데이터 추가
- 개인정보 수정 기능 구현
- 질문하기, 질문목록 기능 구현

### 3-1. QnA HTML 템플릿, H2 데이터베이스 설치, 설정, 관리툴 확인

* SLiPP의 Q&A HTML 템플릿을 clone 받아서 사용
* mvn repo에서 H2 db 의존성 추가
* 관리 tool
  * `http://[ip]:8080/h2-console` 로 접근
  * 기본적으로 JDBC url은 `jdbc:j2:~/[해당 프로젝트명]`

### 3-2. 자바 객체와 테이블 매핑, 회원가입 기능 구현

* spring-boot-starter-data-jpa 의존성 추가

* JPA에서 제공하는 annotation(참고 : http://jojoldu.tistory.com/251?category=635883)

  * **@Entity**

    * 테이블과 연결될 클래스임을 나타낸다.
    * 언더스코어 네이밍으로 이름을 매칭한다.
    * ex) SalesManager.java => sales_manager 테이블

  * **@Id**

    * 해당 테이블의 PK를 나타낸다.

  * **@GeneratedValue**

    * PK의 생성 규칙을 나타낸다.
    * 기본값은 AUTO로 MYSQL의 auto_increment와 같이 자동증가하는 정수형 값이 된다.
    * Spring Boot 2.0에서는 옵션을 추가 해야만 제대로 동작한다.
      * http://jojoldu.tistory.com/295

  * **@Column**

    * 테이블의 컬럼을 나타내면, 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 컬럼이 된다.
    * 사용하는 이유는, 기본값 외에 추가로 변경이 필요한 옵션이 있을경우 사용 한다.
    * 문자열의 경우 VARCHAR(255)가 기본값인데, 사이즈를 500으로 늘리고 싶거나, 타입을 TEXT로 변경하고 싶거나, 해당 필드의 nullable 설정을 false로 바꾸거나 등의 경우에 사용 한다.

  * **정말 유용한 팁**

    ```text
    웬만하면 Entity의 PK는 Long 타입의 Auto_increment를 추천합니다. 
    (MySQL 기준으로 이렇게 하면 bigint 타입이 됩니다.) 
    주민등록번호와 같은 비지니스상 유니크키나, 여러키를 조합한 복합키로 PK를 잡을 경우 난감한 상황이 종종 발생합니다. 
    (1) FK를 맺을때 다른 테이블에서 복합키 전부를 갖고 있거나, 중간 테이블을 하나더 둬야하는 상황이 발생합니다. 
    (2) 인덱스에 좋은 영향을 끼치지 못합니다. 
    (3) 유니크한 조건이 변경될 경우 PK 전체를 수정해야하는 일이 발생합니다. 
    주민등록번호, 복합키 등은 유니크키로 별도로 추가하시는것을 추천드립니다.
    ```

* application.properties파일에서 spring boot - h2 db connection url 설정

```properties
spring.datasource.url=jdbc:h2:~/qna-board;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

* UserRepository 인터페이스 구현

```java
package io.namjune.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
```

* UserController에서 Collection으로 처리하던 작업 JPA 사용으로 변경
  * 회원 가입, 회원 목록 기능에서 JPA로 데이터 처리

```java
...
	@Autowired
  private UserRepository userRepository;
...
  userRepository.save(user);
...
  userRepository.findAll()
```

### 3-3. 개인정보 수정 기능 구현

### 3-4. 질문하기, 질문목록 기능 구현

### 3-5. 원격 서버에 소스 코드 배포

