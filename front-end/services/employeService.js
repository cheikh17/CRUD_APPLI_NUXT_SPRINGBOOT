import api from '../utils/api';

export const employeService = {
    // Récupérer tous les employés
    getAll: () => api.get('/employes/all'),

    // Récupérer un employé par son ID
    getById: (id) => api.get(`/employes/${id}`),

    // Créer un nouvel employé
    create: (employe) => api.post('/employes/create', employe),

    // Mettre à jour un employé
    update: (id, employe) => api.put(`/employes/update/${id}`, employe),

    // Supprimer un employé
    delete: (id) => api.delete(`/employes/delete/${id}`)
};