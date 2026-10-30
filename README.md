# Автоматизация тестирования веб-сервиса «Интернет Банк»

[![Java CI with Gradle, Selenide and MySQL](https://github.com/Ilich8603/javaAQA-SQL_deadline/actions/workflows/gradle.yaml/badge.svg)](https://github.com/Ilich8603/javaAQA-SQL_deadline/actions/workflows/gradle.yaml)


##  Технологический стек
* **Язык программирования:** Java 11
* **Сборщик:** Gradle
* **Фреймворк для UI-тестов:** [Selenide](https://selenide.org/)
* **Тестовый фреймворк:** JUnit 5
* **Работа с БД:** Apache DBUtils, MySQL Connector/J
* **Генерация тестовых данных:** Java Faker
* **Контейнеризация:** Docker, Docker Compose (MySQL 8)
* **Паттерн проектирования:** Page Object Model (POM)

## Инструкция по локальному запуску

### 1. Запуск базы данных
В корне проекта выполните команду:
```bash
    docker-compose up -d
```
Дождитесь полной инициализации MySQL (около 15–30 секунд), чтобы скрипт schema.sql успешно применился и таблицы создались.


### 2. Запуск SUT (System Under Test)
   Запустите тестируемое приложение, передав ему параметры подключения к БД через флаги командной строки:

```bash
      java -jar artifacts/app-deadline.jar \
      -P:jdbc.url=jdbc:mysql://localhost:3306/app \
      -P:jdbc.user=app \
      -P:jdbc.password=pass
 ```
### 3. Запуск автотестов
   Когда SUT и БД запущены, выполните в терминале:

```bash
    ./gradlew test --info
```
