Описание проекта
Проект по созданию и хранению записей студентов и факультетов

Сборка и запуск проекта
1 . Склонируйте проект:

git clone git@github.com:BuyanovMax/School.git
2 . Разверните Docker контейнер:

docker run --name postgres -e POSTGRES_PASSWORD=[password] -p 5433:5432 -d postgres
Стек Технологий:
Язык и Окружение

Java 17
Maven
Spting Boot
Spring Web
Spring Data JPA
Rest
Git
Swagger
Lombok
Liquibase
База данных

PostgreSQL
Тестирование

JUnit
Mockito
Прочее

Docker
