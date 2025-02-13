<script setup>
    const { headers } = defineProps(['headers']);
</script>

<template>
    <table class="w-full text-sm text-left text-gray-500 mt-8">
        <thead class="uppercase text-xs text-gray-900 border-t border-b border-gray-200 bg-gray-200">
            <tr>
                <th
                    v-for="header in headers"
                    :key="header"
                    scope="col"
                    class="px-6 py-3"
                >
                    {{ header }}
                </th>
            </tr>
        </thead>

        <tbody>
            <slot></slot>
        </tbody>
    </table>
</template>
