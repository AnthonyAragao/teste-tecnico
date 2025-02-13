<script setup>
    import { ref } from 'vue';
    import TableTemplate from '@/components/Tables/TableTemplate.vue';
    import TableCell from '@/components/Tables/TableCell.vue';
    import DeleteModal from '@/components/Modals/DeleteModal.vue';
    import api from '@/services/axios.js';

    const { headers, motos } = defineProps(['headers', 'motos']);
    const emit               = defineEmits(['deleteMoto']);

    const isModalOpen = ref(false);
    const idToDelete  = ref(null);

    const handleDelete = (id) => {
        idToDelete.value  = id;
        isModalOpen.value = true;
    };

    const handleConfirm = async () => {
        try {
            await api.delete(`/motos/${idToDelete.value}`);
            isModalOpen.value = false;
            emit('deleteMoto', idToDelete.value);
        } catch (error) {
            console.error("Erro ao excluir Moto:", error);
        }
    };
</script>

<template>
    <TableTemplate :headers="headers">
        <tr v-for="moto in motos" :key="moto.id" class="border-b border-gray-200 hover:bg-gray-100">
            <TableCell isHeader="true">{{ moto.modelo }}</TableCell>
            <TableCell>{{ moto.fabricante }}</TableCell>
            <TableCell>{{ moto.ano }}</TableCell>
            <TableCell>{{ moto.preco }}</TableCell>
            <TableCell>{{ moto.cilindrada }}</TableCell>
            <TableCell customClass="space-x-2">
                <RouterLink
                    :to="`/motos/${moto.id}/edit`"
                    class="bg-yellow-500 hover:bg-yellow-600 text-white text-xs font-bold py-1 px-2 rounded"
                >
                    <i class="fas fa-edit"></i> Editar
                </RouterLink>
                <button 
                    @click="handleDelete(moto.id)"
                    class="bg-red-500 hover:bg-red-700 text-white text-xs font-bold py-1 px-2 rounded cursor-pointer"
                >
                    <i class="fas fa-trash"></i> Excluir
                </button>
            </TableCell>
        </tr>
    </TableTemplate>

    <DeleteModal 
        :isOpen="isModalOpen" 
        itemName="esta moto" 
        @close="isModalOpen = false" 
        @confirm="handleConfirm" 
    />
</template>
