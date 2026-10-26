const inputField = document.getElementById('textInput');
const resultBox = document.getElementById('result');
const exceptionResultBox = document.getElementById('exceptionResult');
const apiInputField = document.getElementById('apiInput');
const apiResultBox = document.getElementById('apiResult');
const libraryResultBox = document.getElementById('libraryResult');

const bookTitleInput = document.getElementById('bookTitleInput');
const authorNameInput = document.getElementById('authorNameInput');

function removeVowels() {
    const text = inputField.value;
    fetch(`/remove-vowels?param=${encodeURIComponent(text)}`)
        .then(response => {
            if (!response.ok) throw new Error('Ошибка 400: Введите текст!');
            return response.text();
        })
        .then(data => {
            resultBox.innerHTML = data;
            resultBox.classList.remove('error');
        })
        .catch(error => {
            resultBox.innerHTML = error.message;
            resultBox.classList.add('error');
        });
}

function countConsonants() {
    const text = inputField.value;
    fetch(`/count-consonants?param=${encodeURIComponent(text)}`)
        .then(response => {
            if (!response.ok) throw new Error('Ошибка 400: Введите текст!');
            return response.text();
        })
        .then(data => {
            resultBox.innerHTML = `Количество согласных: <b>${data}</b>`;
            resultBox.classList.remove('error');
        })
        .catch(error => {
            resultBox.innerHTML = error.message;
            resultBox.classList.add('error');
        });
}

function letterMap() {
    const text = inputField.value;
    fetch(`/letter-map?param=${encodeURIComponent(text)}`)
        .then(response => {
            if (!response.ok) throw new Error('Ошибка 400: Введите текст!');
            return response.json();
        })
        .then(data => {
            resultBox.innerHTML = JSON.stringify(data, null, 2);
            resultBox.classList.remove('error');
        })
        .catch(error => {
            resultBox.innerHTML = error.message;
            resultBox.classList.add('error');
        });
}

function handleExceptionFetch(url) {
    fetch(url)
        .then(async response => {
            const text = await response.text();
            if (!response.ok) throw new Error(`Статус ${response.status}: ${text}`);
            return text;
        })
        .then(data => {
            exceptionResultBox.innerHTML = `Успех:\n${data}`;
            exceptionResultBox.classList.remove('error');
        })
        .catch(error => {
            exceptionResultBox.innerHTML = `Поймана ошибка:\n${error.message}`;
            exceptionResultBox.classList.add('error');
        });
}

function testCheckedException() { handleExceptionFetch('/exception/checked'); }
function testUncheckedException() { handleExceptionFetch('/exception/unchecked'); }
function testLogException() { handleExceptionFetch('/exception/log?text=TestFromHtml'); }

function apiHello() {
    const name = apiInputField.value || "Гость";
    fetch(`/api/hello?name=${encodeURIComponent(name)}`)
        .then(response => response.text())
        .then(data => {
            apiResultBox.innerHTML = data;
            apiResultBox.classList.remove('error');
        })
        .catch(error => {
            apiResultBox.innerHTML = error.message;
            apiResultBox.classList.add('error');
        });
}

function apiReverse() {
    const text = apiInputField.value;
    if (!text) {
        apiResultBox.innerHTML = "Введите текст для переворота!";
        apiResultBox.classList.add('error');
        return;
    }
    fetch(`/api/reverse?text=${encodeURIComponent(text)}`)
        .then(response => response.text())
        .then(data => {
            apiResultBox.innerHTML = data;
            apiResultBox.classList.remove('error');
        })
        .catch(error => {
            apiResultBox.innerHTML = error.message;
            apiResultBox.classList.add('error');
        });
}

function addNewBook() {
    const title = bookTitleInput.value;
    const authorName = authorNameInput.value;

    if (!title || !authorName) {
        libraryResultBox.innerHTML = "Пожалуйста, заполните название книги и автора!";
        libraryResultBox.classList.add('error');
        return;
    }

    fetch(`/api/library/add?title=${encodeURIComponent(title)}&authorName=${encodeURIComponent(authorName)}`)
        .then(response => response.text())
        .then(data => {
            libraryResultBox.innerHTML = data;
            libraryResultBox.classList.remove('error');
            bookTitleInput.value = '';
            authorNameInput.value = '';
        })
        .catch(error => {
            libraryResultBox.innerHTML = "Ошибка при добавлении: " + error.message;
            libraryResultBox.classList.add('error');
        });
}

function getAllBooks() {
    fetch('/api/library/all')
        .then(response => {
            if (!response.ok) throw new Error("Ошибка при получении списка книг");
            return response.json();
        })
        .then(data => {
            libraryResultBox.innerHTML = JSON.stringify(data, null, 2);
            libraryResultBox.classList.remove('error');
        })
        .catch(error => {
            libraryResultBox.innerHTML = error.message;
            libraryResultBox.classList.add('error');
        });
}