<template>
  <header class="navbar">
    <div class="navbar-container">
      <h1 class="navbar-title">Task Manager</h1>
      <nav class="navbar-nav">
        <RouterLink to="/" class="nav-link">Liste des Tâches</RouterLink>
        <RouterLink to="/form" class="nav-link">Nouvelle Tâche</RouterLink>
      </nav>
    </div>
  </header>
</template>

<script>
export default {
  name: "TaskMenu"
}
</script>

<style scoped>
.navbar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1rem 0;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
}

.navbar-title {
  margin: 0;
  font-size: 1.5rem;
  font-weight: 600;
}

.navbar-nav {
  display: flex;
  gap: 2rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  transition: background-color 0.3s ease;
  font-weight: 500;
}

.nav-link:hover,
.nav-link.router-link-active {
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
