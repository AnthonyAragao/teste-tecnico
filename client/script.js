const API_MOTO_URL = "http://localhost:8080/motos"; 
const API_CAR_URL = "http://localhost:8080/carros"; 


// Função para carregar motos
async function loadMotos() {
    const response = await fetch(API_MOTO_URL);
    const motos = await response.json();
    const motoList = document.getElementById("moto-list");
    motoList.innerHTML = "";
    motos.forEach(moto => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `${moto.make} ${moto.model} (${moto.year}) 
            <button onclick="deleteMoto(${moto.id})">Excluir</button>
            <button onclick="editMoto(${moto.id}, '${moto.make}', '${moto.model}', ${moto.year})">Editar</button>`;
        motoList.appendChild(listItem);
    });
}

// Função para carregar carros
async function loadCars() {
    const response = await fetch(API_CAR_URL);
    const cars = await response.json();
    const carList = document.getElementById("car-list");
    carList.innerHTML = "";
    cars.forEach(car => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `${car.make} ${car.model} (${car.year}) 
            <button onclick="deleteCar(${car.id})">Excluir</button>
            <button onclick="editCar(${car.id}, '${car.make}', '${car.model}', ${car.year})">Editar</button>`;
        carList.appendChild(listItem);
    });
}

// Função para adicionar uma moto
async function addMoto(moto) {
    const response = await fetch(API_MOTO_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(moto),
    });
    return await response.json();
}

// Função para adicionar um carro
async function addCar(car) {
    const response = await fetch(API_CAR_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(car),
    });
    return await response.json();
}

// Função para editar uma moto
async function updateMoto(id, moto) {
    const response = await fetch(`${API_MOTO_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(moto),
    });
    return await response.json();
}

// Função para editar um carro
async function updateCar(id, car) {
    const response = await fetch(`${API_CAR_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(car),
    });
    return await response.json();
}

// Função para excluir uma moto
async function deleteMoto(id) {
    await fetch(`${API_MOTO_URL}/${id}`, { method: "DELETE" });
    loadMotos(); // Recarregar a lista
}

// Função para excluir um carro
async function deleteCar(id) {
    await fetch(`${API_CAR_URL}/${id}`, { method: "DELETE" });
    loadCars(); // Recarregar a lista
}

// Função para preencher o formulário de edição de moto
function editMoto(id, make, model, year) {
    document.getElementById("moto-form-title").textContent = "Editar Moto";
    document.getElementById("moto-make").value = make;
    document.getElementById("moto-model").value = model;
    document.getElementById("moto-year").value = year;

    const form = document.getElementById("moto-form");
    form.onsubmit = async function (e) {
        e.preventDefault();
        const updatedMoto = {
            make: document.getElementById("moto-make").value,
            model: document.getElementById("moto-model").value,
            year: document.getElementById("moto-year").value,
        };
        await updateMoto(id, updatedMoto);
        loadMotos(); // Recarregar a lista após a edição
        resetForm("moto");
    };
}

// Função para preencher o formulário de edição de carro
function editCar(id, make, model, year) {
    document.getElementById("car-form-title").textContent = "Editar Carro";
    document.getElementById("car-make").value = make;
    document.getElementById("car-model").value = model;
    document.getElementById("car-year").value = year;

    const form = document.getElementById("car-form");
    form.onsubmit = async function (e) {
        e.preventDefault();
        const updatedCar = {
            make: document.getElementById("car-make").value,
            model: document.getElementById("car-model").value,
            year: document.getElementById("car-year").value,
        };
        await updateCar(id, updatedCar);
        loadCars(); // Recarregar a lista após a edição
        resetForm("car");
    };
}

// Função para resetar o formulário de moto
function resetForm(type) {
    if (type === "moto") {
        document.getElementById("moto-form").reset();
        document.getElementById("moto-form-title").textContent = "Adicionar Moto";
        const form = document.getElementById("moto-form");
        form.onsubmit = async function (e) {
            e.preventDefault();
            const newMoto = {
                make: document.getElementById("moto-make").value,
                model: document.getElementById("moto-model").value,
                year: document.getElementById("moto-year").value,
            };
            await addMoto(newMoto);
            loadMotos(); // Recarregar a lista após adicionar
            resetForm("moto");
        };
    } else {
        document.getElementById("car-form").reset();
        document.getElementById("car-form-title").textContent = "Adicionar Carro";
        const form = document.getElementById("car-form");
        form.onsubmit = async function (e) {
            e.preventDefault();
            const newCar = {
                make: document.getElementById("car-make").value,
                model: document.getElementById("car-model").value,
                year: document.getElementById("car-year").value,
            };
            await addCar(newCar);
            loadCars(); // Recarregar a lista após adicionar
            resetForm("car");
        };
    }
}

// Inicialização das listas
document.getElementById("moto-form").onsubmit = async function (e) {
    e.preventDefault();
    const newMoto = {
        make: document.getElementById("moto-make").value,
        model: document.getElementById("moto-model").value,
        year: document.getElementById("moto-year").value,
    };
    await addMoto(newMoto);
    loadMotos(); // Recarregar a lista após adicionar
    resetForm("moto");
};

document.getElementById("car-form").onsubmit = async function (e) {
    e.preventDefault();
    const newCar = {
        make: document.getElementById("car-make").value,
        model: document.getElementById("car-model").value,
        year: document.getElementById("car-year").value,
    };
    await addCar(newCar);
    loadCars(); // Recarregar a lista após adicionar
    resetForm("car");
};

// Carregar as motos e carros ao iniciar
loadMotos();
loadCars();
