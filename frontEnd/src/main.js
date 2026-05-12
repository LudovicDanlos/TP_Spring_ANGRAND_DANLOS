import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

createApp(App).use(router).mount('#app')

// Stockage d'une information dans le localStorage
localStorage.setItem("User", "ANGRAND Louis");
