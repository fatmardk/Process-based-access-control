<script setup>
import { ref, onMounted } from 'vue'
import api from '../services/api'

const userInfo = ref(null)

onMounted(async () => {
  try {
    const res = await api.get('/user/me')
    userInfo.value = res.data
    console.log('Kullanıcı bilgileri:', userInfo.value)
  } catch (err) {
    console.error('Kullanıcı bilgileri alınamadı:', err)
  }
})
</script>

<template>
  <div class="p-4">
    <h2 class="text-2xl font-bold mb-4">Hoş geldiniz</h2>
    <div v-if="userInfo">
      <p><strong>Ad Soyad:</strong> {{ userInfo.fullName }}</p>
      <p><strong>Kullanıcı Adı:</strong> {{ userInfo.username }}</p>
      <p><strong>Email:</strong> {{ userInfo.email }}</p>
      <!--<p><strong>Aktif mi:</strong> {{ userInfo.isActive ? 'Evet' : 'Hayır' }}</p>-->

      <h3 class="mt-4 font-semibold">Roller:</h3>
      <ul>
        <li v-for="role in userInfo.roles" :key="role.roleCode">
          {{ role.roleCode }} - Proje ID: {{ role.projectId }} - Grantable: {{ role.grantable ? 'Evet' : 'Hayır' }}
        </li>
      </ul>
    </div>
  </div>
</template>
