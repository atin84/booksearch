# Procjet : Search Web
- 

## 오픈 소스 사용 목적
### spring
Spring Boot를 통해서 기본 Spring 설정을 자동화하여 사용한다.
DB는 spring data를 활용하고, 인증 부분은 security를 사용한다.
화면 UI는 thymeleaft를 사용한다.
- spring-boot-starter-data-redis
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-thymeleaf
- spring-boot-starter-tomcat
- spring-boot-starter-web
- spring-boot-starter-aop
- spring-boot-starter-actuator

### hystrix
장애에 대응하기 위한 서킷 브레이커로 Hystrix를 사용한다.
- spring-cloud-starter-netflix-hystrix', version: "${version.springcloud}"
- spring-cloud-starter-netflix-hystrix-dashboard', version: "${version.springcloud}"

### common
공통 Util 사용 목적
- commons-io
- commons-lang3

### retrofit api
API 호출 사용 목적
- retrofit
- converter-jackson

### jackson
JSON 사용 위함
- jackson-datatype-jsr310

### model mapper
객체 매핑시 사용 목적
- modelmapper

### webjars
UI에서 사용하는 자바스크립트 라이브러리
- jquery
- bootstrap
- datatables

### DB and nosql
데이터 저장은 내장용 형태로 하며, RDBMS는 h2에, redis는 embedded-redis를 사용한다.
- h2
- embedded-redis

### test
단위 테스트 사용 목적
- spring-boot-starter-test
- junit

### dev tool
개발 편의를 위한 lombok, 개발시 바로 적용을 위한 devtools
- lombok
- spring-boot-devtools