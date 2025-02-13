<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
    isOpen: Boolean,
    itemName: String
});

const emit = defineEmits(['close', 'confirm']);
</script>

<template>
    <div
        v-if="isOpen"
        class="fixed inset-0 bg-gray-700/50 bg-opacity-50 z-50 flex items-center justify-center"
        @click="emit('close')"
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
            <h2 class="text-xl font-bold p-4 text-gray-700 text-center">
                Tem certeza que deseja excluir {{ itemName }}?
            </h2>
            <p class="text-sm font-bold text-center text-gray-500 px-2">
                Essa ação não poderá ser desfeita.
            </p>
            <div class="mt-6 flex gap-2">
                <button
                    type="button"
                    class="cursor-pointer rounded-full shadow-lg px-5 py-2 text-sm font-medium tracking-wider text-gray-300 border transition ease-in duration-300 bg-gray-600 border-gray-600 hover:bg-transparent hover:text-black"
                    @click="emit('close')"
                >
                    Cancelar
                </button>
                <button
                    type="button"
                    class="cursor-pointer bg-red-500 hover:bg-transparent px-5 py-2 text-sm shadow-sm hover:shadow-lg font-medium tracking-wider border border-red-500 hover:border-red-500 text-white hover:text-red-500 rounded-full transition ease-in duration-300"
                    @click="emit('confirm')"
                >
                    Confirmar
                </button>
            </div>
        </div>
    </div>
</template>
