import { defineStore } from 'pinia'
import axios from '../services/api'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: null,
  }),
  getters: {
    isAuthenticated: (state) => !!state.token,
  },
  actions: {
    async login(username, password) {
      const res = await axios.post('/auth/login', {
        username,
        password
      })

      this.token = res.data.token
      localStorage.setItem('token', this.token)
      console.log('Token:', this.token)

      await this.fetchUser()
    },

    async fetchUser() {
      try {
        const res = await axios.get('/user/me', {
  headers: {
    Authorization: `Bearer ${this.token}`
  }
})

        this.user = res.data
        console.log('User Info:', this.user)
      } catch (err) {
        console.error('Kullanıcı bilgisi alınamadı:', err)
      }
    },

    logout() {
      this.token = ''
      this.user = null
      localStorage.removeItem('token')
    }
  }
})
