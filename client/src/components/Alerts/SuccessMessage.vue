<script setup>
    import { defineProps } from 'vue';

    const props = defineProps({
        message: String
    });
</script>

<template>
    <div 
        v-if="message" 
        class="px-4 py-2 mb-4 text-green-700 bg-green-100 border border-green-400 rounded"
    >
        {{ message }}
    </div>
</template>