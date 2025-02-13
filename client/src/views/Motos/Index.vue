<script setup>
    import { onMounted, ref } from 'vue'
    import { useRoute } from 'vue-router'
    import Layout from '@/components/Layout.vue'
    import MotosTable from '@/components/Tables/MotosTable.vue'
    import api from '@/services/axios.js'

    const route = useRoute()
    const successMessage = ref(route.query.success || '')

    const headers = ['Modelo', 'Fabricante', 'Ano', 'Preço', 'Cilindrada', 'Ações']
    const motos = ref([])

    const fetchMotos = async () => {
        try {
            const { data } = await api.get('/motos')
            motos.value = data
        } catch (error) {
            console.error("Erro ao buscar motos:", error)
        }
    }

    onMounted(() => {
        fetchMotos()
        if (successMessage.value) {
            setTimeout(() => {
                successMessage.value = ''
            }, 3000)
        }
    })
</script>

<template>
    <Layout>
        <div 
            v-if="successMessage" 
            class="px-4 py-2 mb-4 text-green-700 bg-green-100 border border-green-400 rounded"
        >
            {{ successMessage }}
        </div>

        <div class="flex justify-between items-center">
            <h2 class="text-2xl font-semibold text-gray-800">Motos</h2>

            <RouterLink
                to="/motos/create"
                class="bg-blue-500 hover:bg-blue-600 text-white text-xs font-bold py-2 px-4 rounded"
            >
                <i class="fas fa-plus"></i> Adicionar moto
            </RouterLink>
        </div>

        <MotosTable 
            :headers="headers" 
            :motos="motos" 
        />
    </Layout>
</template>

