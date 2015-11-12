package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 14.10.2015.
 */
public class MySqlUserDao extends MySqlDao implements UserDao {

    @Override
    public void create(User user) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("INSERT INTO users "
                    + "(username, password, email, role, firstname, lastname,locale,active,confirmed)"
                    + "VALUES( ?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getFirstname());
            stmt.setString(6, user.getLastname());
            stmt.setString(7, user.getLocale());
            stmt.setBoolean(8, user.isActive());
            stmt.setBoolean(9, user.isConfirmed());

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public User read(int key) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User s = null;
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, key);
            ResultSet rs = stm.executeQuery();
            rs.next();
            UserTypes e = UserTypes.valueOf(rs.getString("role"));
            switch (e) {
                case ADMIN:
                    s = new Admin();
                    break;
                case GUIDE:
                    s = new Guide();
                    break;
                case SIGHTSEER:
                    s = new Sightseer();
                    break;
                case BUSDRIVER:
                    s = new BusDriver();
                    break;
                case TRAVELAGENCY:
                    s = new BusDriver();
                    break;
                //default:
                //    s = new User();
                //    break;
            }
            ;

            s.setDatabaseId(rs.getInt("id"));
            s.setUsername(rs.getString("username"));
            s.setPassword(rs.getString("password"));
            s.setEmail(rs.getString("email"));
            s.setFirstname(rs.getString("firstname"));
            s.setLastname(rs.getString("lastname"));
            s.setRole(rs.getString("role"));
            s.setLocale(rs.getString("locale"));
            s.setActive(rs.getBoolean("active"));
            s.setConfirmed(rs.getBoolean("confirmed"));
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

        return s;
    }

    @Override
    public void update(User user) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("UPDATE users SET username =  ?, password=?, email=?, role=?, firstname=?, lastname=?, locale=?, active=?, confirmed=? " +
                    "WHERE id =  ?");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getFirstname());
            stmt.setString(6, user.getLastname());
            stmt.setString(7, user.getLocale());
            stmt.setBoolean(8, user.isActive());
            stmt.setBoolean(9, user.isConfirmed());
            stmt.setInt(10, user.getDatabaseId());

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
    public void updateVote(User user, AverageCountPair vote) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users SET vote =  ?, vote_count=? " +
                    " WHERE id =  ?");
            stmt.setFloat(1, vote.getAverage());
            stmt.setInt(2, vote.getCount());
            stmt.setInt(3, user.getDatabaseId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(User user) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("DELETE FROM users WHERE id =  ?");
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

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        List<User> list = new ArrayList<User>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            User s=null;
            while (rs.next()
                    ) {
                UserTypes e = UserTypes.valueOf(rs.getString("role"));
                switch (e) {
                    case ADMIN:
                        s = new Admin();
                        break;
                    case GUIDE:
                        s = new Guide();
                        break;
                    case SIGHTSEER:
                        s = new Sightseer();
                        break;
                    case BUSDRIVER:
                        s = new BusDriver();
                        break;
                    case TRAVELAGENCY:
                        s = new BusDriver();
                        break;
                //    default:
                //        s = new User();
                //        break;
                }
                ;

                s.setDatabaseId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setEmail(rs.getString("email"));
                s.setFirstname(rs.getString("firstname"));
                s.setLastname(rs.getString("lastname"));
                s.setRole(rs.getString("role"));
                s.setLocale(rs.getString("locale"));
                s.setActive(rs.getBoolean("active"));
                s.setConfirmed(rs.getBoolean("confirmed"));
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
    public User read(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        User s = null;
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                UserTypes e = UserTypes.valueOf(rs.getString("role"));
                switch (e) {
                    case ADMIN:
                        s = new Admin();
                        break;
                    case GUIDE:
                        s = new Guide();
                        break;
                    case SIGHTSEER:
                        s = new Sightseer();
                        break;
                    case BUSDRIVER:
                        s = new BusDriver();
                        break;
                    case TRAVELAGENCY:
                        s = new BusDriver();
                        break;
                    //default:
                    //    s = new User();
                    //    break;
                }
                ;

                s.setDatabaseId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setFirstname(rs.getString("firstname"));
                s.setLastname(rs.getString("lastname"));
                s.setPassword(rs.getString("password"));
                s.setRole(rs.getString("role"));
                s.setLocale(rs.getString("locale"));
                s.setActive(rs.getBoolean("active"));
                s.setConfirmed(rs.getBoolean("confirmed"));
            } else {
                s = null;
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

        return s;
    }

    @Override
    public User read(String login) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User s = null;
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                UserTypes e = UserTypes.valueOf(rs.getString("role"));
                switch (e) {
                    case ADMIN:
                        s = new Admin();
                        break;
                    case GUIDE:
                        s = new Guide();
                        break;
                    case SIGHTSEER:
                        s = new Sightseer();
                        break;
                    case BUSDRIVER:
                        s = new BusDriver();
                        break;
                    case TRAVELAGENCY:
                        s = new Sightseer();
                        break;
                    //default:
                    //    s = new User();
                    //    break;
                }
                ;

                s.setDatabaseId(rs.getInt("id"));
                s.setUsername(rs.getString("username"));
                s.setPassword(rs.getString("password"));
                s.setEmail(rs.getString("email"));
                s.setFirstname(rs.getString("firstname"));
                s.setLastname(rs.getString("lastname"));
                s.setRole(rs.getString("role"));
                s.setLocale(rs.getString("locale"));
                s.setActive(rs.getBoolean("active"));
                s.setConfirmed(rs.getBoolean("confirmed"));
            } else {
                s = null;
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

        return s;
    }
}
