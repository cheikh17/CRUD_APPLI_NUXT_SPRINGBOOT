<template>
  <div class="px-4 py-6">
    <h2 class="text-2xl font-bold mb-6">Gestion du Stock</h2>

    <div v-if="loading" class="text-center py-4">
      <p class="text-gray-500">Chargement des produits...</p>
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
          @click="openStockModal()"
          class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600"
        >
          Ajouter un produit
        </button>
      </div>

      <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="min-w-full divide-y divide-gray-200">
          <thead>
            <tr class="bg-gray-50">
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Nom du produit
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Description
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Quantité
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Prix
              </th>
              <th
                class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase"
              >
                Actions
              </th>
            </tr>
          </thead>
          <tbody class="bg-white divide-y divide-gray-200">
            <tr v-for="item in stocks" :key="item.id">
              <td class="px-6 py-4 whitespace-nowrap">{{ item.nomProduit }}</td>
              <td class="px-6 py-4 whitespace-nowrap">
                {{ item.description }}
              </td>
              <td class="px-6 py-4 whitespace-nowrap">{{ item.quantite }}</td>
              <td class="px-6 py-4 whitespace-nowrap">{{ item.prix }}FCFA</td>
              <td class="px-6 py-4 whitespace-nowrap">
                <button
                  @click="openStockModal(item)"
                  class="text-blue-600 hover:text-blue-800 mr-2"
                >
                  Modifier
                </button>
                <button
                  @click="handleDelete(item)"
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
            {{ selectedStock ? "Modifier" : "Ajouter" }} un produit
          </h3>
          <form @submit.prevent="handleSubmit">
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Nom du produit</label
              >
              <input
                v-model="formData.nomProduit"
                type="text"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Description</label
              >
              <textarea
                v-model="formData.description"
                required
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              ></textarea>
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Quantité</label
              >
              <input
                v-model.number="formData.quantite"
                type="number"
                required
                min="0"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
              />
            </div>
            <div class="mb-4">
              <label class="block text-sm font-medium text-gray-700"
                >Prix</label
              >
              <input
                v-model.number="formData.prix"
                type="number"
                required
                min="0"
                step="0.01"
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
                {{ selectedStock ? "Modifier" : "Ajouter" }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useStock } from "~/composables/useStock";

const showModal = ref(false);
const selectedStock = ref(null);
const {
  stocks,
  loading,
  error,
  fetchStocks,
  createStock,
  updateStock,
  deleteStock,
} = useStock();

const formData = ref({
  nomProduit: "",
  description: "",
  quantite: 0,
  prix: 0,
});

const resetForm = () => {
  formData.value = {
    nomProduit: "",
    description: "",
    quantite: 0,
    prix: 0,
  };
};

const openStockModal = (stock) => {
  if (stock) {
    selectedStock.value = stock;
    formData.value = { ...stock };
  } else {
    selectedStock.value = null;
    resetForm();
  }
  showModal.value = true;
};

const handleSubmit = async () => {
  try {
    if (selectedStock.value) {
      await updateStock(selectedStock.value.id, formData.value);
    } else {
      await createStock(formData.value);
    }
    showModal.value = false;
    resetForm();
    await fetchStocks();
  } catch (e) {
    console.error("Error submitting form:", e);
  }
};

const handleDelete = async (stock) => {
  if (confirm("Êtes-vous sûr de vouloir supprimer ce produit ?")) {
    try {
      await deleteStock(stock.id);
    } catch (e) {
      console.error("Error deleting stock:", e);
    }
  }
};

onMounted(() => {
  fetchStocks();
});
</script>
