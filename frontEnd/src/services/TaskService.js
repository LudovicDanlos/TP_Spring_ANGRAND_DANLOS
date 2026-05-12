import TaskApi from "../api/TaskApi.js";

class TaskService {
    tasks = [];

    // Méthode create
    async addTask(task) {
        try {
            await TaskApi.createTask(task);
        } catch (e) {
            console.error(e);
            throw Error("Impossible d'ajouter la tâche");
        }
    }

    // Méthode findAll
    async getAllTasks(options = {}) {
        try {
            const apiResponse = await TaskApi.getAllTasks(options);
            const data = apiResponse.data;

            if (Array.isArray(data)) {
                this.tasks = data;
                return {
                    content: data,
                    totalPages: 1,
                    totalElements: data.length,
                    number: 0
                };
            }

            if (data.content) {
                this.tasks = data.content;
            } else if (data.tasks) {
                this.tasks = data.tasks;
            } else {
                this.tasks = data;
            }

            return {
                ...data,
                content: this.tasks
            };
        } catch (e) {
            console.error(e);
            throw Error("Impossible de récupérer les tâches");
        }
    }

    // Méthode findById
    async getTask(id) {
        try {
            const apiResponse = await TaskApi.getTask(id);
            return apiResponse.data;
        } catch (e) {
            console.error(e);
            throw Error("Impossible de récupérer la tâche");
        }
    }

    // Méthode update
    async updateTask(id, task) {
        try {
            await TaskApi.updateTask(id, task);
        } catch (e) {
            console.error(e);
            throw Error("Impossible de mettre à jour la tâche");
        }
    }

    // Méthode delete
    async deleteTask(id) {
        try {
            await TaskApi.deleteTask(id);
        } catch (e) {
            console.error(e);
            throw Error("Impossible de supprimer la tâche");
        }
    }
}

export default new TaskService();