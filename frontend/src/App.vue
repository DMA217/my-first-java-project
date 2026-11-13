<script setup>
import { ref, onMounted } from 'vue'

const activeTab = ref('text')

const text = ref('')
const textResult = ref('')
const textError = ref(false)

const exceptionResult = ref('')
const exceptionError = ref(false)

const title = ref('')
const authorName = ref('')
const pages = ref('')
const books = ref([])
const searchQuery = ref('')

const searchX = ref('')
const searchAuthor = ref('')
const filterResult = ref('')

const chatIp = ref('localhost')
const chatName = ref('Алиса')
const chatText = ref('')
const chatLog = ref([])

const toasts = ref([])

const showToast = (message, type = 'success') => {
  const id = Date.now()
  toasts.value.push({ id, message, type })
  setTimeout(() => {
    toasts.value = toasts.value.filter(t => t.id !== id)
  }, 3000)
}

const fetchBooks = async () => {
  try {
    const response = await fetch('/api/library/all')
    if (!response.ok) throw new Error('Ошибка загрузки')
    books.value = await response.json()
  } catch (error) {
    showToast('Не удалось загрузить список книг', 'error')
  }
}

const removeVowels = async () => {
  if (!text.value) return
  try {
    const res = await fetch(`/remove-vowels?param=${encodeURIComponent(text.value)}`)
    if (!res.ok) throw new Error('Введите текст')
    textResult.value = await res.text()
    textError.value = false
  } catch (e) {
    textResult.value = e.message
    textError.value = true
  }
}

const countConsonants = async () => {
  if (!text.value) return
  try {
    const res = await fetch(`/count-consonants?param=${encodeURIComponent(text.value)}`)
    if (!res.ok) throw new Error('Введите текст')
    textResult.value = `Согласных: ${await res.text()}`
    textError.value = false
  } catch (e) {
    textResult.value = e.message
    textError.value = true
  }
}

const letterMap = async () => {
  if (!text.value) return
  try {
    const res = await fetch(`/letter-map?param=${encodeURIComponent(text.value)}`)
    if (!res.ok) throw new Error('Введите текст')
    textResult.value = JSON.stringify(await res.json(), null, 2)
    textError.value = false
  } catch (e) {
    textResult.value = e.message
    textError.value = true
  }
}

const triggerException = async (type) => {
  exceptionError.value = false
  exceptionResult.value = 'Загрузка...'
  try {
    let url = `/exception/${type}`
    if (type === 'log') url += '?text=TestFromVue'
    const res = await fetch(url)
    const textData = await res.text()
    if (!res.ok) throw new Error(`Статус ${res.status}: ${textData}`)
    exceptionResult.value = textData
  } catch (e) {
    exceptionResult.value = e.message
    exceptionError.value = true
  }
}

const addBook = async () => {
  if (!title.value || !authorName.value) {
    showToast('Заполните название и автора', 'error')
    return
  }
  const payload = {
    title: title.value,
    pages: pages.value ? parseInt(pages.value) : 0,
    authors: [{ name: authorName.value }]
  }
  try {
    const res = await fetch('/api/library/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })
    if (!res.ok) throw new Error(await res.text())

    showToast('Книга успешно добавлена!', 'success')
    title.value = ''; authorName.value = ''; pages.value = ''
    await fetchBooks()
  } catch (e) {
    showToast(e.message, 'error')
  }
}

const deleteBook = async (id) => {
  if (!confirm('Вы уверены, что хотите удалить книгу?')) return
  try {
    const res = await fetch(`/api/library/delete/${id}`, { method: 'DELETE' })
    if (!res.ok) throw new Error(await res.text())
    showToast('Книга удалена', 'success')
    await fetchBooks()
  } catch (e) {
    showToast(e.message, 'error')
  }
}

const getBooksByAuthor = async () => {
  if (!searchAuthor.value) return
  try {
    const res = await fetch(`/crud/books/by-author?surname=${encodeURIComponent(searchAuthor.value)}`)
    filterResult.value = JSON.stringify(await res.json(), null, 2)
  } catch (e) {
    filterResult.value = 'Ошибка: ' + e.message
  }
}

const getBooksOverXPages = async () => {
  if (!searchX.value) return
  try {
    const res = await fetch(`/crud/books/more-than-pages?x=${searchX.value}`)
    filterResult.value = JSON.stringify(await res.json(), null, 2)
  } catch (e) {
    filterResult.value = 'Ошибка: ' + e.message
  }
}

const sendMessage = async () => {
  if (!chatText.value) return
  try {
    const res = await fetch(`/api/chat/send?ip=${chatIp.value}&senderName=${encodeURIComponent(chatName.value)}&text=${encodeURIComponent(chatText.value)}`)
    const result = await res.text()
    showToast(result, 'success')
    chatText.value = ''
  } catch (e) {
    showToast('Ошибка отправки: ' + e.message, 'error')
  }
}

const loadMessages = async () => {
  try {
    const res = await fetch('/api/chat/all')
    chatLog.value = await res.json()
  } catch (e) {
    showToast('Ошибка загрузки сообщений', 'error')
  }
}

onMounted(() => {
  fetchBooks()
  loadMessages()
})
</script>

<template>
  <div class="page-wrapper">
    <div class="main-container">

      <header class="main-header">
        <h1>Fullstack Приложение</h1>
        <div class="tabs">
          <button :class="{ active: activeTab === 'text' }" @click="activeTab = 'text'">Анализатор</button>
          <button :class="{ active: activeTab === 'exception' }" @click="activeTab = 'exception'">Исключения</button>
          <button :class="{ active: activeTab === 'library' }" @click="activeTab = 'library'">Библиотека</button>
          <button :class="{ active: activeTab === 'search' }" @click="activeTab = 'search'">Поиск</button>
          <button :class="{ active: activeTab === 'chat' }" @click="activeTab = 'chat'">Чат</button>
        </div>
      </header>

      <div class="content-area">

        <div v-if="activeTab === 'text'" class="card">
          <h2>Анализатор текста</h2>
          <input v-model="text" placeholder="Введите текст..." />
          <div class="btn-group">
            <button @click="removeVowels">Удалить гласные</button>
            <button @click="countConsonants">Согласные</button>
            <button @click="letterMap">Карта букв</button>
          </div>
          <div class="result-box" :class="{ error: textError }">{{ textResult || 'Результат...' }}</div>
        </div>

        <div v-if="activeTab === 'exception'" class="card">
          <h2>Обработка исключений</h2>
          <div class="btn-group">
            <button class="btn-dark" @click="triggerException('checked')">Checked</button>
            <button class="btn-dark" @click="triggerException('unchecked')">Unchecked</button>
            <button class="btn-dark" @click="triggerException('log')">Log4j2</button>
          </div>
          <div class="result-box" :class="{ error: exceptionError }">{{ exceptionResult || 'Ответ...' }}</div>
        </div>

        <div v-if="activeTab === 'library'" class="card">
          <h2>Добавить книгу</h2>
          <div class="form-group">
            <input v-model="title" placeholder="Название" />
            <input v-model="authorName" placeholder="Автор" />
            <input v-model="pages" type="number" placeholder="Страниц" />
          </div>
          <button @click="addBook" class="btn-success full-width">Добавить (POST)</button>

          <h2 style="margin-top: 30px;">Все книги ({{ books.length }})</h2>
          <input v-model="searchQuery" placeholder="Поиск по названию..." style="margin-bottom: 15px;" />
          <table class="books-table">
            <thead>
              <tr><th>ID</th><th>Название</th><th>Авторы</th><th>Стр.</th><th>Действия</th></tr>
            </thead>
            <tbody>
              <tr v-for="book in books.filter(b => b.title.toLowerCase().includes(searchQuery.toLowerCase()))" :key="book.id">
                <td>{{ book.id }}</td>
                <td><strong>{{ book.title }}</strong></td>
                <td>
                  <span v-for="a in book.authors" :key="a.id" class="tag">{{ a.name }}</span>
                </td>
                <td>{{ book.pages || '—' }}</td>
                <td>
                  <button @click="deleteBook(book.id)" class="btn-delete">Удалить</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div v-if="activeTab === 'search'" class="card">
          <h2>Фильтрация и поиск</h2>
          <div class="form-group">
            <input v-model="searchX" type="number" placeholder="Книги > X страниц..." />
            <button @click="getBooksOverXPages">Найти</button>
          </div>
          <div class="form-group">
            <input v-model="searchAuthor" placeholder="Фамилия автора..." />
            <button @click="getBooksByAuthor">Найти</button>
          </div>
          <div class="result-box">{{ filterResult || 'Результат...' }}</div>
        </div>

        <div v-if="activeTab === 'chat'" class="card">
          <h2>Отправка сообщения</h2>
          <div class="form-group">
            <input v-model="chatIp" placeholder="IP сервера (например, localhost)" />
            <input v-model="chatName" placeholder="Ваше имя" />
          </div>
          <div class="form-group">
            <input v-model="chatText" placeholder="Текст сообщения..." style="flex: 3;" />
            <button @click="sendMessage" style="flex: 1;">Отправить</button>
          </div>

          <h2 style="margin-top: 30px;">История сообщений</h2>
          <button @click="loadMessages" class="btn-dark" style="margin-bottom: 15px; width: 100%;">Обновить историю</button>

          <ul class="chat-list">
            <li v-for="msg in chatLog" :key="msg.id">
              <strong>{{ msg.senderName }}</strong>: {{ msg.text }}
              <span class="chat-time">{{ new Date(msg.timestamp).toLocaleString() }}</span>
            </li>
          </ul>
        </div>

      </div>
    </div>

    <div class="toast-container">
      <div v-for="toast in toasts" :key="toast.id" :class="['toast', toast.type]">
        {{ toast.message }}
      </div>
    </div>

  </div>
</template>

<style scoped>
.page-wrapper {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  min-height: 100vh;
  background-color: #f4f4f9;
  padding: 40px 20px;
  box-sizing: border-box;
}

.main-container {
  width: 100%;
  max-width: 800px;
}

.main-header {
  text-align: center;
  margin-bottom: 30px;
}

.main-header h1 {
  color: #2c3e50;
  margin-bottom: 20px;
}

.tabs {
  display: flex;
  justify-content: center;
  gap: 10px;
  flex-wrap: wrap;
}

.tabs button {
  padding: 10px 20px;
  border: none;
  border-radius: 20px;
  background-color: #e2e8f0;
  color: #475569;
  cursor: pointer;
  font-weight: 600;
  transition: 0.2s;
}

.tabs button:hover { background-color: #cbd5e1; }
.tabs button.active { background-color: #42b883; color: white; }

.card {
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.05);
}

h2 { margin-top: 0; color: #333; font-size: 1.2rem; margin-bottom: 20px; }

input {
  width: 100%;
  padding: 12px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  font-size: 14px;
}

.form-group { display: flex; gap: 10px; margin-bottom: 15px; }
.form-group input { margin-bottom: 0; }
.btn-group { display: flex; gap: 10px; margin-bottom: 20px; }

button {
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  background-color: #e52b50;
  color: white;
  cursor: pointer;
  font-weight: 600;
  transition: 0.2s;
}
button:hover { opacity: 0.9; }

.btn-dark { background-color: #333; }
.btn-success { background-color: #28a745; }
.btn-delete { background-color: #dc3545; padding: 6px 12px; font-size: 12px; }
.full-width { width: 100%; margin-bottom: 20px; }

.result-box {
  background: #e9ecef;
  padding: 15px;
  border-radius: 6px;
  min-height: 50px;
  font-family: monospace;
  white-space: pre-wrap;
  word-wrap: break-word;
  max-height: 300px;
  overflow-y: auto;
}
.result-box.error { color: #dc3545; font-weight: bold; }

.books-table { width: 100%; border-collapse: collapse; }
.books-table th, .books-table td { padding: 10px; text-align: left; border-bottom: 1px solid #eee; font-size: 14px; }
.books-table th { background-color: #f8fafc; color: #64748b; }
.tag { background: #e2e8f0; padding: 2px 6px; border-radius: 4px; font-size: 12px; margin-right: 4px; }

.chat-list { list-style: none; padding: 0; margin: 0; }
.chat-list li { padding: 10px; border-bottom: 1px solid #eee; font-size: 14px; }
.chat-time { float: right; color: #94a3b8; font-size: 12px; }

.toast-container {
  position: fixed;
  top: 20px;
  right: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  z-index: 1000;
}
.toast {
  padding: 12px 20px;
  border-radius: 8px;
  color: white;
  font-weight: 600;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
  animation: slideIn 0.3s ease-out;
}
.toast.success { background-color: #28a745; }
.toast.error { background-color: #dc3545; }

@keyframes slideIn {
  from { transform: translateX(100%); opacity: 0; }
  to { transform: translateX(0); opacity: 1; }
}
</style>