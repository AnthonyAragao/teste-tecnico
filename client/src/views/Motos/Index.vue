<script setup>
    import Layout from '../../components/Layout.vue'
    
</script>

<template>
    <Layout>
        <div class="flex justify-between items-center">
            <h2 class="text-2xl font-semibold text-gray-800">Motos</h2>

            <RouterLink
                to="/motos/create"
                class="bg-blue-500 hover:bg-blue-700 text-white text-xs font-bold py-2 px-4 rounded"
            >
                <i class="fas fa-plus"></i> Adicionar moto
            </RouterLink>
        </div>
    </Layout>
</template>

