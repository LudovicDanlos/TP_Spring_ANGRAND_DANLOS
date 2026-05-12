<template>
  <div class="home">
    <!-- Affichage du menu -->
    <TaskMenu/>
    <!-- Affichage du contenu principal -->
    <main class="main-content">
      <router-view></router-view>
    </main>
    <!-- Affichage du footer -->
    <Footer/>

    <!--<div class="user-info" v-if="username">
      <p>Connecté en tant que : <strong>{{ username }}</strong></p>
    </div>-->
  </div>
</template>

<script>
import TaskApi from "@/api/TaskApi";

// Importation des composants
import Footer from "../components/Footer.vue";
import TaskMenu from "../components/TaskMenu.vue";

export default {
  name: 'Home',
  components: { Footer, TaskMenu },
  // Récupération du nom d'utilisateur depuis le localStorage
  data() {
    return {
      username: localStorage.getItem("User"),
    };
  },
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  padding: 2rem 0;
}

.user-info {
  position: fixed;
  top: 1rem;
  right: 2rem;
  background: white;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  font-size: 0.875rem;
  color: #4a5568;
}
</style>