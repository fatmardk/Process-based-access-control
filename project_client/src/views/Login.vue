<template>
  <div class="flex justify-content-center align-items-center min-h-screen bg-gray-100">
    <div class="card w-10 md:w-6 lg:w-6 xl:w-6">
      <h2 class="text-center mb-4 text-2xl font-bold">Giriş Yap</h2>
      <form @submit.prevent="handleLogin">
        <div class="mb-3">
          <label for="username" class="block mb-1 font-medium">Kullanıcı Adı</label>
          <InputText id="username" v-model="username" class="w-full" autocomplete="username" />
        </div>

        <div class="mb-3">
          <label for="password" class="block mb-1 font-medium">Şifre</label>
          <Password id="password" v-model="password" class="w-full" toggleMask autocomplete="current-password" />
        </div>

        <Button label="Giriş Yap" type="submit" class="w-full" :loading="loading" />

        <p v-if="error" class="mt-2 text-red-600 text-sm">{{ error }}</p>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'

const username = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

const authStore = useAuthStore()
const router = useRouter()
const handleLogin = async () => {
  error.value = ''
  loading.value = true
  try {
    await authStore.login(username.value, password.value)
    router.push('/') // Giriş başarılıysa ana sayfaya yönlendir
  } catch (err) {
    error.value = 'Login failed. Please check your username and password.'
    console.error(err)
  } finally {
    loading.value = false
  }
}
console.log('Login bileşeni yüklendi')
</script>

<style scoped>
.card {
  padding: 2rem;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

::v-deep(.p-password) {
  width: 100%;
}

::v-deep(.p-password-input) {
  width: 100%;
  box-sizing: border-box;
}
</style>

