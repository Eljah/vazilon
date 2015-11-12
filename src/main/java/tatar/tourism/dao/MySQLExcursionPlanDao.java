package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 26.10.2015.
 */
public class MySQLExcursionPlanDao extends MySqlDao implements ExcursionPlanDao {

    @Override
    public void create(ExcursionPlan excursionPlan) {
        Connection con =getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO excursion_plan "
                    + "(explanation, short_explanation, author_guide)"
                    + "VALUES( ?,?,?)");
            stmt.setString(1, excursionPlan.getExplanation());
            stmt.setString(2, excursionPlan.getShortExplanation());
            stmt.setInt(3, excursionPlan.getAuthor().getDatabaseId());
            stmt.execute();
            //log.trace("Addition to notes by user " + note.getUser().getUsername());
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error("Addition of new comment failed " + e.getLocalizedMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public ExcursionPlan read(int key) {
        String sql = "SELECT * FROM excursion_plan where id=?";
        PreparedStatement stm = null;
        Connection con =getConnection();
        ExcursionPlan s = new ExcursionPlan();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, key);
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                s.setDatabaseId(rs.getInt("id"));
                s.setExplanation(rs.getString("explanation"));
                s.setShortExplanation(rs.getString("short_explanation"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("author_guide"));
                s.setAuthor(guide);
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
    public void update(ExcursionPlan excursion) {
        Connection con =getConnection();
PreparedStatement stmt=null;
        try {
            stmt = con.prepareStatement("UPDATE excursion_plan SET explanation =  ?, short_explanation=?, author_guide=? " +
                    "WHERE id =  ?");
            stmt.setString(1, excursion.getExplanation());
            stmt.setString(2, excursion.getShortExplanation());
            stmt.setInt(3, excursion.getAuthor().getDatabaseId());
            stmt.setInt(4, excursion.getDatabaseId());
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
    public void updateVote(ExcursionPlan excursion, AverageCountPair vote) {

            Connection con = getConnection();
            PreparedStatement stmt = null;
            try {
                stmt = con.prepareStatement("UPDATE excursion_plan SET vote =  ?, vote_count=?" +
                        " WHERE id =  ?");
                stmt.setFloat(1, vote.getAverage());
                stmt.setInt(2, vote.getCount());
                stmt.setInt(3, excursion.getDatabaseId());
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
    public void delete(ExcursionPlan excursion) {

    }

    @Override
    public List<ExcursionPlan> getAll() throws SQLException {
        String sql = "SELECT * FROM excursion_plan LEFT JOIN users A ON excursion_plan.author_guide=A.id ";
        List<ExcursionPlan> list = new ArrayList<ExcursionPlan>();
        ExcursionPlan s;
        Connection con =getConnection();
        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                s = new ExcursionPlan();
                s.setDatabaseId(rs.getInt("id"));
                s.setExplanation(rs.getString("explanation"));
                s.setShortExplanation(rs.getString("short_explanation"));
                Guide author = new Guide();
                author.setDatabaseId(rs.getInt("A.id"));
                author.setUsername(rs.getString("A.username"));
                s.setAuthor(author);
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
            return list;
        }
    }

    @Override
    public List<ExcursionPlan> getForGuide(Guide guide) throws SQLException {
        String sql = "SELECT * FROM excursion_plan where author_guide=?";
        List<ExcursionPlan> list = new ArrayList<ExcursionPlan>();
        Connection con =getConnection();
        PreparedStatement stm = null;
        ExcursionPlan s;

        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, guide.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                s = new ExcursionPlan();
                s.setDatabaseId(rs.getInt("id"));
                s.setExplanation(rs.getString("explanation"));
                s.setShortExplanation(rs.getString("short_explanation"));
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
            return list;
        }
    }
}
