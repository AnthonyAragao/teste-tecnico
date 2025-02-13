<script setup>
    import { onMounted, ref } from 'vue'
    import { useRoute } from 'vue-router'
    import Layout from '@/components/Layout.vue'
    import api from '@/services/axios.js'
    import CarrosTable from '@/components/Tables/CarrosTable.vue'
    import SuccessMessage from '@/components/Alerts/SuccessMessage.vue'

    const route = useRoute()
    const successMessage = ref(route.query.success || '')

    const headers = ['Modelo', 'Fabricante', 'Ano', 'Preço', 'Quantidade de Portas', 'Tipo de Combustível', 'Ações']
    const carros = ref([])

    const fetchCarros = async () => {
        try {
            const { data } = await api.get('/carros')
            carros.value = data
        } catch (error) {
            console.error("Erro ao buscar carros:", error)
        }
    }

    onMounted(() => {
        fetchCarros()
        if (successMessage.value) {
            setTimeout(() => {
                successMessage.value = ''
            }, 3000)
        }
    })

    const deleteCarro = () => {
        successMessage.value = 'Carro excluído com sucesso!'
        fetchCarros()
        setTimeout(() => {
            successMessage.value = ''
        }, 3000)
    }
</script>

<template>
    <Layout>
        <SuccessMessage :message="successMessage" />
       
        <div class="flex justify-between items-center">
            <h2 class="text-2xl font-semibold text-gray-800">Carros</h2>

            <RouterLink
                to="/carros/create"
                class="bg-blue-500 hover:bg-blue-600 text-white text-xs font-bold py-2 px-4 rounded"
            >
                <i class="fas fa-plus"></i> Adicionar carro
            </RouterLink>
        </div>

        <CarrosTable 
            :headers="headers" 
            :carros="carros" 
            @deleteCarro="deleteCarro"
        />
    </Layout>
</template>

