CREATE TABLE veiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('CARRO', 'MOTO')),
    modelo VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE carros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantidade_portas INT NOT NULL,
    tipo_combustivel VARCHAR(20) NOT NULL CHECK (tipo_combustivel IN ('GASOLINA', 'ETANOL', 'DIESEL', 'FLEX')),
    veiculo_id INT,
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)
);

CREATE TABLE AUTO_INCREMENT motos (
    id INT PRIMARY KEY,
    cilindrada INT NOT NULL,
    veiculo_id INT,
    FOREIGN KEY (veiculo_id) REFERENCES veiculos(id)
);