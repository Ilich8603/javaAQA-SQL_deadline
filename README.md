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
Дождитесь полной инициализации MySQL (около 15–30 секунд), чтобы скрипт `schema.sql` успешно применился и таблицы создались.


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
## CI/CD (GitHub Actions)
В проекте настроен пайплайн GitHub Actions (`.github/workflows/gradle.yml`). При каждом пуше в ветку `main` (или при создании Pull Request) автоматически выполняются следующие шаги:

Разворачивается контейнер с MySQL 8 через `hoverkraft-tech/compose-action`.

Ожидается инициализация БД (команда `sleep 30`).

Запускается SUT в фоновом режиме (`nohup java -jar ... &`).

Запускаются автотесты Gradle.

Генерируется и сохраняется HTML-отчет о тестировании (доступен в артефактах сборки).

## Известные баги (Known Issues)
В ходе тестирования был выявлен дефект в логике работы SUT. Баг-репорт находится в разделе Issues.

**[Блокировка пользователя](https://github.com/Ilich8603/javaAQA-SQL_deadline/issues/1)** 

Отсутствует блокировка пользователя после 3-х неверных попыток ввода пароля. Статус в БД не меняется на blocked, UI не выводит сообщение о блокировке.
