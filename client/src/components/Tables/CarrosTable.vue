<script setup>
    import { ref } from 'vue';
    import TableTemplate from '@/components/Tables/TableTemplate.vue';
    import TableCell from '@/components/Tables/TableCell.vue';
    import DeleteModal from '@/components/Modals/DeleteModal.vue';
    import api from '@/services/axios.js';

    const { headers, carros } = defineProps(['headers', 'carros']);
    const emit                = defineEmits(['deleteCarro']);

    const isModalOpen = ref(false);
    const idToDelete  = ref(null);

    const handleDelete = (id) => {
        idToDelete.value  = id;
        isModalOpen.value = true;
    };

    const handleConfirm = async () => {
        try {
            await api.delete(`/carros/${idToDelete.value}`);
            isModalOpen.value = false;
            emit('deleteCarro', idToDelete.value);
        } catch (error) {
            console.error("Erro ao excluir Carro:", error);
        }
    };
</script>

<template>
    <TableTemplate :headers="headers">
        <tr v-for="carro in carros" :key="carro.id" class="border-b border-gray-200 hover:bg-gray-100">
            <TableCell isHeader="true">{{ carro.modelo }}</TableCell>
            <TableCell>{{ carro.fabricante }}</TableCell>
            <TableCell>{{ carro.ano }}</TableCell>
            <TableCell>{{ carro.preco }}</TableCell>
            <TableCell>{{ carro.quantidadePortas }}</TableCell>
            <TableCell>{{ carro.tipoCombustivel }}</TableCell>
            <TableCell customClass="space-x-2">
                <RouterLink
                    :to="`/carros/${carro.id}/edit`"
                    class="bg-yellow-500 hover:bg-yellow-600 text-white text-xs font-bold py-1 px-2 rounded"
                >
                    <i class="fas fa-edit"></i> Editar
                </RouterLink>
                <button 
                    @click="handleDelete(carro.id)"
                    class="bg-red-500 hover:bg-red-700 text-white text-xs font-bold py-1 px-2 rounded cursor-pointer"
                >
                    <i class="fas fa-trash"></i> Excluir
                </button>
            </TableCell>
        </tr>
    </TableTemplate>

    <DeleteModal 
        :isOpen="isModalOpen" 
        itemName="este carro" 
        @close="isModalOpen = false" 
        @confirm="handleConfirm" 
    />
</template>
