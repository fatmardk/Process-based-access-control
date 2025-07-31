<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import { useRouter } from 'vue-router'
import api from '../services/api'

const userInfo = ref(null)
const authStore = useAuthStore()
const router = useRouter()

onMounted(async () => {
  try {
    const res = await api.get('/user/me')
    userInfo.value = res.data
  } catch (err) {
    console.error('Kullanıcı bilgileri alınamadı:', err)
  }
})

const logout = () => {
  authStore.logout()
  router.push('/login')
}
</script>
<template>
  <div class="p-4">
    <h2 class="text-2xl font-bold mb-4">Hoş geldiniz</h2>

    <div v-if="userInfo">
      <p><strong>Full Name:</strong> {{ userInfo.fullName }}</p>
      <p><strong>Username:</strong> {{ userInfo.username }}</p>
      <p><strong>Email:</strong> {{ userInfo.email }}</p>

      <h3 class="mt-4 font-semibold">Roller:</h3>
      <ul>
        <li v-for="role in userInfo.roles" :key="role.roleCode">
          {{ role.roleCode }} - Proje ID: {{ role.projectId }} - Grantable: {{ role.grantable ? 'Evet' : 'Hayır' }}
        </li>
      </ul>

      <h3 class="mt-6 font-semibold">Allowed Processes:</h3>
      <ul class="list-disc pl-6">
        <li
          v-for="proc in userInfo.allowedProcesses"
          :key="proc.code"
          class="my-1"
        >
          <router-link
            :to="`/process/${proc.code}`"
            class="text-blue-600 hover:underline"
          >
            {{ proc.code }} - {{ proc.name }}
          </router-link>
          <div class="text-sm text-gray-500 ml-2">{{ proc.explanation }}</div>
        </li>
      </ul>
    </div>

    <Button label="Çıkış Yap" type="button" class="w-1/5 mt-4" @click="logout" />
  </div>
</template>
