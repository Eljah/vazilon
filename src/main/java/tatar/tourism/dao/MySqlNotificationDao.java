package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 05.11.2015.
 */
public class MySqlNotificationDao extends MySqlDao implements NotificationDao {
    @Override
    public void create(Notification notification) {
        PreparedStatement stmt = null;
        Connection con = getConnection();

        try {
            stmt = con.prepareStatement("INSERT INTO notification " +
                    "(user_id, text)" +
                    "VALUES( ?,?)");
            stmt.setInt(1, notification.getAddressee().getDatabaseId());
            stmt.setString(2, notification.getText());
            stmt.execute();
            //log.trace("Addition to notes by user " + note.getUser().getUsername());
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error("Addition of new comment failed " + e.getLocalizedMessage());
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Notification read(int databaseId) {
        return null;
    }

    @Override
    public List<Notification> readAllForUser(User user) {
        String sql = "SELECT * FROM notification WHERE user_id = ?";
        List<Notification> list = new ArrayList<Notification>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, user.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            Notification s;
            while (rs.next()) {
                s = new Notification();
                s.setDatabaseId(rs.getInt("id"));
                s.setText(rs.getString("text"));
                s.setAddressee(user);
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stm.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void delete(Notification notification) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("DELETE FROM notification WHERE id =  ? AND user_id=?");
            stmt.setInt(1, notification.getDatabaseId());
            stmt.setInt(2, notification.getAddressee().getDatabaseId());
            stmt.execute();
            //log.trace("Addition to notes by user " + note.getUser().getUsername());
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error("Addition of new comment failed " + e.getLocalizedMessage());
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteAllForUser(User user) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("DELETE FROM notification WHERE user_id=?");
            stmt.setInt(1, user.getDatabaseId());
            stmt.execute();
            //log.trace("Addition to notes by user " + note.getUser().getUsername());
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error("Addition of new comment failed " + e.getLocalizedMessage());
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
