import axios from 'axios';

// Create axios instance with base configuration
const api = axios.create({
    baseURL: 'http://localhost:8080/api',  // Ajout du préfixe /api
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    }
});

// Add response interceptor
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response) {
            // Erreur de réponse du serveur
            console.error('Erreur API:', error.response.status, error.response.data);
        } else if (error.request) {
            // Erreur de connexion
            console.error('Erreur de connexion:', error.message);
        } else {
            // Autre erreur
            console.error('Erreur:', error.message);
        }
        return Promise.reject(error);
    }
);

export default api;