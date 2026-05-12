import Axios from "axios";

const axios = Axios.create({});

// URL de base de l'API
const BASE_URL = "/api";

class TaskApi {
    // Méthode create
    createTask(task) { 
        return axios.post(`${BASE_URL}/tasks`, task);
    };

    // Méthode findAll
    getAllTasks(params = {}) { 
        return axios.get(`${BASE_URL}/tasks`, { params });
    };

    // Méthode findById
    getTask(id) { 
        return axios.get(`${BASE_URL}/tasks/${id}`);
    };

    // Méthode update
    updateTask(id, task) { 
        return axios.put(`${BASE_URL}/tasks/${id}`, task);
    };

    // Méthode delete
    deleteTask(id) { 
        return axios.delete(`${BASE_URL}/tasks/${id}`);
    };
}

export default new TaskApi();