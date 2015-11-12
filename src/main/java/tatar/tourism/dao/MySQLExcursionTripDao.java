package tatar.tourism.dao;

import tatar.tourism.pojo.AverageCountPair;
import tatar.tourism.pojo.ExcursionPlan;
import tatar.tourism.pojo.ExcursionTrip;
import tatar.tourism.pojo.Guide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 27.10.2015.
 */
public class MySQLExcursionTripDao extends MySqlDao implements ExcursionTripDao {

    @Override
    public void create(ExcursionTrip excursionTrip) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO excursion_trip "
                    + "(date, complete, plan_id, leading_guide)"
                    + "VALUES( ?,?,?,?)");
            stmt.setDate(1, new java.sql.Date(excursionTrip.getDate().getTime()));
            stmt.setBoolean(2, excursionTrip.isComplete());
            stmt.setInt(3, excursionTrip.getOrigin().getDatabaseId());
            stmt.setInt(4, excursionTrip.getLeadingGuide().getDatabaseId());
            stmt.execute();
            //log.trace("Addition to notes by user " + note.getUser().getUsername());
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error("Addition of new comment failed " + e.getLocalizedMessage());
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
    public ExcursionTrip read(int key) {
        String sql = "SELECT * FROM excursion_trip LEFT JOIN excursion_plan ON excursion_trip.plan_id=excursion_plan.id LEFT JOIN users A ON excursion_plan.author_guide=A.id LEFT JOIN users L ON excursion_trip.leading_guide=L.id  where excursion_trip.id=?";
        Connection con = getConnection();
        PreparedStatement stm = null;
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, key);
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s = new ExcursionTrip();
            while (rs.next()
                    ) {
                s.setDatabaseId(rs.getInt("excursion_trip.id"));
                s.setDate(rs.getDate("excursion_trip.date"));
                s.setComplete(rs.getBoolean("excursion_trip.complete"));
                s.setVote(rs.getFloat("excursion_trip.vote"));
                s.setVoteCount(rs.getInt("excursion_trip.vote_count"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("excursion_trip.leading_guide"));
                guide.setUsername(rs.getString("L.username"));
                guide.setFirstname(rs.getString("L.firstname"));
                guide.setLastname(rs.getString("L.lastname"));
                guide.setVote(rs.getFloat("L.vote"));
                guide.setVoteCount(rs.getInt("L.vote_count"));
                s.setLeadingGuide(guide);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("excursion_trip.plan_id"));
                excursionPlan.setShortExplanation(rs.getString("excursion_plan.short_explanation"));
                excursionPlan.setExplanation(rs.getString("excursion_plan.explanation"));
                excursionPlan.setVote(rs.getFloat("excursion_plan.vote"));
                excursionPlan.setVoteCount(rs.getInt("excursion_plan.vote_count"));
                Guide auth = new Guide();
                auth.setUsername(rs.getString("A.username"));
                auth.setFirstname(rs.getString("A.firstname"));
                auth.setLastname(rs.getString("A.lastname"));
                auth.setVote(rs.getFloat("A.vote"));
                auth.setVoteCount(rs.getInt("A.vote_count"));
                excursionPlan.setAuthor(auth);
                s.setOrigin(excursionPlan);
            }
            return s;

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

        return null;
    }

    @Override
    public void update(ExcursionTrip excursion) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE excursion_trip SET date =  ?, complete=?, leading_guide=?, plan_id=? " +
                    "WHERE id =  ?");
            stmt.setDate(1, new java.sql.Date(excursion.getDate().getTime()));
            stmt.setBoolean(2, excursion.isComplete());
            stmt.setInt(3, excursion.getLeadingGuide().getDatabaseId());
            stmt.setInt(4, excursion.getOrigin().getDatabaseId());
            stmt.setInt(5, excursion.getDatabaseId());
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
    public void updateVote(ExcursionTrip excursion, AverageCountPair vote) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE excursion_trip SET vote =  ?, vote_count=?" +
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
    public void delete(ExcursionTrip excursion) {

    }

    @Override
    public List<ExcursionTrip> getAll() throws SQLException {
        String sql = "SELECT * FROM excursion_trip LEFT JOIN excursion_plan ON excursion_trip.plan_id=excursion_plan.id  LEFT JOIN users A ON excursion_plan.author_guide=A.id LEFT JOIN users L ON excursion_trip.leading_guide=L.id ";
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()
                    ) {
                s = new ExcursionTrip();
                s.setDatabaseId(rs.getInt("excursion_trip.id"));
                s.setDate(rs.getDate("excursion_trip.date"));
                s.setComplete(rs.getBoolean("excursion_trip.complete"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("excursion_trip.leading_guide"));
                guide.setUsername(rs.getString("L.username"));
                guide.setFirstname(rs.getString("L.firstname"));
                guide.setLastname(rs.getString("L.lastname"));
                s.setLeadingGuide(guide);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("excursion_trip.plan_id"));
                excursionPlan.setShortExplanation(rs.getString("excursion_plan.short_explanation"));
                excursionPlan.setExplanation(rs.getString("excursion_plan.explanation"));
                Guide auth = new Guide();
                auth.setUsername(rs.getString("A.username"));
                auth.setFirstname(rs.getString("A.firstname"));
                auth.setLastname(rs.getString("A.lastname"));
                excursionPlan.setAuthor(auth);
                s.setOrigin(excursionPlan);
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
    public List<ExcursionTrip> getForExcusionPlan(ExcursionPlan plan) throws SQLException {
        String sql = "SELECT * FROM excursion_trip where plan_id=?";
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, plan.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()
                    ) {
                s = new ExcursionTrip();
                s.setDatabaseId(rs.getInt("id"));
                s.setDate(rs.getDate("date"));
                s.setComplete(rs.getBoolean("complete"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("leading_guide"));
                s.setLeadingGuide(guide);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("plan_id"));
                s.setOrigin(excursionPlan);
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
    public List<ExcursionTrip> getAllInFuture() throws SQLException {
        String sql = "SELECT * FROM excursion_trip LEFT JOIN excursion_plan ON excursion_trip.plan_id=excursion_plan.id  LEFT JOIN users A ON excursion_plan.author_guide=A.id LEFT JOIN users L ON excursion_trip.leading_guide=L.id where excursion_trip.date>now()";
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()
                    ) {
                s = new ExcursionTrip();
                s.setDatabaseId(rs.getInt("excursion_trip.id"));
                s.setDate(rs.getDate("excursion_trip.date"));
                s.setComplete(rs.getBoolean("excursion_trip.complete"));
                s.setVote(rs.getFloat("excursion_trip.vote"));
                s.setVoteCount(rs.getInt("excursion_trip.vote_count"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("excursion_trip.leading_guide"));
                guide.setUsername(rs.getString("L.username"));
                s.setLeadingGuide(guide);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("excursion_trip.plan_id"));
                excursionPlan.setShortExplanation(rs.getString("excursion_plan.short_explanation"));
                Guide auth = new Guide();
                auth.setUsername(rs.getString("A.username"));
                excursionPlan.setAuthor(auth);
                s.setOrigin(excursionPlan);
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
    public List<ExcursionTrip> getAllComplete() throws SQLException {
        String sql = "SELECT * FROM excursion_trip LEFT JOIN excursion_plan ON excursion_trip.plan_id=excursion_plan.id  LEFT JOIN users A ON excursion_plan.author_guide=A.id LEFT JOIN users L ON excursion_trip.leading_guide=L.id where excursion_trip.complete=1";
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()
                    ) {
                s = new ExcursionTrip();
                s.setDatabaseId(rs.getInt("excursion_trip.id"));
                s.setDate(rs.getDate("excursion_trip.date"));
                s.setComplete(rs.getBoolean("excursion_trip.complete"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("excursion_trip.leading_guide"));
                guide.setUsername(rs.getString("L.username"));
                s.setLeadingGuide(guide);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("excursion_trip.plan_id"));
                excursionPlan.setShortExplanation(rs.getString("excursion_plan.short_explanation"));
                Guide auth = new Guide();
                auth.setUsername(rs.getString("A.username"));
                excursionPlan.setAuthor(auth);
                s.setOrigin(excursionPlan);
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
    public List<ExcursionTrip> getAllIncomplete() throws SQLException {
        String sql = "SELECT * FROM excursion_trip LEFT JOIN excursion_plan ON excursion_trip.plan_id=excursion_plan.id  LEFT JOIN users A ON excursion_plan.author_guide=A.id LEFT JOIN users L ON excursion_trip.leading_guide=L.id where excursion_trip.complete=1";
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()
                    ) {
                s = new ExcursionTrip();
                s.setDatabaseId(rs.getInt("excursion_trip.id"));
                s.setDate(rs.getDate("excursion_trip.date"));
                s.setComplete(rs.getBoolean("excursion_trip.complete"));
                Guide guide = new Guide();
                guide.setDatabaseId(rs.getInt("excursion_trip.leading_guide"));
                guide.setUsername(rs.getString("L.username"));
                s.setLeadingGuide(guide);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("excursion_trip.plan_id"));
                excursionPlan.setShortExplanation(rs.getString("excursion_plan.short_explanation"));
                Guide auth = new Guide();
                auth.setUsername(rs.getString("A.username"));
                excursionPlan.setAuthor(auth);
                s.setOrigin(excursionPlan);
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

}
