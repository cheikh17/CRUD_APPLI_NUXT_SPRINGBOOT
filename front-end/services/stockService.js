import api from '../utils/api';

export const stockService = {
    // Récupérer tous les produits
    getAll: () => api.get('/stocks/all'),

    // Récupérer un produit par son ID
    getById: (id) => api.get(`/stocks/${id}`),

    // Créer un nouveau produit
    create: (produit) => api.post('/stocks/create', produit),

    // Mettre à jour un produit
    update: (id, produit) => api.put(`/stocks/update/${id}`, produit),

    // Supprimer un produit
    delete: (id) => api.delete(`/stocks/delete/${id}`)
};