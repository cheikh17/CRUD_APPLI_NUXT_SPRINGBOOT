import { ref } from 'vue';
import { employeService } from '../services/employeService';

export function useEmployees() {
    const employees = ref([]);
    const loading = ref(false);
    const error = ref(null);

    const fetchEmployees = async () => {
        loading.value = true;
        error.value = null;
        try {
            const response = await employeService.getAll();
            employees.value = response.data;
        } catch (e) {
            error.value = "Erreur lors du chargement des employés";
            console.error(e);
        } finally {
            loading.value = false;
        }
    };

    const createEmployee = async (employee) => {
        loading.value = true;
        error.value = null;
        try {
            const response = await employeService.create(employee);
            employees.value.push(response.data);
            return response.data;
        } catch (e) {
            error.value = "Erreur lors de la création de l'employé";
            throw e;
        } finally {
            loading.value = false;
        }
    };

    const updateEmployee = async (id, employee) => {
        loading.value = true;
        error.value = null;
        try {
            const response = await employeService.update(id, employee);
            const index = employees.value.findIndex(e => e.id === id);
            if (index !== -1) {
                employees.value[index] = response.data;
            }
            return response.data;
        } catch (e) {
            error.value = "Erreur lors de la mise à jour de l'employé";
            throw e;
        } finally {
            loading.value = false;
        }
    };

    const deleteEmployee = async (id) => {
        loading.value = true;
        error.value = null;
        try {
            await employeService.delete(id);
            employees.value = employees.value.filter(e => e.id !== id);
        } catch (e) {
            error.value = "Erreur lors de la suppression de l'employé";
            throw e;
        } finally {
            loading.value = false;
        }
    };

    return {
        employees,
        loading,
        error,
        fetchEmployees,
        createEmployee,
        updateEmployee,
        deleteEmployee
    };
}