<script setup>
    import { ref, onMounted } from 'vue';
    import { useRouter, useRoute } from 'vue-router';
    import Layout from '@/components/Layout.vue';
    import InputField from '@/components/Ui/InputField.vue';
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
                <InputField v-model="form.modelo" id="modelo" label="Modelo" placeholder="Insira o modelo da moto" required />
                <InputField v-model="form.fabricante" id="fabricante" label="Fabricante" placeholder="Insira o fabricante da moto" required />
                <InputField v-model="form.ano" id="ano" label="Ano" type="number" placeholder="Insira o ano da moto" required />
                <InputField v-model="form.preco" id="preco" label="Preço" type="number" placeholder="Insira o preço da moto" required step="0.01" min="0.01" />
                <InputField v-model="form.cilindrada" id="cilindrada" label="Cilindrada" type="number" placeholder="Insira a cilindrada da moto" required />

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
