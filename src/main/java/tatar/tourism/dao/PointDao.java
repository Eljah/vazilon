package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 07.11.2015.
 */
public interface PointDao {
    public Route create(Route route);

    public Point readPoint(int databaseId);

    public Route readRoute(int databaseId);

    public Route readRoute(ExcursionPlan plan);

    public Route readRoute(Point point);

    public List<Point> readAllForRoute(Route route);

    public void updatePoint(Point point, User user);

    public void delete(Point point);

    public void delete(Route route);
}
