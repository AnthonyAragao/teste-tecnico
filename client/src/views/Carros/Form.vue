<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Layout from '@/components/Layout.vue';
import InputField from '@/components/Ui/InputField.vue';
import api from '@/services/axios.js';

const router = useRouter();
const route = useRoute();

const form = ref({
    tipo: 'CARRO',
    modelo: '',
    fabricante: '',
    ano: '',
    preco: '',
    quantidadePortas: '',
    tipoCombustivel: ''
});

const tiposCombustivel = ['GASOLINA', 'ETANOL', 'DIESEL', 'FLEX'];

onMounted(() => {
    const id = route.params.id;
    if (id) {
        fetchCarro(id);
    }
});

const fetchCarro = async (id) => {
    try {
        const response = await api.get(`/carros/${id}`);
        form.value = response.data;
    } catch (error) {
        console.error(error);
    }
};

const handleSubmit = async () => {
    try {
        let successMessage = '';
        if (route.params.id) {
            await api.put(`/carros/${route.params.id}`, form.value);
            successMessage = 'carro atualizado com sucesso!';
        } else {
            await api.post('/carros', form.value);
            successMessage = 'carro cadastrado com sucesso!';
        }

        form.value = {
            tipo: 'CARRO',
            modelo: '',
            fabricante: '',
            ano: '',
            preco: '',
            quantidadePortas: '',
            tipoCombustivel: ''
        };

        router.push({ path: '/carros', query: { success: successMessage } });
    } catch (error) {
        console.error(error);
    }
};
</script>

<template>
    <Layout>
        <div class="w-full max-w-2xl mx-auto mb-4">
            <form class="space-y-4" @submit.prevent="handleSubmit">
                <InputField v-model="form.modelo" id="modelo" label="Modelo" placeholder="Insira o modelo do carro" required />
                <InputField v-model="form.fabricante" id="fabricante" label="Fabricante" placeholder="Insira o fabricante do carro" required />
                <InputField v-model="form.ano" id="ano" label="Ano" type="number" placeholder="Insira o ano do carro" required />
                <InputField v-model="form.preco" id="preco" label="Preço" type="number" placeholder="Insira o preço do carro" required step="0.01" min="0.01" />
                <InputField v-model="form.quantidadePortas" id="quantidadePortas" label="Quantidade de Portas" type="number" placeholder="Insira a quantidade de portas" required />

                <div>
                    <label for="tipoCombustivel" class="block text-gray-700 text-sm font-bold mb-2">
                        Tipo de Combustível <span class="text-red-500">*</span>
                    </label>
                    <select
                        id="tipoCombustivel"
                        v-model="form.tipoCombustivel"
                        required
                        class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                    >
                        <option value="" disabled>Selecione o tipo de combustível</option>
                        <option 
                            v-for="tipo in tiposCombustivel" 
                            :key="tipo" 
                            :value="tipo"
                        >
                            {{ tipo }}
                        </option>
                    </select>
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
