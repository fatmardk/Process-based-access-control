<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">İşlem Detayı</h2>

    <div v-if="loading" class="text-gray-600">Yükleniyor...</div>
    <div v-else-if="error" class="text-red-600">Hata: {{ error }}</div>
    <div v-else-if="processDetail">
      <p><strong>Kod:</strong> {{ processDetail.code }}</p>
      <p><strong>İsim:</strong> {{ processDetail.name }}</p>
      <p><strong>Açıklama:</strong> {{ processDetail.description }}</p>
    </div>

    <Button class="mt-4" label="Geri Dön" @click="goBack" />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '../services/api'
import Button from 'primevue/button'

const route = useRoute()
const router = useRouter()

const processCode = route.params.code
const processDetail = ref(null)
const loading = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    // Burada örnek bir API isteği var, kendi endpoint'ine göre düzenle
    const res = await api.get(`/process/${processCode}`)
    processDetail.value = res.data
  } catch (err) {
    error.value = 'İşlem detayları alınamadı.'
    console.error(err)
  } finally {
    loading.value = false
  }
})

const goBack = () => {
  router.back()
}
</script>

<style scoped>
p {
  margin-bottom: 0.5rem;
}
</style>
