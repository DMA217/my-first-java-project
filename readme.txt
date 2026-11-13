Это мой первый файл для Git
Я внес изменения
Ещё изменения
1. Удаление гласных (и пробелов)
URL: http://localhost:8080/remove-vowels?param=ВАШ_ТЕКСТ

Метод: GET

Что делает: Убирает все английские/русские гласные и пробелы.

Примеры:

http://localhost:8080/remove-vowels?param=HelloWorld → Ответ: HllWrld

http://localhost:8080/remove-vowels?param=Привет мир → Ответ: Првтмр

http://localhost:8080/remove-vowels?param=Hello World → Ответ: HllWrld (пробел удален)

2. Подсчет согласных
URL: http://localhost:8080/count-consonants?param=ВАШ_ТЕКСТ

Метод: GET

Что делает: Считает количество согласных букв (цифры и знаки препинания игнорируются).

Примеры:

http://localhost:8080/count-consonants?param=HelloWorld → Ответ: 7 (H, l, l, W, r, l, d)

http://localhost:8080/count-consonants?param=12345 → Ответ: 0 (нет букв)

http://localhost:8080/count-consonants?param=Мама → Ответ: 2 (М, м)

3. Карта букв (JSON)
URL: http://localhost:8080/letter-map?param=ВАШ_ТЕКСТ

Метод: GET

Что делает: Возвращает JSON-объект, где ключ — это буква, а значение — сколько раз она встречается в тексте.

Примеры:

http://localhost:8080/letter-map?param=Hello → Ответ: {"h":1,"e":1,"l":2,"o":1}

http://localhost:8080/letter-map?param=АБВ → Ответ: {"а":1,"б":1,"в":1}

4. Проверка на ошибку (HTTP 400 Bad Request)
Во всех трех методах мы добавили проверку на пустую строку. Если вы отправите запрос без текста (или только с пробелами), сервер вернет ошибку.

URL: http://localhost:8080/remove-vowels?param= (пусто) или ?param= (пробелы)

Ответ: Текст Ошибка: параметр 'param' не может быть пустым. и статус-код 400 (в браузере просто увидите текст, а в Postman — красную цифру 400).

Зайти в папку cd ~/IdeaProjects/untitled1/frontend
Запустить сервер npm run dev
Выйти cd ~

Чтобы запустить проект с нуля, убедитесь, что Docker Desktop запущен.

Откройте терминал, перейдите в папку проекта командой cd ~/IdeaProjects/untitled1 и выполните docker compose up -d, что запустит все контейнеры в фоновом режиме (подождите 20–30 секунд). Остановить проект можно командой docker compose down.

Для использования интерфейса откройте в браузере адрес http://localhost (без порта 8080). Вы увидите Vue-приложение с вкладками: «Анализатор» (работает без пароля), «Исключения» (демонстрация try-catch), «Библиотека» (добавление и удаление книг, но при добавлении появится ошибка 401, так как Vue не передает логин и пароль — это нормально для текущей версии), «Поиск» (фильтрация книг) и «Чат» (отправка сообщений).

Для тестирования защищенных методов API используйте Swagger по адресу http://localhost:8080/swagger-ui/index.html. Если появилась кнопка Authorize, нажмите её и введите admin / password, затем выберите метод, нажмите Try it out, заполните JSON и Execute.

Альтернативно можно использовать curl в терминале, например: curl -X POST http://localhost:8080/api/library/add -u admin:password -H "Content-Type: application/json" -d '{"title":"Новая книга","pages":200,"authors":[{"name":"Автор"}]}'.

Чтобы посмотреть базу данных, откройте DBeaver, подключитесь к базе java_learning (она работает в Docker, но доступна на порту 5432) и увидите таблицы books, authors, user_book с добавленными данными.

Если что-то не работает, смотрите логи командой docker compose logs -f app (выход по Ctrl+C). Если вы изменили Java или Vue код, остановите контейнеры (docker compose down), пересоберите и запустите заново (docker compose up --build -d), подождав 2–3 минуты