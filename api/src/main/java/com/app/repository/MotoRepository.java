package com.app.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.app.model.Moto;
import com.app.config.DatabaseConfig;
import com.app.repository.interfaces.IMotoRepository;

public class MotoRepository implements IMotoRepository {
    private static final String SELECT_ALL_MOTOS_QUERY = "SELECT * FROM motos "
            + "INNER JOIN veiculos ON motos.veiculo_id = veiculos.id "
            + "ORDER BY veiculos.ano DESC";

    private static final String SELECT_MOTO_BY_ID_QUERY = "SELECT * FROM motos "
            + "INNER JOIN veiculos ON motos.veiculo_id = veiculos.id "
            + "WHERE motos.id = ?";

    private static final String INSERT_VEICULO_QUERY = "INSERT INTO veiculos (tipo, modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_MOTO_QUERY = "INSERT INTO motos (veiculo_id, cilindrada) VALUES (?, ?)";

    private static final String UPDATE_VEICULO_QUERY = "UPDATE veiculos SET modelo = ?, fabricante = ?, ano = ?, preco = ? WHERE id = ?";

    private static final String UPDATE_MOTO_QUERY = "UPDATE motos SET cilindrada = ? WHERE id = ?";

    private static final String DELETE_MOTO_QUERY = "DELETE FROM motos WHERE id = ?";

    private static final String DELETE_VEICULO_QUERY = "DELETE FROM veiculos WHERE id = ?";

    private static final String SELECT_VEICULO_ID_BY_MOTO_ID_QUERY = "SELECT veiculo_id FROM motos WHERE id = ?";

    public List<Moto> findAll() {
        List<Moto> motos = new ArrayList<>();

        try(
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_ALL_MOTOS_QUERY);
            ResultSet result = statement.executeQuery();
        ){
            while (result.next()) {
                motos.add(mapResultSetToMoto(result));
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }

        return motos;
    }
   
    @Override
    public Moto findById(int id) {   
        Moto moto = null;

        try(
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(SELECT_MOTO_BY_ID_QUERY);
        ){
            statement.setInt(1, id);

            try(ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    moto = mapResultSetToMoto(result);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao buscar moto com ID: " + id, exception);
        }

        return moto;
    }

    @Override
    public void insert(Moto moto) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false); 
    
            try (
                PreparedStatement veiculoStmt = conn.prepareStatement(INSERT_VEICULO_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement motoStmt = conn.prepareStatement(INSERT_MOTO_QUERY)
            ) {
                // Inserção na tabela 'veiculos'
                veiculoStmt.setString(1, moto.getTipo());
                veiculoStmt.setString(2, moto.getModelo());
                veiculoStmt.setString(3, moto.getFabricante());
                veiculoStmt.setInt(4, moto.getAno());
                veiculoStmt.setDouble(5, moto.getPreco());
                veiculoStmt.executeUpdate();
    
                // Recuperar a chave gerada
                try (ResultSet generatedKeys = veiculoStmt.getGeneratedKeys()) {
                    if (generatedKeys == null || !generatedKeys.next()) {
                        throw new RuntimeException("Erro ao inserir veículo: ID não gerado.");
                    }
    
                    int veiculoId = generatedKeys.getInt(1);
    
                    motoStmt.setInt(1, veiculoId);
                    motoStmt.setInt(2, moto.getCilindrada());
                    motoStmt.executeUpdate();
                }
    
                conn.commit();
            } catch (SQLException exception) {
                conn.rollback(); 
                throw new RuntimeException("Erro ao inserir moto", exception);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao inserir moto", exception);
        }
    }

    @Override
    public void update(Moto moto) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false); 

            try (
                PreparedStatement veiculoStmt = conn.prepareStatement(UPDATE_VEICULO_QUERY);
                PreparedStatement motoStmt = conn.prepareStatement(UPDATE_MOTO_QUERY)
            ) {
                veiculoStmt.setString(1, moto.getModelo());
                veiculoStmt.setString(2, moto.getFabricante());
                veiculoStmt.setInt(3, moto.getAno());
                veiculoStmt.setDouble(4, moto.getPreco());
                veiculoStmt.setInt(5, moto.getVeiculoId());
                veiculoStmt.executeUpdate();

                motoStmt.setInt(1, moto.getCilindrada());
                motoStmt.setInt(2, moto.getId());
                motoStmt.executeUpdate();

                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                throw new RuntimeException("Erro ao atualizar moto", exception);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao atualizar moto", exception);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);

            int veiculoId = getVeiculoIdByMotoId(conn, id);

            try (
                PreparedStatement stmtMoto = conn.prepareStatement(DELETE_MOTO_QUERY);
                PreparedStatement stmtVeiculo = conn.prepareStatement(DELETE_VEICULO_QUERY)
            ) {
                // Deletando moto
                stmtMoto.setInt(1, id);
                stmtMoto.executeUpdate();

                // Deletando veiculo
                stmtVeiculo.setInt(1, veiculoId);
                stmtVeiculo.executeUpdate();

                conn.commit();
            } catch (SQLException exception) {
                conn.rollback();
                throw new RuntimeException("Erro ao deletar moto", exception);
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Erro ao deletar moto", exception);
        }
    }

    private int getVeiculoIdByMotoId(Connection conn, int motoId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_VEICULO_ID_BY_MOTO_ID_QUERY)) {
            stmt.setInt(1, motoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("veiculo_id");
                }
                throw new RuntimeException("Moto não encontrada com ID: " + motoId);
            }
        }
    }

    private Moto mapResultSetToMoto(ResultSet resultSet) throws SQLException {
        return new Moto(
            resultSet.getInt("motos.id"),
            resultSet.getString("tipo"),
            resultSet.getString("modelo"),
            resultSet.getString("fabricante"),
            resultSet.getInt("ano"),
            resultSet.getDouble("preco"),
            resultSet.getInt("cilindrada"),
            resultSet.getInt("veiculo_id")
        );
    }
}
