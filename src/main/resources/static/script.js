const inputField = document.getElementById('textInput');
const resultBox = document.getElementById('result');
const exceptionResultBox = document.getElementById('exceptionResult');
const apiInputField = document.getElementById('apiInput');
const apiResultBox = document.getElementById('apiResult');
const libraryResultBox = document.getElementById('libraryResult');

const bookTitleInput = document.getElementById('bookTitleInput');
const authorNameInput = document.getElementById('authorNameInput');
const bookPagesInput = document.getElementById('bookPagesInput');
const searchInput = document.getElementById('searchInput');
const issueUserInput = document.getElementById('issueUserInput');
const issueBookInput = document.getElementById('issueBookInput');
const deleteBookId = document.getElementById('deleteBookId');

const pagesInput = document.getElementById('pagesInput');
const authorSurnameInput = document.getElementById('authorSurnameInput');
const searchResultBox = document.getElementById('searchResult');

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

function addNewBookPost() {
    const title = bookTitleInput.value;
    const authorName = authorNameInput.value;
    const pages = bookPagesInput.value;

    if (!title || !authorName) {
        libraryResultBox.innerHTML = "Пожалуйста, заполните название книги и автора!";
        libraryResultBox.classList.add('error');
        return;
    }

    const payload = {
        title: title,
        pages: pages ? parseInt(pages) : 0,
        authors: [ { name: authorName } ]
    };

    fetch('/api/library/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(async response => {
        const text = await response.text();
        if (!response.ok) {
            throw new Error(text || "Ошибка сервера");
        }
        return text;
    })
    .then(data => {
        libraryResultBox.innerHTML = data;
        libraryResultBox.classList.remove('error');
        bookTitleInput.value = '';
        authorNameInput.value = '';
        bookPagesInput.value = '';
    })
    .catch(error => {
        libraryResultBox.innerHTML = "Ошибка: " + error.message;
        libraryResultBox.classList.add('error');
    });
}

function searchBooks() {
    const query = searchInput.value;
    if (!query) {
        libraryResultBox.innerHTML = "Введите запрос для поиска!";
        libraryResultBox.classList.add('error');
        return;
    }

    fetch(`/api/library/search?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            libraryResultBox.innerHTML = JSON.stringify(data, null, 2);
            libraryResultBox.classList.remove('error');
        })
        .catch(error => {
            libraryResultBox.innerHTML = "Ошибка поиска: " + error.message;
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

function issueBook() {
    const userName = issueUserInput.value;
    const bookTitle = issueBookInput.value;

    if (!userName || !bookTitle) {
        libraryResultBox.innerHTML = "Введите имя читателя и название книги!";
        libraryResultBox.classList.add('error');
        return;
    }

    fetch(`/api/library/issue?userName=${encodeURIComponent(userName)}&bookTitle=${encodeURIComponent(bookTitle)}`)
        .then(response => response.text())
        .then(data => {
            libraryResultBox.innerHTML = data;
            libraryResultBox.classList.remove('error');
            issueUserInput.value = '';
            issueBookInput.value = '';
        })
        .catch(error => {
            libraryResultBox.innerHTML = "Ошибка выдачи: " + error.message;
            libraryResultBox.classList.add('error');
        });
}

function deleteBook() {
    const id = deleteBookId.value;
    if (!id) {
        libraryResultBox.innerHTML = "Введите ID книги!";
        libraryResultBox.classList.add('error');
        return;
    }

    fetch(`/api/library/delete/${id}`, {
        method: 'DELETE'
    })
    .then(async response => {
        const text = await response.text();
        if (!response.ok) {
            try {
                const err = JSON.parse(text);
                throw new Error(err.error);
            } catch (e) {
                throw new Error(text);
            }
        }
        return text;
    })
    .then(data => {
        libraryResultBox.innerHTML = data;
        libraryResultBox.classList.remove('error');
        deleteBookId.value = '';
    })
    .catch(error => {
        libraryResultBox.innerHTML = "Ошибка удаления: " + error.message;
        libraryResultBox.classList.add('error');
    });
}

function getBooksOver100Pages() {
    fetch('/crud/books/more-than-100-pages')
        .then(response => response.json())
        .then(data => {
            searchResultBox.innerHTML = JSON.stringify(data, null, 2);
            searchResultBox.classList.remove('error');
        })
        .catch(error => {
            searchResultBox.innerHTML = "Ошибка: " + error.message;
            searchResultBox.classList.add('error');
        });
}

function getBooksOverXPages() {
    const x = pagesInput.value;
    if (!x) {
        searchResultBox.innerHTML = "Введите количество страниц!";
        searchResultBox.classList.add('error');
        return;
    }

    fetch(`/crud/books/more-than-pages?x=${x}`)
        .then(response => response.json())
        .then(data => {
            searchResultBox.innerHTML = JSON.stringify(data, null, 2);
            searchResultBox.classList.remove('error');
        })
        .catch(error => {
            searchResultBox.innerHTML = "Ошибка: " + error.message;
            searchResultBox.classList.add('error');
        });
}

function getBooksByAuthor() {
    const surname = authorSurnameInput.value;
    if (!surname) {
        searchResultBox.innerHTML = "Введите фамилию автора!";
        searchResultBox.classList.add('error');
        return;
    }

    fetch(`/crud/books/by-author?surname=${encodeURIComponent(surname)}`)
        .then(response => response.json())
        .then(data => {
            searchResultBox.innerHTML = JSON.stringify(data, null, 2);
            searchResultBox.classList.remove('error');
        })
        .catch(error => {
            searchResultBox.innerHTML = "Ошибка: " + error.message;
            searchResultBox.classList.add('error');
        });
}