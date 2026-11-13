import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/remove-vowels': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/count-consonants': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/letter-map': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/exception': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/crud': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
})