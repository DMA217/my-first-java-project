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