<template>
    <nav>
        <ul class="flex justify-center space-x-8 p-6 ">
            <RouterLink
                to="/motos"
                class="font-semibold text-sm text-black uppercase"
            >
                <i class="fas fa-motorcycle"></i> Motos
            </RouterLink>

            <RouterLink
                to="/carros"
                class="font-semibold text-sm text-black uppercase"
             
            >
                <i class="fas fa-car"></i> Carros
            </RouterLink>
        </ul>
    </nav>
</template>
