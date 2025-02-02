package com.app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.app.model.Carro;
import com.app.config.DatabaseConfig;
import com.app.repository.interfaces.ICarroRepository;

public class CarroRepository implements ICarroRepository
{
    public List<Carro> findAll() 
    {
        List<Carro> carros = new ArrayList<>();
        String query = "SELECT * FROM carros "
                     + "INNER JOIN veiculos ON carros.veiculo_id = veiculos.id "
                     + "ORDER BY veiculos.ano DESC";

        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
        ) {
            while (result.next()) {
                carros.add(mapResultSetToCarro(result));
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return carros;
    }
   
    @Override
    public Carro findById(int id) 
    {
        Carro carro = null;
        String sql = "SELECT * FROM carros "
                   + "INNER JOIN veiculos ON carros.veiculo_id = veiculos.id "
                   + "WHERE carros.id = ?";

        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
        ) {
            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    carro = mapResultSetToCarro(result);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar carro com ID: " + id, exception);
        }

        return carro;
    }

    @Override
    public void insert(Carro carro) 
    {
        String veiculoSql = "INSERT INTO veiculos (tipo, modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?, ?)";
        String carroSql = "INSERT INTO carros (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (?, ?, ?)";
    
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
    
            try (
                PreparedStatement veiculoStmt = conn.prepareStatement(veiculoSql, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement carroStmt = conn.prepareStatement(carroSql)
            ) {
                veiculoStmt.setString(1, carro.getTipo());
                veiculoStmt.setString(2, carro.getModelo());
                veiculoStmt.setString(3, carro.getFabricante());
                veiculoStmt.setInt(4, carro.getAno());
                veiculoStmt.setDouble(5, carro.getPreco());
                veiculoStmt.executeUpdate();
    
                try (ResultSet generatedKeys = veiculoStmt.getGeneratedKeys()) {
                    if (generatedKeys == null || !generatedKeys.next()) {
                        throw new RuntimeException("Erro ao inserir veículo: ID não gerado.");
                    }
    
                    int veiculoId = generatedKeys.getInt(1);
                    carroStmt.setInt(1, veiculoId);
                    carroStmt.setInt(2, carro.getQuantidadePortas());
                    carroStmt.setString(3, carro.getTipoCombustivel());
                    carroStmt.executeUpdate();
                }
    
                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                throw new RuntimeException("Erro ao inserir carro", exception);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir carro", exception);
        }
    }

    @Override
    public void update(Carro carro) 
    {
        String veiculoSql = "UPDATE veiculos SET modelo = ?, fabricante = ?, ano = ?, preco = ? WHERE id = ?";
        String carroSql = "UPDATE carros SET quantidade_portas = ?, tipo_combustivel = ? WHERE veiculo_id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement veiculoStmt = conn.prepareStatement(veiculoSql);
                PreparedStatement carroStmt = conn.prepareStatement(carroSql)
            ) {
                veiculoStmt.setString(1, carro.getModelo());
                veiculoStmt.setString(2, carro.getFabricante());
                veiculoStmt.setInt(3, carro.getAno());
                veiculoStmt.setDouble(4, carro.getPreco());
                veiculoStmt.setInt(5, carro.getId());
                veiculoStmt.executeUpdate();

                carroStmt.setInt(1, carro.getQuantidadePortas());
                carroStmt.setString(2, carro.getTipoCombustivel());
                carroStmt.setInt(3, carro.getId());
                carroStmt.executeUpdate();

                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                throw new RuntimeException("Erro ao atualizar carro", exception);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar carro", exception);
        }
    }

    @Override
    public void delete(int id) 
    {
        String sqlDeleteCarro = "DELETE FROM carros WHERE id = ?";
        String sqlDeleteVeiculo = "DELETE FROM veiculos WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement stmtCarro = conn.prepareStatement(sqlDeleteCarro);
                PreparedStatement stmtVeiculo = conn.prepareStatement(sqlDeleteVeiculo)
            ) {
                int veiculoId;
                try (PreparedStatement stmtGetVeiculoId = conn.prepareStatement("SELECT veiculo_id FROM carros WHERE id = ?")) {
                    stmtGetVeiculoId.setInt(1, id);
                    try (ResultSet rs = stmtGetVeiculoId.executeQuery()) {
                        if (rs.next()) {
                            veiculoId = rs.getInt("veiculo_id");
                        } else {
                            throw new RuntimeException("Carro não encontrado com ID: " + id);
                        }
                    }
                }
                stmtCarro.setInt(1, id);
                stmtCarro.executeUpdate();

                stmtVeiculo.setInt(1, veiculoId);
                stmtVeiculo.executeUpdate();

                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                throw new RuntimeException("Erro ao deletar carro", exception);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar carro", exception);
        }
    }

    private Carro mapResultSetToCarro(ResultSet resultSet) throws SQLException 
    {
        Carro carro = new Carro(
            resultSet.getString("tipo"),
            resultSet.getString("modelo"),
            resultSet.getString("fabricante"),
            resultSet.getInt("ano"),
            resultSet.getDouble("preco"),
            resultSet.getInt("quantidade_portas"),
            resultSet.getString("tipo_combustivel")
        );
        carro.setId(resultSet.getInt("veiculo_id"));
        return carro;
    }
}
