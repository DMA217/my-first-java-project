<script setup>
import { ref } from 'vue'

const name = ref('Гость')
const message = ref('')

const sayHello = async () => {
  try {
    const response = await fetch(`/api/hello?name=${encodeURIComponent(name.value)}`)
    const text = await response.text()
    message.value = text
  } catch (error) {
    message.value = 'Ошибка: ' + error.message
  }
}
</script>

<template>
  <div style="max-width: 600px; margin: 40px auto; font-family: sans-serif;">
    <h1>Мой Vue-проект</h1>

    <input v-model="name" placeholder="Введите имя..." style="padding: 10px; width: 100%; margin-bottom: 10px;" />
    <button @click="sayHello" style="padding: 10px 20px; background: #42b883; color: white; border: none; cursor: pointer;">
      Поздороваться с бэкендом
    </button>

    <p v-if="message" style="margin-top: 20px; font-weight: bold;">
      Ответ от Spring Boot: {{ message }}
    </p>
  </div>
</template>