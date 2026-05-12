<template>
  <div class="form-container">
    <div class="form-card">
      <h1 class="form-title">{{ formTitle }}</h1>

      <form @submit.prevent="saveTask" class="task-form">
        <div class="form-group">
          <label for="name" class="form-label">Nom</label>
          <input type="text" id="name" v-model="task.name" class="form-input" placeholder="Entrez le nom de la tâche">
        </div>

        <div class="form-group">
          <label for="description" class="form-label">Description</label>
          <textarea id="description" v-model="task.description" class="form-textarea" placeholder="Décrivez la tâche"></textarea>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label for="priority" class="form-label">Priorité</label>
            <select id="priority" v-model="task.priority" class="form-select">
              <option value="LOW">Faible</option>
              <option value="MEDIUM">Moyenne</option>
              <option value="HIGH">Élevée</option>
            </select>
          </div>

          <div class="form-group">
            <label for="status" class="form-label">Statut</label>
            <select id="status" v-model="task.status" class="form-select">
              <option value="TODO">À faire</option>
              <option value="IN_PROGRESS">En cours</option>
              <option value="DONE">Terminée</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label for="deadline" class="form-label">Date limite</label>
          <input type="datetime-local" id="deadline" v-model="task.deadline" class="form-input">
        </div>

        <div class="form-actions">
          <button type="button" class="btn btn-secondary" @click="cancelForm">Annuler</button>
          <button type="submit" class="btn btn-primary">{{ submitLabel }}</button>
        </div>
      </form>

      <div class="error-message" v-if="error">
        <p>{{ errorMessage }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import TaskService from '@/services/TaskService';

export default {
  name: 'FormTask',
  props: {
    id: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      task: {
        id: null,
        name: '',
        description: '',
        priority: 'MEDIUM',
        status: 'TODO',
        deadline: ''
      },
      error: false,
      errorMessage: ''
    };
  },
  computed: {
    isEditMode() {
      return Boolean(this.id);
    },
    formTitle() {
      return this.isEditMode ? 'Modifier une tâche' : 'Créer une tâche';
    },
    submitLabel() {
      return this.isEditMode ? 'Enregistrer' : 'Créer';
    }
  },
  methods: {
    // Charge les données d'une tâche existante pour le formulaire de modification
    async loadTask() {
      if (!this.isEditMode) {
        return;
      }
      this.error = false;
      this.errorMessage = '';
      try {
        const response = await TaskService.getTask(this.id);
        this.task = {
          ...response,
          deadline: response.deadline ? this.formatToInputDate(response.deadline) : ''
        };
      } catch (e) {
        this.error = true;
        this.errorMessage = e.message || 'Impossible de charger la tâche.';
      }
    },

    // Formate une date de l'API
    formatToInputDate(value) {
      if (!value) {
        return '';
      }
      const normalized = value.replace('T', ' ').trim();
      const [datePart, timePart] = normalized.split(' ');
      if (!datePart || !timePart) {
        return '';
      }
      const [year, month, day] = datePart.split('-').map(Number);
      const [hour, minute] = timePart.split(':').map(Number);
      const localDate = new Date(year, month - 1, day, hour, minute);
      return localDate.toISOString().slice(0, 16);
    },

    // Formate une date pour l'API
    formatDeadlineForApi(deadline) {
      if (!deadline) {
        return null;
      }
      return deadline.replace('T', ' ').slice(0, 16);
    },

    // Valide les données du formulaire et envoie la requête pour créer ou mettre à jour une tâche
    async saveTask() {
      this.error = false;
      this.errorMessage = '';

      // Validation des champs du formulaire
      if (!this.taskIsValid()) {
        this.error = true;
        this.errorMessage = 'Veuillez remplir tous les champs obligatoires.';
        return;
      }

      // Prépare les données à envoyer à l'API
      const taskToSend = {
        name: this.task.name,
        description: this.task.description,
        priority: this.task.priority,
        status: this.task.status,
        deadline: this.formatDeadlineForApi(this.task.deadline)
      };

      // Envoie la requête pour créer ou mettre à jour la tâche
      try {
        if (this.isEditMode) {
          await TaskService.updateTask(this.id, taskToSend);
        } else {
          await TaskService.addTask({ ...taskToSend, creationDate: this.formatDeadlineForApi(new Date().toISOString().slice(0, 16)) });
        }
        this.$router.push({ name: 'list' });
      } catch (e) {
        this.error = true;
        this.errorMessage = e.message || 'Une erreur est survenue lors de l’enregistrement.';
      }
    },

    // Vérifie que tous les champs requis du formulaire sont remplis
    taskIsValid() {
      return this.task.name && this.task.description && this.task.priority && this.task.status;
    },

    // Redirige vers la liste des tâches sans enregistrer les modifications
    cancelForm() {
      this.$router.push({ name: 'list' });
    }
  },
  async mounted() {
    await this.loadTask();
  }
};
</script>

<style scoped>
.form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.form-card {
  background: white;
  border-radius: 0.75rem;
  padding: 2rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.form-title {
  text-align: center;
  margin-bottom: 2rem;
  color: #2d3748;
  font-size: 2rem;
  font-weight: 700;
}

.task-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.5rem;
}

.form-label {
  font-weight: 600;
  color: #4a5568;
  font-size: 0.875rem;
}

.form-input,
.form-textarea,
.form-select {
  padding: 0.75rem;
  border: 1px solid #e2e8f0;
  border-radius: 0.375rem;
  font-size: 1rem;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.form-input:focus,
.form-textarea:focus,
.form-select:focus {
  outline: none;
  border-color: #3182ce;
  box-shadow: 0 0 0 3px rgba(49, 130, 206, 0.1);
}

.form-textarea {
  resize: vertical;
  min-height: 100px;
}

.form-select {
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='m6 8 4 4 4-4'/%3e%3c/svg%3e");
  background-position: right 0.5rem center;
  background-repeat: no-repeat;
  background-size: 1.5em 1.5em;
  padding-right: 2.5rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 1rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 0.375rem;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-primary {
  background-color: #3182ce;
  color: white;
}

.btn-primary:hover {
  background-color: #2c5282;
}

.btn-secondary {
  background-color: #e2e8f0;
  color: #4a5568;
}

.btn-secondary:hover {
  background-color: #cbd5e0;
}

.error-message {
  margin-top: 1.5rem;
  padding: 1rem;
  background-color: #fed7d7;
  color: #c53030;
  border-radius: 0.375rem;
  border: 1px solid #feb2b2;
}
</style>
