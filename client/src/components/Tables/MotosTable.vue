<script setup>
    import TableTemplate from '@/components/Tables/TableTemplate.vue'
    import TableCell from '@/components/Tables/TableCell.vue'

    const { headers , motos } = defineProps(['headers', 'motos'])
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
                <button class="bg-red-500 hover:bg-red-700 text-white text-xs font-bold py-1 px-2 rounded">
                    Excluir
                </button>
            </TableCell>
        </tr>
    </TableTemplate>
</template>