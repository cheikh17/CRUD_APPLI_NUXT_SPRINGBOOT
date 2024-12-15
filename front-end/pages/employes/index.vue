<template>
  <div class="px-4 py-6">
    <h2 class="text-2xl font-bold mb-6">Gestion des Employés</h2>

    <div v-if="loading" class="text-center py-4">
      <p class="text-gray-500">Chargement des employés...</p>
    </div>

    <div
      v-else-if="error"
      class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4"
    >
      {{ error }}
    </div>

    <div v-else>
      <div class="mb-4">
        <button
          @click="openEmployeeModal()"
          class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
        >
          Ajouter un employé
        </button>
      </div>

      <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
          <thead>
            <tr class="bg-gray-50">
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Nom
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Prénom
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Email
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Téléphone
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Département
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Actions
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="employee in employees" :key="employee.id">
              <td class="px-6 py-4 whitespace-nowrap">
                {{ employee.employeSurname }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                {{ employee.employeName }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                {{ employee.employeEmail }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                {{ employee.employePhone }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                {{ employee.employeDepartment }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">
                <button
                  @click="openEmployeeModal(employee)"
                  class="text-blue-600 hover:text-blue-800 mr-2"
                >
                  Modifier
                </button>
                <button
                  @click="handleDelete(employee)"
                  class="text-red-600 hover:text-red-800"
                >
                  Supprimer
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div
      v-if="showModal"
      class="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full"
    >
      <div
        class="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white"
      >
        <div class="mt-3">
          <h3 class="text-lg font-medium leading-6 text-gray-900 mb-4">
            {{ selectedEmployee ? "Modifier" : "Ajouter" }} un employé
          </h3>
          <form @submit.prevent="handleSubmit">
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Prénom</label
              >
              <input
                v-model="formData.employeName"
                type="text"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700">Nom</label>
              <input
                v-model="formData.employeSurname"
                type="text"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Email</label
              >
              <input
                v-model="formData.employeEmail"
                type="email"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Téléphone</label
              >
              <input
                v-model="formData.employePhone"
                type="number"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Département</label
              >
              <input
                v-model="formData.employeDepartment"
                type="text"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Mot de passe</label
              >
              <input
                v-model="formData.employePassword"
                type="password"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="flex justify-end gap-3">
              <button
                type="button"
                @click="showModal = false"
                class="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300"
              >
                Annuler
              </button>
              <button
                type="submit"
                class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
              >
                {{ selectedEmployee ? "Modifier" : "Ajouter" }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useEmployees } from "~/composables/useEmployees";

const showModal = ref(false);
const selectedEmployee = ref(null);
const {
  employees,
  loading,
  error,
  fetchEmployees,
  createEmployee,
  updateEmployee,
  deleteEmployee,
} = useEmployees();

const formData = ref({
  employeName: "",
  employeSurname: "",
  employeEmail: "",
  employePhone: 0,
  employePassword: "",
  employeDepartment: "",
});

const resetForm = () => {
  formData.value = {
    employeName: "",
    employeSurname: "",
    employeEmail: "",
    employePhone: 0,
    employePassword: "",
    employeDepartment: "",
  };
};

const openEmployeeModal = (employee) => {
  if (employee) {
    selectedEmployee.value = employee;
    formData.value = { ...employee };
  } else {
    selectedEmployee.value = null;
    resetForm();
  }
  showModal.value = true;
};

const handleSubmit = async () => {
  try {
    if (selectedEmployee.value) {
      await updateEmployee(selectedEmployee.value.id, formData.value);
    } else {
      await createEmployee(formData.value);
    }
    showModal.value = false;
    resetForm();
    await fetchEmployees();
  } catch (e) {
    console.error("Error submitting form:", e);
  }
};

const handleDelete = async (employee) => {
  if (confirm("Êtes-vous sûr de vouloir supprimer cet employé ?")) {
    try {
      await deleteEmployee(employee.id);
    } catch (e) {
      console.error("Error deleting employee:", e);
    }
  }
};

onMounted(() => {
  fetchEmployees();
});
</script>
