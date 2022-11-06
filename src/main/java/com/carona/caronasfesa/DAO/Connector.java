package com.carona.caronasfesa.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private static Connection conn;

    public static Connection getInstance() throws SQLException {
        if (conn == null) {
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\database.db";

            conn = DriverManager.getConnection(url);
        }

        return conn;
    }
}
