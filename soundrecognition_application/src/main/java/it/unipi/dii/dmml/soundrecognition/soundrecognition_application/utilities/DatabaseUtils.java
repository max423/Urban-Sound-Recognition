package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config.ParameterConfig;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities.User;


import weka.core.Instances;
import weka.experiment.InstanceQuery;
import java.io.File;
import java.sql.*;


public class DatabaseUtils {

    static Connection connection = null;

    private static void openJDBCConnection() {
        if (connection == null) {
            try {
                String dbParams = ParameterConfig.getIp() + ":" + ParameterConfig.getPort();
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" + dbParams + "/" + ParameterConfig.getDbname() + "+?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=CET",
                        "root", "massimo99");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeJDBCConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException ignored) {
            }
        }
    }

    public static boolean addUserToDB(User user) {
        openJDBCConnection();
        if (connection == null)
            return false;
        String insertStmt = "INSERT INTO user (fullName, email, username,password)" +
                " VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertStmt)) {
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            closeJDBCConnection();
            return false;
        }
        closeJDBCConnection();
        return true;

    }

    public static User getUser(String username) {

        openJDBCConnection();

        User selectedUser = null;

        String queryStmt = "SELECT * FROM user WHERE username = ?";

        ResultSet rs = null;

        try (PreparedStatement pstmt = connection.prepareStatement(queryStmt)) {

            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String fullName = rs.getString("fullName");
                String password = rs.getString("password");
                String email = rs.getString("email");

                selectedUser = new User(fullName, email, password, username);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {
                }
            }
            closeJDBCConnection();
        }

        return selectedUser;

    }
}
