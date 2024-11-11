## Запуск проекта
Выполнить в терминале следующие команды:
1) `./gradlew bootJar`  
2) `docker-compose up` 
### Доступные эндпоинты описаны в документации swagger:
http://localhost:8080/swagger-ui/index.html

#### При запуске через среду разработки добавить переменные среды: 
`DB_HOST=localhost;DB_PORT=5432;DB_NAME=propvue;POSTGRES_USER=user;POSTGRES_password=password`

_P.S. Чувствительные данные умышленно добавлены в репозиторий_