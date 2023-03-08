## 프로젝트 개요 & 목적
스프링 부트를 기반으로 웹 게시판 서비스 전반을 개발하는 개인 프로젝트. <br/>

에자일 프로세스를 간접적으로 경험해보기 위하여 Github에서 제공하는 Projects 기능과 Issues 기능을 활용하였고,
Kanban Board를 구성하여 프로젝트 진행 과정 전반을 관리하는 과정 또한 거쳐본다.
<br/>

프로젝트는 구현 및 테스트, 권한 인증 및 보안, 배포 자동화 흐름으로 진행된다. <br/>
또한 Github Flow 방법론에 따라 main, feature 두 브랜치만을 구성하여 개발을 진행해나가며, 
생성된 이슈 번호를 커밋 메세지 헤더로 지정하는 관례를 따른다.
<br/>

마지막으로, 완성된 Java 프로젝트를 Kotlin으로 마이그레이션 해보는 과정을 거친다.

---
## Tech Stack & Version
- Framework: Spring Boot 3.0.3
- Language: Java 17, Kotlin
- Test: JUnit5, MockMvc
- DB/관련기술: MySQL, PostgreSQL, QueryDSL
- API: Rest Repositories + HAL Explorer, Swagger 3.0
- 보안/인증: Spring Security
- 생산성: Lombok, Spring Boot Actuator/DevTools
- 배포: Heroku
- 기타 도구: Vim, Postman, GitKraken, Thymeleaf, Bootstrap 5.2

---

## Issues & Solve
### Spring Boot 3.0 QueryDSL 적용
- Build: Gradle -> Intellij IDEA
- build.gradle
```yaml
dependencies {
  // ...
  // QueryDSL 의존성 추가
  implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
  implementation 'com.querydsl:querydsl-core'
  implementation 'com.querydsl:querydsl-collections'
  annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
  annotationProcessor 'jakarta.persistence:jakarta.persistence-api'
  annotationProcessor 'jakarta.annotation:jakarta.annotation-api'
}

// QClass 경로 지정
def generated = 'src/main/generated'

tasks.withType(JavaCompile) {
  options.getGeneratedSourceOutputDirectory().set(file(generated))
}

sourceSets {
  main.java.srcDirs += [ generated ]
}

clean {
  delete file(generated)
}
```