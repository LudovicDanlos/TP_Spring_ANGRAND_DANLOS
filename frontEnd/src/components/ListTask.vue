<template>
  <div class="task-list-container">
    <div v-if="error" class="error-message">
      <p>Erreur : {{ error }}</p>
    </div>

    <div class="filters">
      <div class="filter-group">
        <label for="status-filter">Filtrer par statut :</label>
        <select id="status-filter" v-model="filters.status" @change="updateFilters" class="form-select">
          <option value="">Tous</option>
          <option value="TODO">À faire</option>
          <option value="IN_PROGRESS">En cours</option>
          <option value="DONE">Terminée</option>
        </select>
      </div>
      <div class="filter-group">
        <label for="priority-filter">Filtrer par priorité :</label>
        <select id="priority-filter" v-model="filters.priority" @change="updateFilters" class="form-select">
          <option value="">Toutes</option>
          <option value="LOW">Faible</option>
          <option value="MEDIUM">Moyenne</option>
          <option value="HIGH">Élevée</option>
        </select>
      </div>
    </div>

    <div class="task-grid">
      <div v-for="task in taskList" :key="task.id" class="task-card" :class="getPriorityClass(task.priority)">
        <div class="task-header">
          <h3 class="task-name">{{ task.name ? task.name.toUpperCase() : '' }}</h3>
          <span class="task-priority">{{ task.priority }}</span>
        </div>
        <p class="task-description">{{ task.description }}</p>
        <div class="task-details">
          <span class="task-status">{{ task.status }}</span>
          <span class="task-date">Créé: {{ formatDate(task.creationDate) }}</span>
          <span class="task-deadline">Échéance: {{ formatDate(task.deadline) }}</span>
        </div>
        <div class="task-actions">
          <button class="btn btn-edit" @click="editTask(task)">Modifier</button>
          <button class="btn btn-delete" @click="deleteTask(task)">Supprimer</button>
          <button class="btn btn-toggle" @click="toggleStatus(task)">
            {{ task.status === 'TODO' ? 'Mettre IN_PROGRESS' : task.status === 'IN_PROGRESS' ? ' Mettre DONE' : 'Mettre TODO' }}
          </button>
        </div>
      </div>
    </div>

    <div class="pagination" v-if="nextPageAvailable || previousPageAvailable">
      <button class="btn btn-pagination" :disabled="!previousPageAvailable" @click="loadTasks(pagination.page - 1)">Précédent</button>
      <span class="page-info">Page {{ pagination.page + 1 }}</span>
      <button class="btn btn-pagination" :disabled="!nextPageAvailable" @click="loadTasks(pagination.page + 1)">Suivant</button>
    </div>
  </div>
</template>

<script>
import TaskService from '@/services/TaskService';

export default {
  name: 'ListTask',
  data() {
    return {
      taskList: [],
      error: null,
      pagination: {
        page: 0,
        size: 6
      },
      nextPageAvailable: false,
      previousPageAvailable: false,
      filters: {
        status: '',
        priority: ''
      }
    }
  },
  methods: {
    // Chargement des tâches avec pagination et filtres
    async loadTasks(page = 0) {
      this.error = null;
      try {
        // Met à jour la page courante
        this.pagination.page = page;

        // Vérifie la disponibilité des pages suivante et précédente
        this.nextPageAvailable = await this.checkPageAvailability(this.pagination.page + 1);
        this.previousPageAvailable = await this.checkPageAvailability(this.pagination.page - 1);

        // Prépare les paramètres de requête en fonction des filtres
        const params = { page, size: this.pagination.size };
        if (this.filters.status) params.status = this.filters.status;
        if (this.filters.priority) params.priority = this.filters.priority;

        // Récupère les tâches depuis le service
        const result = await TaskService.getAllTasks(params);
        this.taskList = result.content || [];
      } catch (e) {
        this.error = e.message || 'Impossible de charger les tâches.';
        this.nextPageAvailable = false;
        this.previousPageAvailable = false;
      }
    },

    // Vérifie si une page est disponible en essayant de charger les tâches de cette page
    async checkPageAvailability(page) {
      try {
        // Prépare les paramètres de requête en fonction des filtres
        const params = { page, size: this.pagination.size };
        if (this.filters.status) params.status = this.filters.status;
        if (this.filters.priority) params.priority = this.filters.priority;

        // Tente de charger les tâches de la page spécifiée
        const result = await TaskService.getAllTasks(params);

        //Si le résultat contient des tâches, la page est disponible
        return Array.isArray(result.content) && result.content.length > 0;
      } catch (e) {
        return false;
      }
    },

    // Supprime une tâche après confirmation de l'utilisateur
    async deleteTask(task) {
      // Demande de confirmation avant de supprimer la tâche
      if (!confirm(`Supprimer la tâche « ${task.name} » ?`)) {
        return;
      }

      // Réinitialise l'erreur avant de tenter la suppression
      this.error = null;

      try {
        // Appelle le service pour supprimer la tâche
        await TaskService.deleteTask(task.id);

        // Recharge les tâches après suppression
        await this.loadTasks(this.pagination.page);
      } catch (e) {
        this.error = e.message || 'Impossible de supprimer la tâche.';
      }
    },

    // Bascule le statut d'une tâche entre TODO, IN_PROGRESS et DONE
    async toggleStatus(task) {
      // Réinitialise l'erreur avant de tenter de changer le statut
      this.error = null;
      try {
        // Calcul du prochain statut en fonction du statut actuel
        const nextStatus = task.status === 'TODO' ? 'IN_PROGRESS' : task.status === 'IN_PROGRESS' ? 'DONE' : 'TODO';

        // Appelle le service pour mettre à jour la tâche avec le nouveau statut
        await TaskService.updateTask(task.id, { ...task, status: nextStatus, deadline: this.formatDateForApi(task.deadline), creationDate: this.formatDateForApi(task.creationDate) });
        
        // Recharge les tâches après la mise à jour du statut
        await this.loadTasks(this.pagination.page);
      } catch (e) {
        this.error = e.message || 'Impossible de changer le statut.';
      }
    },

    // Redirige vers le formulaire de modification d'une tâche
    editTask(task) {
      this.$router.push({ name: 'form', params: { id: task.id } });
    },

    // Retourne une classe CSS en fonction de la priorité de la tâche pour le style
    getPriorityClass(priority) {
      switch(priority) {
        case 'HIGH': return 'high';
        case 'MEDIUM': return 'medium';
        case 'LOW': return 'low';
        default: return 'normal';
      }
    },

    // Formate une date pour l'affichage en français
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString('fr-FR') + ' ' + date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
    },

    // Formate une date pour l'API
    formatDateForApi(dateString) {
      if (!dateString) {
        return null;
      }
      return dateString.replace('T', ' ').slice(0, 16);
    },

    // Met à jour les filtres et synchronise avec les paramètres de l'URL
    updateFilters() {
      const query = {};
      if (this.filters.status) query.status = this.filters.status;
      if (this.filters.priority) query.priority = this.filters.priority;
      this.$router.push({ query }).catch(() => {});
      this.loadTasks(0); // Reset to page 0 when filters change
    },

    // Initialise les filtres à partir des paramètres de l'URL lors du montage du composant
    setFiltersFromQuery() {
      this.filters.status = this.$route.query.status || '';
      this.filters.priority = this.$route.query.priority || '';
    },
  },
  watch: {
    '$route.query': {
      handler() {
        this.setFiltersFromQuery();
        this.loadTasks(0);
      },
      deep: true
    }
  },
  async mounted() {
    this.setFiltersFromQuery();
    await this.loadTasks();
  }
}
</script>

<style scoped>
.task-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.error-message {
  background-color: #fee;
  color: #c33;
  padding: 1rem;
  border-radius: 0.5rem;
  margin-bottom: 2rem;
  border: 1px solid #fcc;
}

.filters {
  display: flex;
  gap: 2rem;
  margin-bottom: 2rem;
  padding: 1rem;
  background: #f7fafc;
  border-radius: 0.5rem;
  border: 1px solid #e2e8f0;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.filter-group label {
  font-weight: 600;
  color: #2d3748;
}

.form-select {
  padding: 0.5rem;
  border: 1px solid #cbd5e0;
  border-radius: 0.375rem;
  background: white;
  font-size: 1rem;
}

.task-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.task-card {
  background: white;
  border-radius: 0.75rem;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  border-left: 4px solid #ddd;
}

.task-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.15);
}

.task-card.high {
  border-left-color: #e53e3e;
  background: linear-gradient(135deg, #fed7d7 0%, #feb2b2 100%);
}

.task-card.medium {
  border-left-color: #d69e2e;
  background: linear-gradient(135deg, #fef5e7 0%, #fbd38d 100%);
}

.task-card.low {
  border-left-color: #38a169;
  background: linear-gradient(135deg, #c6f6d5 0%, #9ae6b4 100%);
}

.task-card.normal {
  border-left-color: #718096;
  background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
}

.task-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.task-name {
  margin: 0;
  font-size: 1.25rem;
  font-weight: 600;
  color: #2d3748;
}

.task-priority {
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  background-color: rgba(255, 255, 255, 0.8);
}

.task-description {
  color: #4a5568;
  margin-bottom: 1rem;
  line-height: 1.5;
}

.task-details {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 1rem;
  font-size: 0.875rem;
  color: #718096;
}

.task-status {
  font-weight: 500;
  color: #2d3748;
}

.task-actions {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.btn {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-edit {
  background-color: #3182ce;
  color: white;
}

.btn-edit:hover {
  background-color: #2c5282;
}

.btn-delete {
  background-color: #e53e3e;
  color: white;
}

.btn-delete:hover {
  background-color: #c53030;
}

.btn-toggle {
  background-color: #38a169;
  color: white;
}

.btn-toggle:hover {
  background-color: #2f855a;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1rem;
  margin-top: 2rem;
}

.btn-pagination {
  background-color: #4a5568;
  color: white;
}

.btn-pagination:hover:not(:disabled) {
  background-color: #2d3748;
}

.btn-pagination:disabled {
  background-color: #a0aec0;
  cursor: not-allowed;
}

.page-info {
  font-weight: 500;
  color: #4a5568;
}
</style>