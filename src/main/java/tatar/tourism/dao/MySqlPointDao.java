package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.sql.*;
import java.util.*;

/**
 * Created by Ilya Evlampiev on 07.11.2015.
 */
public class MySqlPointDao extends MySqlDao implements PointDao {
    @Override
    public Route create(Route route) {
        PreparedStatement stm = null;
        PreparedStatement stmt=null;
        PreparedStatement stmt1=null;
        Connection con = getConnection();
        LinkedList<Point> points = route.getPoints();
        List<Route> r = new ArrayList<Route>() {
        };
        try {
            con.setAutoCommit(false);

            for (Point p : points) {

                String sql = "SELECT MAX(point_order) FROM routes WHERE plan_id = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, route.getPlan().getDatabaseId());
                ResultSet rs = stm.executeQuery();
                int maxOrder = 0;
                int id=0;
                while (rs.next()) {
                    maxOrder = rs.getInt(1);
                }
                stm.close();


                 stmt = con.prepareStatement("INSERT INTO points " +
                        "(latitude,longitude,name,description,author)" +
                        "VALUES( ?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                stmt.setFloat(1, p.getLatitude());
                stmt.setFloat(2,  p.getLongitude());
                stmt.setString(3,  p.getName());
                stmt.setString(4,  p.getDescription());
                stmt.setInt(5,  p.getAuthor().getDatabaseId());
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id=generatedKeys.getInt(1);
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                stmt.close();
                route.getPoints().getLast().setId(id);
                stmt1 = con.prepareStatement("INSERT INTO routes " +
                        "(plan_id,point_id,point_order)" +
                        "VALUES( ?,?,?)", Statement.RETURN_GENERATED_KEYS);
                stmt1.setInt(1, route.getPlan().getDatabaseId());
                stmt1.setInt(2, id);
                stmt1.setInt(3, (maxOrder + 1));
                int affectedRows1 = stmt1.executeUpdate();

                if (affectedRows1 == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt1.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        id=generatedKeys.getInt(1);
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                route.setId(id);
                stmt1.close();
            }

            //log.trace("Addition to notes by user " + note.getUser().getUsername());
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            //log.error("Addition of new comment failed " + e.getLocalizedMessage());
        } finally {
            try {
                stm.close();
                stmt.close();
                stmt1.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    return route;
    }

    @Override
    public Point readPoint(int databaseId) {
        String sql = "SELECT * FROM points WHERE id = ?";
        Point p = new Point();
        LinkedList<Point> arrayList = new LinkedList<Point>();
        PreparedStatement stm = null;
        Connection con = getConnection();

        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, databaseId);
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setLatitude(rs.getFloat("latitude"));
                p.setLongitude(rs.getFloat("longitude"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                Guide author = new Guide();
                author.setDatabaseId(rs.getInt("author"));
                p.setAuthor(author);
                arrayList.add(p);
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
        return p;
    }

    @Override
    public Route readRoute(int databaseId) {
        return null;
    }

    @Override
    public Route readRoute(ExcursionPlan plan) {
        String sql = "SELECT * FROM routes LEFT JOIN points ON routes.point_id=points.id WHERE routes.plan_id = ? order by routes.point_order";
        Route route = new Route();
        LinkedList<Point> arrayList = new LinkedList<Point>();
        PreparedStatement stm = null;
        Connection con = getConnection();

        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, plan.getDatabaseId());
            ResultSet rs = stm.executeQuery();
            ExcursionTrip s;
            while (rs.next()) {
                Point p = new Point();
                p.setId(rs.getInt("points.id"));
                p.setLatitude(rs.getFloat("points.latitude"));
                p.setLongitude(rs.getFloat("points.longitude"));
                p.setName(rs.getString("points.name"));
                p.setDescription(rs.getString("points.description"));
                Guide author = new Guide();
                author.setDatabaseId(rs.getInt("author"));
                p.setAuthor(author);

                route.setId(rs.getInt("routes.id"));

                arrayList.add(p);
            }
            route.setPoints(arrayList);
            route.setPlan(plan);

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
        return route;
    }

    @Override
    public Route readRoute(Point point) {
        String sql = "SELECT * FROM routes LEFT JOIN points ON routes.point_id=points.id WHERE points.id = ?";
        Route route = new Route();
        LinkedList<Point> arrayList = new LinkedList<Point>();
        PreparedStatement stm = null;
        Connection con = getConnection();

        try {
            stm = con.prepareStatement(sql);
            stm.setInt(1, point.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Point p = new Point();
                p.setId(rs.getInt("points.id"));
                p.setLatitude(rs.getFloat("points.latitude"));
                p.setLongitude(rs.getFloat("points.longitude"));
                p.setName(rs.getString("points.name"));
                p.setDescription(rs.getString("points.description"));
                Guide author = new Guide();
                author.setDatabaseId(rs.getInt("author"));
                p.setAuthor(author);
                ExcursionPlan excursionPlan = new ExcursionPlan();
                excursionPlan.setDatabaseId(rs.getInt("routes.plan_id"));
                route.setPlan(excursionPlan);
                route.setId(rs.getInt("routes.id"));
                arrayList.add(p);
            }
            route.setPoints(arrayList);

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
        return route;
    }


    @Override
    public List<Point> readAllForRoute(Route route) {
        return null;
    }

    @Override
    public void updatePoint(Point point, User user) {
        Connection con = getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE points SET latitude=?, longitude=?,name=?,description=? " +
                    "WHERE id =  ? and author=?");
            stmt.setFloat(1, point.getLatitude());
            stmt.setFloat(2, point.getLongitude());
            stmt.setString(3, point.getName());
            stmt.setString(4, point.getDescription());
            stmt.setInt(5, point.getId());
            stmt.setInt(6, user.getDatabaseId());
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
    public void delete(Point point) {

    }

    @Override
    public void delete(Route route) {

    }
}
