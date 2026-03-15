package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl user = new UserServiceImpl();
        user.createUsersTable();
        user.saveUser("Andry", "Kapla", (byte) 28);
        user.saveUser("Ivan", "Porosh", (byte) 24);
        user.saveUser("July", "Elfima", (byte) 24);
        user.saveUser("Agafy", "Kaplanajana", (byte) 24);
        System.out.println(user.getAllUsers());
//user.cleanUsersTable();
//user.dropUsersTable();
    }

}
