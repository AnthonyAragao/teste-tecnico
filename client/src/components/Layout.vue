<script setup>
    import NavBar from '../Components/Navigation/NavBar.vue'
</script>

<template>
    <header class="bg-[#FFB80C] text-gray-800 p-4 text-center font-bold uppercase tracking-wider">
        Gerenciamento de frota
    </header>

    <NavBar />

    <div class="container max-w-5xl mx-auto py-8">
        <slot></slot>
    </div>
</template>

<style>
    body {
        background-color: #f3f4f6;
    }
</style>