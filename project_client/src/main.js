import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config'
import { createPinia } from 'pinia'

// Stil dosyaları
import 'primevue/resources/themes/lara-light-blue/theme.css'
import 'primevue/resources/primevue.min.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css'

// PrimeVue bileşenleri
import InputText from 'primevue/inputtext'
import Password from 'primevue/password'
import Button from 'primevue/button'

// ✔ Pinia kur
const pinia = createPinia()

// ✔ Uygulamayı başlat
const app = createApp(App)

app.use(pinia)        
app.use(router)
app.use(PrimeVue)

app.component('InputText', InputText)
app.component('Password', Password)
app.component('Button', Button)

app.mount('#app')
