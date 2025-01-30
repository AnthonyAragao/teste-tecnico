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

public class MotoRepository implements IMotoRepository
{
    public List<Moto> findAll() 
    {
        List<Moto> motos = new ArrayList<>();
        String query = "SELECT * FROM motos "
            + "INNER JOIN veiculos ON motos.veiculo_id = veiculos.id "
            + "ORDER BY veiculos.ano DESC";

        try(
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);
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
    public Moto findById(int id) 
    {   
        Moto moto = null;
        String sql =  "SELECT * FROM motos "
            + "INNER JOIN veiculos ON motos.veiculo_id = veiculos.id "
            + "WHERE motos.id = ?";

        try(
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
        ){
            statement.setInt(1, id);

            try(ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    moto = mapResultSetToMoto(result);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error fetching moto with ID: " + id, exception);
        }

        return moto;
    }

    @Override
    public void insert(Moto moto) 
    {
        String veiculoSql = "INSERT INTO veiculos (tipo, modelo, fabricante, ano, preco) VALUES (?, ?, ?, ?, ?)";
        String motoSql    = "INSERT INTO motos (veiculo_id, cilindrada) VALUES (?, ?)";

        try(
            Connection conn = DatabaseConfig.getConnection();
            PreparedStatement veiculoStmt = conn.prepareStatement(veiculoSql, PreparedStatement.RETURN_GENERATED_KEYS);
            PreparedStatement motoStmt = conn.prepareStatement(motoSql);
        ){
            veiculoStmt.setString(1, moto.getTipo());
            veiculoStmt.setString(2, moto.getModelo());
            veiculoStmt.setString(3, moto.getFabricante());
            veiculoStmt.setInt(4, moto.getAno());
            veiculoStmt.setDouble(5, moto.getPreco());
            veiculoStmt.executeUpdate();

            try(ResultSet generatedKeys = veiculoStmt.getGeneratedKeys()){
                if (generatedKeys.next()) {
                    motoStmt.setInt(1, generatedKeys.getInt(1));
                    motoStmt.setInt(2, moto.getCilindrada());
                    motoStmt.executeUpdate();
                }
            }

        } catch (SQLException exception) {
            throw new RuntimeException("Error inserting moto", exception);
        }
    }

    @Override
    public void update(Moto veiculo) 
    {}

    @Override
    public void delete(Moto veiculo) 
    {}

    private Moto mapResultSetToMoto(ResultSet resultSet) throws SQLException 
    {
        return new Moto(
            resultSet.getInt("id"),
            resultSet.getString("tipo"),
            resultSet.getString("modelo"),
            resultSet.getString("fabricante"),
            resultSet.getInt("ano"),
            resultSet.getDouble("preco"),
            resultSet.getInt("cilindrada")
        );
    }
}
