<script  setup>
    import { computed } from 'vue';

    const { isHeader, customClass} = defineProps(['isHeader', 'customClass']);

    const classes = computed(() =>
        `${isHeader
            ? 'px-6 py-4 text-gray-800 font-bold whitespace-nowrap'
            : 'px-6 py-4 text-gray-800'
        } ${customClass}`
    );
</script>

<template>
    <td :class="classes">
        <slot></slot>
    </td>
</template>


