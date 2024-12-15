<template>
  <div>
    <h2 class="text-2xl font-bold mb-6">Bienvenue dans votre application</h2>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <!-- Carte Gestion du Stock -->
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h3 class="text-xl font-bold mb-4">Gestion du Stock</h3>
        <p class="mb-4">Gérez facilement votre inventaire et vos produits.</p>
        <NuxtLink
            to="/stocks"
            class="inline-block bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Accéder au stock
        </NuxtLink>
      </div>

      <!-- Carte Gestion des Employés -->
      <div class="bg-white p-6 rounded-lg shadow-md">
        <h3 class="text-xl font-bold mb-4">Gestion des Employés</h3>
        <p class="mb-4">Gérez les informations de vos employés.</p>
        <NuxtLink
            to="/employes"
            class="inline-block bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Gérer les employés
        </NuxtLink>
      </div>
    </div>
  </div>
</template>