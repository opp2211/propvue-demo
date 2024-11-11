# Тестовое задание от PropVue LLC
Задание выполнено на стеке: Java 17, Spring (Boot, Web, Data), Gradle(Kotlin), 
Postgres, Liquibase, JUnit 5, Lombok, Mapstruct.

Приложение, а также БД для него помещены в докер, запуск осуществляется через docker-compose. Слой данных реализован через Spring Data JPA, один компонент через Criteria API. 

## Запуск проекта
Выполнить в терминале следующие команды:
1) `./gradlew bootJar`  
2) `docker-compose up` 
### Доступные эндпоинты описаны в документации swagger:
http://localhost:8080/swagger-ui/index.html

#### При запуске через среду разработки добавить переменные среды: 
`DB_HOST=localhost;DB_PORT=5432;DB_NAME=propvue;POSTGRES_USER=user;POSTGRES_password=password`

_P.S. Чувствительные данные умышленно добавлены в репозиторий_