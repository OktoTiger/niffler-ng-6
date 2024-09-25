package guru.qa.niffler.data.dao.imp;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.SpendDao;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.model.CurrencyValues;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpendDaoJdbc implements SpendDao {
    private static final Config CFG = Config.getInstance();

    @Override
    public SpendEntity create(SpendEntity spend) {
        try (Connection connection = Databases.connection(CFG.spendJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO spend (username, spend_date, currency, amount, description, category_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                ps.setString(1, spend.getUsername());
                ps.setObject(2, spend.getSpendDate());
                ps.setString(3, spend.getCurrency().name());
                ps.setDouble(4, spend.getAmount());
                ps.setString(5, spend.getDescription());
                ps.setObject(6, spend.getCategory());

                ps.executeUpdate();
                final UUID generatedKey;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedKey = rs.getObject("id", UUID.class);
                    } else {
                        throw new SQLException("Can not find id in ResultSet");
                    }
                }
                spend.setId(generatedKey);
                return spend;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        try (Connection connection = Databases.connection(CFG.spendJdbcUrl())) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM category WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS)){
                ps.setObject(1,id);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SpendEntity> findSpendById(UUID id) {
        try (Connection connection = Databases.connection(CFG.spendJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM spend WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                ps.setObject(1,id);
                ps.execute();
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        SpendEntity se = new SpendEntity();
                        se.setId(rs.getObject("id", UUID.class));
                        se.setUsername(rs.getString("username"));
                        se.setCurrency(rs.getObject("currency", CurrencyValues.class));
                        se.setSpendDate(rs.getDate("spendDate"));
                        se.setAmount(rs.getDouble("amount"));
                        se.setDescription(rs.getString("description"));
                        se.setCategory(rs.getObject("category", CategoryEntity.class));

                        return Optional.of(se);
                    } else {
                        Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        try(Connection connection = Databases.connection(CFG.spendJdbcUrl())) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM category WHERE username = ?",
                    Statement.RETURN_GENERATED_KEYS
            )){
               ps.setObject(1,username);
               ps.execute();
               try(ResultSet rs = ps.getResultSet()){
                   List<SpendEntity> spends = new ArrayList<>();
                   if(rs.next()){
                       SpendEntity se = new SpendEntity();
                       se.setId(rs.getObject("id", UUID.class));
                       se.setUsername(rs.getString("username"));
                       se.setCurrency(rs.getObject("currency", CurrencyValues.class));
                       se.setSpendDate(rs.getDate("spendDate"));
                       se.setAmount(rs.getDouble("amount"));
                       se.setDescription(rs.getString("description"));
                       se.setCategory(rs.getObject("category", CategoryEntity.class));
                       spends.add(se);
                   }
                   return spends;
               }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
