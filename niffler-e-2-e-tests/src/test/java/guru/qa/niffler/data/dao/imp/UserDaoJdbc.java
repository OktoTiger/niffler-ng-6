package guru.qa.niffler.data.dao.imp;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.data.Databases;
import guru.qa.niffler.data.dao.UserDao;
import guru.qa.niffler.data.entity.spend.CategoryEntity;
import guru.qa.niffler.data.entity.spend.SpendEntity;
import guru.qa.niffler.data.entity.spend.UserEntity;
import guru.qa.niffler.model.CurrencyValues;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDaoJdbc implements UserDao {
    private static final Config CFG = Config.getInstance();

    @Override
    public UserEntity createUser(UserEntity user) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO userdata (username, currency, firstname, surname, fullname) " +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                ps.setString(1, user.getUsername());
                ps.setObject(2, user.getCurrency());
                ps.setString(3, user.getFirstname());
                ps.setString(4, user.getSurname());
                ps.setString(5, user.getFullname());

                ps.executeUpdate();
                final UUID generatedKey;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedKey = rs.getObject("id", UUID.class);
                    } else {
                        throw new SQLException("Can not find id in ResultSet");
                    }
                }
                user.setId(generatedKey);
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM userdata WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                ps.setObject(1,id);
                ps.execute();
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        UserEntity ue = new UserEntity();
                        ue.setId(rs.getObject("id", UUID.class));
                        ue.setUsername(rs.getString("username"));
                        ue.setCurrency(rs.getObject("currency", CurrencyValues.class));
                        ue.setFirstname(rs.getString("firstname"));
                        ue.setSurname(rs.getString("surname"));
                        ue.setFullname(rs.getString("fullname"));
                        return Optional.of(ue);
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try(Connection connection = Databases.connection(CFG.spendJdbcUrl())) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM userdata WHERE username = ?",
                    Statement.RETURN_GENERATED_KEYS
            )){
                ps.setObject(1,username);
                ps.execute();
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        UserEntity ue = new UserEntity();
                        ue.setId(rs.getObject("id", UUID.class));
                        ue.setUsername(rs.getString("username"));
                        ue.setCurrency(rs.getObject("currency", CurrencyValues.class));
                        ue.setFirstname(rs.getString("firstname"));
                        ue.setSurname(rs.getString("surname"));
                        ue.setFullname(rs.getString("fullname"));
                        return Optional.of(ue);
                    } else {
                        return Optional.empty();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UserEntity user) {
        try (Connection connection = Databases.connection(CFG.userdataJdbcUrl())) {
            try(PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM userdata WHERE user = ?",
                    Statement.RETURN_GENERATED_KEYS)){
                ps.setObject(1,user);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
