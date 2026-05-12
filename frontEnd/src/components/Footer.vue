<template>
  <footer class="footer">
    <div class="footer-container">
      <p class="footer-text">&copy; 2024 Task Manager. Tous droits réservés.</p>
      <div class="footer-links">
        <a href="https://www.youtube.com/watch?v=xMHJGd3wwZk" class="footer-link">À propos</a>
        <a href="https://www.youtube.com/watch?v=xMHJGd3wwZk" class="footer-link">Contact</a>
        <a href="https://www.youtube.com/watch?v=xMHJGd3wwZk" class="footer-link">Confidentialité</a>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name:'Footer'
};
</script>

<style scoped>
.footer {
  background-color: #2d3748;
  color: white;
  padding: 2rem 0;
  margin-top: auto;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;
}

.footer-text {
  margin: 0;
  font-size: 0.875rem;
  color: #a0aec0;
}

.footer-links {
  display: flex;
  gap: 2rem;
}

.footer-link {
  color: #a0aec0;
  text-decoration: none;
  font-size: 0.875rem;
  transition: color 0.2s ease;
}

.footer-link:hover {
  color: white;
}
</style>