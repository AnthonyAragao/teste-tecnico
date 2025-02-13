package com.app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.app.model.Carro;
import com.app.model.TipoCombustivel;
import com.app.config.DatabaseConfig;
import com.app.repository.interfaces.ICarroRepository;

public class CarroRepository implements ICarroRepository {
    private static final String SELECT_ALL_CARROS_QUERY = "SELECT * FROM carros "
            + "INNER JOIN veiculos ON carros.veiculo_id = veiculos.id "
            + "ORDER BY veiculos.ano DESC";

    private static final String SELECT_CARRO_BY_ID_QUERY = "SELECT * FROM carros "
            + "INNER JOIN veiculos ON carros.veiculo_id = veiculos.id "
            + "WHERE carros.id = ?";

    private static final String INSERT_VEICULO_QUERY = "INSERT INTO veiculos (tipo, modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_CARRO_QUERY = "INSERT INTO carros (veiculo_id, quantidade_portas, tipo_combustivel) VALUES (?, ?, ?)";

    private static final String UPDATE_VEICULO_QUERY = "UPDATE veiculos SET modelo = ?, fabricante = ?, ano = ?, preco = ? WHERE id = ?";

    private static final String UPDATE_CARRO_QUERY = "UPDATE carros SET quantidade_portas = ?, tipo_combustivel = ? WHERE veiculo_id = ?";

    private static final String DELETE_CARRO_QUERY = "DELETE FROM carros WHERE id = ?";

    private static final String DELETE_VEICULO_QUERY = "DELETE FROM veiculos WHERE id = ?";

    private static final String SELECT_VEICULO_ID_BY_CARRO_ID_QUERY = "SELECT veiculo_id FROM carros WHERE id = ?";

    @Override
    public List<Carro> findAll() {
        List<Carro> carros = new ArrayList<>();
       
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_ALL_CARROS_QUERY);
             ResultSet result = statement.executeQuery()) {
             
            while (result.next()) {
                carros.add(mapResultSetToCarro(result));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar todos os carros", exception);
        }

        return carros;
    }

    @Override
    public Carro findById(int id) {
        Carro carro = null;
       
        try (
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_CARRO_BY_ID_QUERY)
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
    public void insert(Carro carro) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
           
            try (
                PreparedStatement veiculoStmt = conn.prepareStatement(INSERT_VEICULO_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement carroStmt = conn.prepareStatement(INSERT_CARRO_QUERY)
            ) {
                veiculoStmt.setString(1, carro.getTipo());
                veiculoStmt.setString(2, carro.getModelo());
                veiculoStmt.setString(3, carro.getFabricante());
                veiculoStmt.setInt(4, carro.getAno());
                veiculoStmt.setDouble(5, carro.getPreco());
                veiculoStmt.executeUpdate();

                // Pegando o ID gerado para o veiculo
                try (ResultSet generatedKeys = veiculoStmt.getGeneratedKeys()) {
                    if (!generatedKeys.next()) {
                        throw new RuntimeException("Erro ao gerar o ID do veículo");
                    }

                    int veiculoId = generatedKeys.getInt(1);

                    carroStmt.setInt(1, veiculoId);
                    carroStmt.setInt(2, carro.getQuantidadePortas());
                    carroStmt.setString(3, carro.getTipoCombustivel().name());
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
    public void update(Carro carro) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement veiculoStmt = conn.prepareStatement(UPDATE_VEICULO_QUERY);
                PreparedStatement carroStmt = conn.prepareStatement(UPDATE_CARRO_QUERY)
            ) {
                // Atualizando veiculo
                veiculoStmt.setString(1, carro.getModelo());
                veiculoStmt.setString(2, carro.getFabricante());
                veiculoStmt.setInt(3, carro.getAno());
                veiculoStmt.setDouble(4, carro.getPreco());
                veiculoStmt.setInt(5, carro.getVeiculoId());
                veiculoStmt.executeUpdate();

                // Atualizando carro
                carroStmt.setInt(1, carro.getQuantidadePortas());
                carroStmt.setString(2, carro.getTipoCombustivel().name());
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
    public void delete(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            int veiculoId = getVeiculoIdByCarroId(conn, id);
           
            try (
                PreparedStatement stmtCarro = conn.prepareStatement(DELETE_CARRO_QUERY);
                PreparedStatement stmtVeiculo = conn.prepareStatement(DELETE_VEICULO_QUERY)
            ) {
                // Deletando carro
                stmtCarro.setInt(1, id);
                stmtCarro.executeUpdate();

                // Deletando veiculo
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

    private int getVeiculoIdByCarroId(Connection conn, int carroId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_VEICULO_ID_BY_CARRO_ID_QUERY)) {
            stmt.setInt(1, carroId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("veiculo_id");
                } 
                throw new RuntimeException("Carro não encontrado com ID: " + carroId);
            }
        }
    }

    private Carro mapResultSetToCarro(ResultSet resultSet) throws SQLException {
        return new Carro(
            resultSet.getInt("carros.id"),
            resultSet.getString("tipo"),
            resultSet.getString("modelo"),
            resultSet.getString("fabricante"),
            resultSet.getInt("ano"),
            resultSet.getDouble("preco"),
            resultSet.getInt("quantidade_portas"),
            TipoCombustivel.valueOf(resultSet.getString("tipo_combustivel").toUpperCase()),
            resultSet.getInt("veiculo_id")
        );
    }
}
