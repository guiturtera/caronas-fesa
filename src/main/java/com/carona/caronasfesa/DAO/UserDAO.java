package com.carona.caronasfesa.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.carona.caronasfesa.models.Course;
import com.carona.caronasfesa.models.User;

public class UserDAO implements GenericDAO<User> {

    private static final String INSERT_SQL = "INSERT INTO [User] VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_SQL = "UPDATE [User] SET" +
            "name = ?" +
            "description = ?" +
            "student_id = ?" +
            "course = ?" +
            "phone_number = ?" +
            "password = ?" +
            "WHERE id = ?";

    private static final String DELETE_SQL = "DELETE FROM [User] WHERE id = ?";
    private static final String SELECT_SQL = "SELECT * FROM [User] WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM [User]";

    @Override
    public void insert(User e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(INSERT_SQL);

            ps.setInt(1, e.getId());
            ps.setString(2, e.getName());
            ps.setString(3, e.getDescription());
            ps.setString(4, e.getStudentId());
            ps.setInt(5, e.getCourse().getValue());
            ps.setString(6, e.getPhoneNumber());
            ps.setString(7, e.getPassword());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void update(User e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {

            conn = Connector.getInstance();
            ps = conn.prepareStatement(UPDATE_SQL);

            ps.setString(1, e.getName());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getStudentId());
            ps.setInt(4, e.getCourse().getValue());
            ps.setString(5, e.getPhoneNumber());
            ps.setString(6, e.getPassword());
            ps.setInt(7, e.getId());

            ps.executeUpdate();

        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }

    }

    @Override
    public void remove(User e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(DELETE_SQL);

            ps.setInt(1, e.getId());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public User readById(User e) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Connector.getInstance();
            ps = conn.prepareStatement(SELECT_SQL);

            ps.setInt(1, e.getId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return ConvertToUser(rs);
            }

            return null;
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<User> readAll() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        List<User> users = new ArrayList<User>();

        try {
            conn = Connector.getInstance();

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);

            while (rs.next()) {
                users.add(ConvertToUser(rs));
            }

            return users;
        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    private User ConvertToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("Id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("student_id"),
                Course.fromInteger(rs.getInt("course")),
                rs.getString("phone_number"),
                rs.getString("password"));
    }
}
