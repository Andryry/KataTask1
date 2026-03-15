package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {  String createTableSQL = """
                CREATE TABLE IF NOT EXISTS studentes (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(100) NOT NULL,
                    surname VARCHAR(100) NOT NULL,
                    age TINYINT
                )
                """;
        try (PreparedStatement preparedStatement = Util.getNewConnection().prepareStatement(createTableSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void dropUsersTable()  {
        String dropTableSQL = "DROP TABLE IF EXISTS studentes";
        try (PreparedStatement preparedStatement = Util.getNewConnection().prepareStatement(dropTableSQL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age)  {
        String saveNewUser = "INSERT INTO studentes (name,surname,age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = Util.getNewConnection().prepareStatement(saveNewUser)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id)  {
        String removeUserByIdNow = ("DELETE from studentes where id = ?");
        try (PreparedStatement preparedStatement = Util.getNewConnection().prepareStatement(removeUserByIdNow)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String getAllUsersNow = "select * from studentes";
        try (PreparedStatement preparedStatement = Util.getNewConnection().prepareStatement(getAllUsersNow);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("surname"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE studentes";
        try (PreparedStatement preparedStatement = Util.getNewConnection().prepareStatement(cleanTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
