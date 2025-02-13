<script setup>
    import { ref, onMounted } from 'vue';
    import { useRouter, useRoute } from 'vue-router';
    import Layout from '@/components/Layout.vue';
    import api from '@/services/axios.js';

    const router = useRouter();
    const route = useRoute();

    const form = ref({
        tipo: 'MOTO',
        modelo: '',
        fabricante: '',
        ano: '',
        preco: '',
        cilindrada: ''
    });

    onMounted(() => {
        const id = route.params.id;
        if (id) {
            fetchMoto(id);
        }
    });

    const fetchMoto = async (id) => {
        try {
            const response = await api.get(`/motos/${id}`);
            form.value = response.data;
        } catch (error) {
            console.error(error);
        }
    };

    const handleSubmit = async () => {
        try {
            let successMessage = '';
            if (route.params.id) {
                await api.put(`/motos/${route.params.id}`, form.value);
                successMessage = 'Moto atualizada com sucesso!';
            } else {
                await api.post('/motos', form.value);
                successMessage = 'Moto cadastrada com sucesso!';
            }

            form.value = {
                tipo: 'MOTO',
                modelo: '',
                fabricante: '',
                ano: '',
                preco: '',
                cilindrada: ''
            };

            router.push({ path: '/motos', query: { success: successMessage } });
        } catch (error) {
            console.error(error);
        }
    };
</script>

<template>
    <Layout>
        <div class="w-full max-w-2xl mx-auto mb-4">
            <form class="space-y-4" @submit.prevent="handleSubmit">
                <div>
                    <label for="modelo" class="block text-gray-700 text-sm font-bold mb-2">
                        Modelo: <span class="text-red-500">*</span>
                    </label>
                    <input v-model="form.modelo" type="text" id="modelo" name="modelo" placeholder="Insira o modelo da moto"
                        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                </div>

                <div>
                    <label for="fabricante" class="block text-gray-700 text-sm font-bold mb-2">
                        Fabricante: <span class="text-red-500">*</span>
                    </label>
                    <input v-model="form.fabricante" type="text" id="fabricante" name="fabricante" placeholder="Insira o fabricante da moto"
                        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                </div>

                <div>
                    <label for="ano" class="block text-gray-700 text-sm font-bold mb-2">
                        Ano: <span class="text-red-500">*</span>
                    </label>
                    <input v-model="form.ano" type="text" id="ano" name="ano" placeholder="Insira o ano da moto"
                        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                </div>

                <div>
                    <label for="preco" class="block text-gray-700 text-sm font-bold mb-2">
                        Preço: <span class="text-red-500">*</span>
                    </label>
                    <input v-model="form.preco" type="text" id="preco" name="preco" placeholder="Insira o preço da moto"
                        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                </div>

                <div>
                    <label for="cilindrada" class="block text-gray-700 text-sm font-bold mb-2">
                        Cilindrada: <span class="text-red-500">*</span>
                    </label>
                    <input v-model="form.cilindrada" type="text" id="cilindrada" name="cilindrada" placeholder="Insira a cilindrada da moto"
                        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                </div>

                <button
                    class="bg-blue-500 hover:bg-blue-700 text-white text-xs font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline cursor-pointer"
                    type="submit"
                >
                    <i class="fas fa-save"></i> Salvar
                </button>
            </form>
        </div>
    </Layout>
</template>
