### User Service
Проект представляет собой сервис для управления пользователями с использованием Spring Boot.

### Запуск приложения
Сервис запускается на порту 8080 по умолчанию.

### Доступные эндпоинты
Пользователи
GET /users - Получить список всех пользователей

GET /users/{id} - Получить пользователя по ID

POST /users - Создать нового пользователя

json
{
  "username": "string",
  "email": "string"
}
PUT /users/{id} - Обновить данные пользователя

json
{
  "username": "string",
  "email": "string"
}
DELETE /users/{id} - Удалить пользователя

### Примеры запросов
Получить всех пользователей:

bash
curl -X GET http://localhost:8080/users
Создать пользователя:

bash
curl -X POST -H "Content-Type: application/json" -d '{"username":"test","email":"test@example.com"}' http://localhost:8080/users
### Технологии
Java 17

Spring Boot 3.x

Maven

