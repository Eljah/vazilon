package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
public class MySQLExcursionDao extends MySqlDao implements ExcursionDao {
    @Override
    public void create(Excursion excursion) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO users_plan_trip "
                    + "(user, plan, trip)"
                    + "VALUES( ?,?,?)");
            stmt.setInt(1, excursion.getUser().getDatabaseId());
            stmt.setInt(2, excursion.getExcursionPlan().getDatabaseId());
            stmt.setInt(3, excursion.getExcursionTrip().getDatabaseId());
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
    public Excursion read(int key) {
        return null;
    }

    @Override
    public Excursion read(Excursion excursion) {
        String sql = "SELECT * FROM users_plan_trip LEFT JOIN excursion_trip ON excursion_trip.id=users_plan_trip.trip LEFT JOIN excursion_plan ON excursion_trip.plan_id=excursion_plan.id LEFT JOIN users A ON excursion_plan.author_guide=A.id LEFT JOIN users L ON excursion_trip.leading_guide=L.id  where users_plan_trip.id=?";
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, excursion.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                ExcursionTrip s = new ExcursionTrip();
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

                excursion.setExcursionTrip(s);
                excursion.setExcursionPlan(excursionPlan);
                excursion.setGuide(guide);
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
        return excursion;
    }

    @Override
    public Excursion read(ExcursionTrip excursionTrip, User user) {
        String sql = "SELECT * FROM users_plan_trip LEFT JOIN excursion_trip ON excursion_trip.id=users_plan_trip.trip WHERE users_plan_trip.trip = ? and users_plan_trip.user = ?";
        Excursion ex = null;
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, excursionTrip.getDatabaseId());
            stm.setInt(2, user.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                ex = new Excursion();
                list.add(excursionTrip);
                ex.setExcursionTrip(excursionTrip);
                ex.setDatabaseId(rs.getInt("users_plan_trip.id"));
                ex.setGuideStars(rs.getInt("users_plan_trip.stars_guide"));
                ex.setTripStars(rs.getInt("users_plan_trip.stars_trip"));
                ex.setPlanStars(rs.getInt("users_plan_trip.stars_plan"));
                ex.setGuideFeedback(rs.getString("users_plan_trip.feedback_guide"));
                ex.setTripFeedback(rs.getString("users_plan_trip.feedback_trip"));
                ex.setPlanFeedback(rs.getString("users_plan_trip.feedback_plan"));
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
        return ex;
    }


    @Override
    public void update(Excursion excursion) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users_plan_trip SET feedback_trip=?, stars_trip=?,  feedback_guide=?, stars_guide=?,  feedback_plan=?, stars_plan=? " +
                    "WHERE id =  ? and user=?");
            stmt.setString(1, excursion.getTripFeedback());
            stmt.setString(2, excursion.getGuideFeedback());
            stmt.setString(3, excursion.getPlanFeedback());
            stmt.setInt(4, excursion.getDatabaseId());
            stmt.setInt(5, excursion.getUser().getDatabaseId());
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
    public void updateVoteTrip(Excursion excursion) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users_plan_trip SET stars_trip=? " +
                    "WHERE id =  ? and user=?");
            if (excursion.getTripStars() != null)
                stmt.setInt(1, excursion.getTripStars());
            else
                stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(2, excursion.getDatabaseId());
            stmt.setInt(3, excursion.getUser().getDatabaseId());
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
    public void updateVotePlan(Excursion excursion) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users_plan_trip SET stars_plan=? " +
                    "WHERE id =  ? and user=?");
            if (excursion.getPlanStars() != null)
                stmt.setInt(1, excursion.getPlanStars());
            else
                stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(2, excursion.getDatabaseId());
            stmt.setInt(3, excursion.getUser().getDatabaseId());
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
    public void updateVoteGuide(Excursion excursion) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE users_plan_trip SET stars_guide=? " +
                    "WHERE id =  ? and user=?");
            if (excursion.getGuideStars() != null)
                stmt.setInt(1, excursion.getGuideStars());
            else
                stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setInt(2, excursion.getDatabaseId());
            stmt.setInt(3, excursion.getUser().getDatabaseId());
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
    public void delete(Excursion excursion) {

    }

    @Override
    public void delete(ExcursionTrip excursionTrip, User user) {
        PreparedStatement stmt = null;
        Connection con = getConnection();
        try {
            stmt = con.prepareStatement("DELETE FROM users_plan_trip WHERE trip =  ? AND user=?");
            stmt.setInt(1, excursionTrip.getDatabaseId());
            stmt.setInt(2, user.getDatabaseId());
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
    public List<ExcursionPlan> getAll() throws SQLException {
        return null;
    }

    @Override
    public List<ExcursionPlan> getForGuide(Guide guide) throws SQLException {
        return null;
    }


    public List<ExcursionTrip> getExcursionTripsIds(User user) {
        String sql = "SELECT * FROM users_plan_trip LEFT JOIN excursion_trip ON excursion_trip.id=users_plan_trip.trip WHERE user = ?";
        List<ExcursionTrip> list = new ArrayList<ExcursionTrip>();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, user.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()) {
                s = new ExcursionTrip();
                s.setDatabaseId(rs.getInt("excursion_trip.id"));
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
    public AverageCountPair averageVote(ExcursionTrip excursionTrip) {
        String sql = "SELECT AVG(stars_trip), COUNT(stars_trip) FROM users_plan_trip WHERE trip = ?";
        AverageCountPair avg = new AverageCountPair();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, excursionTrip.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                avg.setAverage(rs.getFloat(1));
                avg.setCount(rs.getInt(2));
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
        return avg;
    }

    @Override
    public AverageCountPair averageVote(ExcursionPlan excursionPlan) {
        String sql = "SELECT AVG(stars_plan), COUNT(stars_plan) FROM users_plan_trip WHERE plan = ?";
        AverageCountPair avg = new AverageCountPair();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, excursionPlan.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                avg.setAverage(rs.getFloat(1));
                avg.setCount(rs.getInt(2));
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
        return avg;
    }

    @Override
    public AverageCountPair averageVote(Guide guide) {
        String sql = "SELECT AVG(users_plan_trip.stars_guide), COUNT(users_plan_trip.stars_guide) FROM users_plan_trip LEFT JOIN excursion_trip ON excursion_trip.id=users_plan_trip.trip WHERE excursion_trip.leading_guide = ?";
        AverageCountPair avg = new AverageCountPair();
        PreparedStatement stm = null;
        Connection con = getConnection();
        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, guide.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()
                    ) {
                avg.setAverage(rs.getFloat(1));
                avg.setCount(rs.getInt(2));
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
        return avg;
    }

}
