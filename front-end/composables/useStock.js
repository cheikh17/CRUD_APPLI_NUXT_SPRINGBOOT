import { ref } from 'vue';
import { stockService } from '../services/stockService';

export function useStock() {
    const stocks = ref([]);
    const loading = ref(false);
    const error = ref(null);

    const fetchStocks = async () => {
        loading.value = true;
        error.value = null;
        try {
            const response = await stockService.getAll();
            stocks.value = response.data;
        } catch (e) {
            error.value = "Erreur lors du chargement des produits";
            console.error(e);
        } finally {
            loading.value = false;
        }
    };

    const createStock = async (produit) => {
        loading.value = true;
        error.value = null;
        try {
            const response = await stockService.create(produit);
            stocks.value.push(response.data);
            return response.data;
        } catch (e) {
            error.value = "Erreur lors de la création du produit";
            throw e;
        } finally {
            loading.value = false;
        }
    };

    const updateStock = async (id, produit) => {
        loading.value = true;
        error.value = null;
        try {
            const response = await stockService.update(id, produit);
            const index = stocks.value.findIndex(s => s.id === id);
            if (index !== -1) {
                stocks.value[index] = response.data;
            }
            return response.data;
        } catch (e) {
            error.value = "Erreur lors de la mise à jour du produit";
            throw e;
        } finally {
            loading.value = false;
        }
    };

    const deleteStock = async (id) => {
        loading.value = true;
        error.value = null;
        try {
            await stockService.delete(id);
            stocks.value = stocks.value.filter(s => s.id !== id);
        } catch (e) {
            error.value = "Erreur lors de la suppression du produit";
            throw e;
        } finally {
            loading.value = false;
        }
    };

    return {
        stocks,
        loading,
        error,
        fetchStocks,
        createStock,
        updateStock,
        deleteStock
    };
}