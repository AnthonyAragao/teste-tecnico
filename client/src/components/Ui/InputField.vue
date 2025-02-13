<script setup>
    import { defineProps, defineEmits, computed } from 'vue';

    const props = defineProps({
        modelValue: [String, Number],
        label: String,
        type: { type: String, default: 'text' },
        placeholder: String,
        id: String,
        required: { type: Boolean, default: false },
        step: { type: String, default: '1' }
    });

    const emit = defineEmits(['update:modelValue']);

    const inputType = computed(() => (
        props.type === 'number' ? 'number' : 'text'
    ));

    const handleInput = (event) => {
        let value = event.target.value;

        if (props.type === 'number' && props.step === '1') {
            // Para campos de número inteiro (ex: ano)
            value = value.replace(/\D/g, ''); // Remove tudo que não for número
            if (value !== '' && parseInt(value) < 0) value = ''; // Bloqueia negativos
        } else if (props.type === 'number' && props.step === '0.01') {
            // Para valores decimais (ex: preço)
            value = value.replace(/[^0-9.]/g, ''); // Permite apenas números e ponto
            const parts = value.split('.');
            if (parts.length > 2) {
                value = parts[0] + '.' + parts.slice(1).join('');
            }
        }

        emit('update:modelValue', value);
    };
</script>

<template>
    <div>
        <label :for="id" class="block text-gray-700 text-sm font-bold mb-2">
            {{ label }} <span v-if="required" class="text-red-500">*</span>
        </label>
        <input 
            :id="id"
            :type="inputType"
            :placeholder="placeholder"
            :value="modelValue"
            :required="required"
            :step="step"
            @input="handleInput"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
        />
    </div>
</template>
