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

### 3-3. HTML 정리, URL 정리

* clone 받은 HTML 템플릿 url 정리
* @RequestMapping을 이용하여 컨트롤러 매핑 정보 수정
* mustache 템플릿 엔진을 활용하여 HTML 파일 상단 메뉴 중복 제거(mustache include file)

### 3-4. 개인정보 수정 기능 구현 1

* 관리자가 사용자의 정보를 수정할 수 있도록 개인정보 수정 기능 구현
* /users/{id}/form 컨트롤러 메소드 추가, 사용자 별 updateForm 페이지 제공, findOne(id)를 이용하여 데이터 조회
* @PathVariable 을 통해서 request의 id 사용

### 3-5. 개인정보 수정 기능 구현 2

* 실제로 updateForm에서 수정 버튼을 눌렀을 때, Update 동작을 하기 위한 update 메소드 구현, 컨트롤러 매핑
* User 클래스(VO) update 메소드 추가, userRepository.save(user)를 이용하여 업데이트 수행
* `<input type="hidden" name="_method" value="put" /> ` 태그를 이용하여 HTML에서는 post 요청으로 전달하되 서버측에서 PUT 메소드로 처리할 수 있도록 수정(@PostMapping => @PutMapping)

### 3-6. 원격 서버에 소스 코드 배포

* 원격 서버에서 jar 파일 생성과정 없이 spring-boot application 구동하기
  * 개발 환경에서 문제가 없었던 프로젝트가 배포하는 과정에서 문제(jar로 만드는 과정에서 mustache의 partial 기능 오류 문제)가 생겼을 경우 jar 생성 없이 구동
* `$ ./mvmw spring-boot:run &`

## 반복주기 4

### 학습목표

* 로그인 기능 구현을 통한 쿠키와 세션에 대한 대략적인 이해
* 로그인 사용자에 대한 접근 제한

### 4-1. 로그인 기능 구현

* loginForm, login 컨트롤러 매핑 추가
* login.html 페이지 작성
* User 클래에스에서 PK가 Long 타입의 ID로 되어있어서 기본적으로 findOne() 메소드는 PK로 조회 가능하다.
* userId를 기반으로 조회하고 싶다면, Repository 인터페이스에 다음 메소드를 추가 한다.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUserId(String userId);
}
```

* 로그인에 성공하면, HttpSession에 로그인 정보를 저장한다.

```java
session.setAttribute("sessionUser", user);
```

### 4-2. 로그인 상태에 따른 메뉴 처리 및 로그아웃

* 로그인 상태에 따라서 메뉴 처리를 하기위해 서버가 재시작 될 때마다 회원 가입을 해야 했던 로직 자동화

  * spring data jpa initialize database
  * 프로젝트 루트(/src/main/resources)에 import.sql 파일을 생성하고 관리한다.
  * sql 파일에서 생성한 유저는 프로그램이 재시작해도 유지되는 초기 데이터다.

* mustache 템플릿 엔진에서 session 정보 다루기

  * spring-boot에서는 session을 mustache 템플릿 엔진에 전달해 주는 설정 값이 default false로 설정되어 있다.
  * 따라서, application.properties의 설정 파일에 아래의 설정을 true로 변경하면,
  * mustache에서 HttpSession에 저장된 attribute 정보를 사용할 수 있다.

  ```java
  spring.mustache.expose-session-attributes=true
  ```

  ```html
  {{^sessionUser}}
  <li><a href="/users/loginForm" role="button">로그인</a></li>
  <li><a href="/users/form" role="button">회원가입</a></li>
  {{/sessionUser}}
  {{#sessionUser}}
  <li><a href="/users/logout" role="button">로그아웃</a></li>
  <li><a href="/users/{{id}}/form" role="button">개인정보수정</a></li>
  {{/sessionUser}}
  ```

* 로그아웃 기능 구현

  * 로그아웃과 동시에 세션 데이터를 삭제해야 한다.

  ```java
  session.removeAttribute("sessionUser");
  ```

### 4-3. 로그인 사용자에 한해 자신의 정보를 수정하도록 수정

* session에 저장된 사용자에 한해 자신의 정보를 조회하고 수정하도록 방어 코드 작성 후 Exception 처리

### 4-4. 중복 제거, clean code, 쿼리 보기 설정

* HttpSessionUtils클래스를 만들어서 하드 코딩 되어있는 "sessionUser"를 상수화
* 로그인 유저을 판단하는 isLoginUser() 메소드와 로그인 유저일 경우 유저의 정보를 가져오는 getUserFormSession() 메소드 추가하여 중복 제거
* User 클래스에서 값을 꺼내와서 컨트롤러에서 판단하기 보다는, 클래스의 메소드를 생성해서 메시지를 전달(함수 호출)하여 판단된 결과를 얻어서 사용, 그리고 User 클랫그의 getPassword() 메소드 제거
* 마찬가지로 sessionUser를 판단하기 위해서 id값을 getId()를 통해서 컨트롤러로 거져와서 판단하는 로직을 User클래스의 matchId(id) 메소드를 만들어서 메세지를 전달(함수 호출)하고, 반환 값을 사용
* jpa 설정 값들을 통해서 실행되는 jpa 쿼리문을 확인할 수 있다.

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### 4-5. 질문하기, 질문목록 기능 구현

* questionController 생성, 로그인한 사용자만 질문 가능 하도록 기능 구현
* 질문정보를 담을 Question 클래스 생성, 애노테이션을 활용하여 DB 테이블 추가 확인
* 데이터베이스와의 작업을 담당할 QuestionRepository 인터페이스 생성 

```java
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
```

* 질문목록 기능 구현(HomeController로 접근시 index.html)

### 4-6. 원격 서버에 소스 코드 배포

* 반복주기 3에서 jar파일로 배포하는 과정에서 문제가 생겼다.
* war파일로 배포하는 방법이 존재한다.
* 모바일 애플리케이션과 같은 경우는 웹 자원을 관리하지 않고, 데이터들만 웹 서버단에서 제공하므로 jar 파일로 배포가 가능하다. 하지만, 웹 애플리케이션은 이미지와 HTML과 같은 자원들이 포함되어 있으므로 war로 배포해야 한다.
* war파일로 배포할 땐, tomcat server가 필요하다. 현재 Spring-Boot 프로젝트에는 tomcat이 내장되어 있지만, war로 packaging 하면 문제가 될 수 있다.
* 따라서, 별도의 tomcat server를 설치하고 해당 서버에 war파일을 배포한다.
* Spring Boot를 tomcat에 배포하려면 몇가지 Maven 설정이 필요하다.
  * packaging을 war로
  * spring-boot-starter-tomcat 의존성을 추가하고, provided scope 속성을 추가하면 내장된 tomcat을 사용하지 않고 별도의 tomcat을 사용한다.

```xml
...
	<packaging>war</packaging>
...

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
	</dependencies>
...
```

- **주의할 점**

  - 이렇게 내장 tomcat을 사용하지 않으면, 추가적인 애플리케이션 코드가 필요하다. 그래야만 외부에 tomcat서버가 구동되면서 spring boot 애플리케이션을 초기화 하는 것이 가능하다.
  - 내장 tomcat을 사용할 경우 현재 아래 기본생성된 클래스가 초기화를 담당하고 있다.

  ```java
  package io.namjune;
  
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  public class QnaBoardApplication {
  
  	public static void main(String[] args) {
  		SpringApplication.run(QnaBoardApplication.class, args);
  	}
  }
  ```

  * 아래의 클래스를 추가한다.
    * SpringBootServletInitializer를 상속받아서 configure 메소드를 통해서 위의 기본 생성된 초기화 담당 클래스를 builder에 등록한다.

  ```java
  package io.namjune;
  
  import org.springframework.boot.builder.SpringApplicationBuilder;
  import org.springframework.boot.web.support.SpringBootServletInitializer;
  
  public class WebInitializer extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(QnaBoardApplication.class);
    }
  }
  ```

  

