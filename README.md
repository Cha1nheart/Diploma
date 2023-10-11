# Для работы с автотестами вам необходимо установить следующее ПО
- IntelliJ IDEA 2022.3.2 (Community Edition)
- Amazon corretto OpenJDK 11.0.18
- Git(последняя версия)
- Docker Desktop(последняя версия)
- Node.js(последняя версия)

Для работы приложений необходима регистрация(кроме Node.js).

## Запуск автотестов
1. Создайте пустой проект в среде разработки IntelliJ IDEA.
2. Инициализируйте репозиторий в папке проекта с помощью Git.
3. Клонируйте репозиторий https://github.com/Cha1nheart/Diploma в ваш пустой проект.
4. Запустите Docker Desktop и скачайте официальные образы(ссылка на официальный сайт https://hub.docker.com/): MySQL, PostgreSQL и Node.js.
5. Запустите IntelliJ IDEA и откройте дипломный проект, который был клонирован в шаге № 3.
6. Откройте терминал из корневой папки проекта. Используйте команду <code>docker compose up</code> для запуска контейнеров.
7. Откройте терминал из корневой папки проекта. Запустите приложение *aqa-shop.jar* командой <code>java -jar ./aqa-shop.jar</code>.
8. Откройте терминал из папки **gate-simulator** командой <code>npm start</code>.
9. Запустите тесты из класса *TourPurchaseTest.java*.
10. Запустите тесты из класса *CreditTourPurchaseTest.java*.