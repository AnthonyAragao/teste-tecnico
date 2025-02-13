<script setup>
    import { onMounted, ref } from 'vue'
    import Layout from '@/components/Layout.vue'
    import TableTemplate from '@/components/Tables/TableTemplate.vue'
    import TableCell from '@/components/Tables/TableCell.vue'
    import api from '@/services/axios.js'

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

    onMounted(fetchMotos)
</script>

<template>
    <Layout>
        <div class="flex justify-between items-center">
            <h2 class="text-2xl font-semibold text-gray-800">Motos</h2>

            <RouterLink
                to="/"
                class="bg-blue-500 hover:bg-blue-600 text-white text-xs font-bold py-2 px-4 rounded"
            >
                <i class="fas fa-plus"></i> Adicionar moto
            </RouterLink>
        </div>


        <TableTemplate :headers=headers>
            <tr
                v-for="moto in motos"
                :key="moto.id"
                class="border-b border-gray-200 hover:bg-gray-100"
            >
                <TableCell isHeader="true">{{ moto.modelo }}</TableCell>
                <TableCell>{{ moto.fabricante }}</TableCell>
                <TableCell>{{ moto.ano }}</TableCell>
                <TableCell>{{ moto.preco }}</TableCell>
                <TableCell>{{ moto.cilindrada }}</TableCell>
                <TableCell customClass="space-x-2">
                    <RouterLink
                        to="/"
                        class="bg-yellow-500 hover:bg-yellow-600 text-white text-xs font-bold py-1 px-2 rounded"
                    >
                        Editar
                    </RouterLink>
                    <button class="bg-red-500 hover:bg-red-700 text-white text-xs font-bold py-1 px-2 rounded">
                        Excluir
                    </button>
                </TableCell>
            </tr>
        </TableTemplate>
    </Layout>
</template>

