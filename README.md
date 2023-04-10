## 프로젝트 개요 & 과정
스프링 부트를 기반으로 웹 게시판 서비스 전반을 개발하는 개인 프로젝트. <br/>
프로젝트는 구현 및 테스트, 권한 인증 및 보안, 배포 자동화 흐름으로 진행된다. <br/>
마지막으로, 완성된 Java 프로젝트를 Kotlin으로 마이그레이션 해보는 과정을 거친다.

---
## Tech Stack & Version
- Framework: Spring Boot 3.0.3
- Language: Java 17, Kotlin
- Test: JUnit5 (BDDMockito)
- DB/관련기술: MySQL, PostgreSQL, QueryDSL
- API: Rest Repositories + HAL Explorer, Swagger 3.0
- 보안/인증: Spring Security (JWT Authentication)
- 생산성: Lombok, Spring Boot Actuator/DevTools
- 배포: AWS
- 기타 도구: Vim, GitKraken, Thymeleaf, Bootstrap 5.2

---

## ERD
![forum-erd](https://user-images.githubusercontent.com/95991654/224528500-f01aa2b4-d204-4643-af02-8d275aeae4d7.svg)

<br/>

---

## Issues & Solve
### 1. Spring Boot 3.0 QueryDSL 적용
- Build: Gradle -> Intellij IDEA
- build.gradle

```groovy
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

<br/>

- kotlin 설정 (build.gradle.kts)
```kotlin
plugins {
    kotlin("kapt") version "1.7.10"
    idea
}

dependencies {
    // QueryDSL 의존성 추가
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("com.infobip:infobip-spring-data-jpa-querydsl-boot-starter:8.1.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
}

// 컴파일 인자 추가 (JvmDefault)
tasks.withType<KotlinCompile> {
    kotlinOptions {
        // freeCompilerArgs = listOf("-Xjsr305=strict")
        freeCompilerArgs = listOf("-Xjsr305=strict","-Xjvm-default=enable")
        jvmTarget = "17"
    }
}

// QClass 경로 지정
idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}
```

<br/>

- Repository (customize 오버라이딩 시 JPA 메서드로 인식되는 문제때문에 @JvmDefault 추가)
```kotlin
@RepositoryRestResource
interface ArticleRepository :
    JpaRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>,
    QuerydslBinderCustomizer<QArticle>
{
    @JvmDefault
    override fun customize(bindings: QuerydslBindings, root: QArticle): Unit {
        bindings.excludeUnlistedProperties(true)
        bindings.including(root.title, root.content, root.hashtag, root.createdBy)

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase)
    }
}
```

<br/>

### 2. Spring Security 개편
기존 WebSecurityConfigurerAdapter의 configure 메소드를 오버라이딩하는 방식에서 
SecurityFilterChain을 Bean으로 등록하는 방식으로 변경 <br/>
https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .formLogin()
            .and()
            .build();
}
```
