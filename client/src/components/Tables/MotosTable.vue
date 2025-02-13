<script setup>
    import { ref } from 'vue';
    import TableTemplate from '@/components/Tables/TableTemplate.vue'
    import TableCell from '@/components/Tables/TableCell.vue'
    import api from '@/services/axios.js'

    const { headers , motos } = defineProps(['headers', 'motos'])
    const emit = defineEmits(['deleteMoto'])
    const isModalOpen = ref(false)
    const idToDelete = ref(null)

    const handleDelete = (id) => {
        idToDelete.value = id
        isModalOpen.value = true
    }

    const handleConfirm = async () => {
        try {
            await api.delete(`/motos/${idToDelete.value}`)
            isModalOpen.value = false
            emit('deleteMoto', idToDelete.value)
        } catch (error) {
            console.error("Erro ao excluir moto:", error)
        }
    }
</script>

<template>
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
                    :to="`/motos/${moto.id}/edit`"
                    class="bg-yellow-500 hover:bg-yellow-600 text-white text-xs font-bold py-1 px-2 rounded"
                >
                    Editar
                </RouterLink>
                <button 
                    @click="handleDelete(moto.id)"
                    class="bg-red-500 hover:bg-red-700 text-white text-xs font-bold py-1 px-2 rounded cursor-pointer"
                >
                    Excluir
                </button>
            </TableCell>
        </tr>
    </TableTemplate>


    <!-- Modal delete -->
    <div
        v-if="isModalOpen"
        class="fixed inset-0 bg-gray-700/50 bg-opacity-50 z-50 flex items-center justify-center"
        @click="isModalOpen = false"
    >
        <div
            class="group w-[300px] py-10 rounded-2xl border shadow-md flex flex-col items-center bg-gray-100 border-gray-200"
            @click.stop
        >
            <svg
                fill="currentColor"
                viewBox="0 0 20 20"
                class="animate-bounce w-12 h-12 flex items-center text-gray-600 fill-red-500 mx-auto"
                xmlns="http://www.w3.org/2000/svg"
            >
                <path
                    clipRule="evenodd"
                    d="M9 2a1 1 0 00-.894.553L7.382 4H4a1 1 0 000 2v10a2 2 0 002 2h8a2 2 0 002-2V6a1 1 0 100-2h-3.382l-.724-1.447A1 1 0 0011 2H9zM7 8a1 1 0 012 0v6a1 1 0 11-2 0V8zm5-1a1 1 0 00-1 1v6a1 1 0 102 0V8a1 1 0 00-1-1z"
                    fillRule="evenodd"
                ></path>
            </svg>
            <h2 class="text-xl font-bold py-4 text-gray-700 text-center">
                Tem certeza que deseja excluir?
            </h2>
            <p class="text-sm font-bold text-center text-gray-500 px-2">
                Essa ação não poderá ser desfeita.
            </p>
            <div class="mt-6 flex gap-2">
                <button
                    type="button"
                    class="cursor-pointer rounded-full shadow-lg px-5 py-2 text-sm font-medium tracking-wider text-gray-300 border transition ease-in duration-300 bg-gray-600 border-gray-600 hover:bg-transparent hover:text-black"
                    @click="isModalOpen = false"
                >
                    Cancel
                </button>
                <button
                    type="button"
                    class="cursor-pointer bg-red-500 hover:bg-transparent px-5 py-2 text-sm shadow-sm hover:shadow-lg font-medium tracking-wider border border-red-500 hover:border-red-500 text-white hover:text-red-500 rounded-full transition ease-in duration-300"
                    @click="handleConfirm"
                >
                    Confirm
                </button>
            </div>
        </div>
    </div>
</template>