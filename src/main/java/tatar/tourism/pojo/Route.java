package tatar.tourism.pojo;

import java.util.LinkedList;

/**
 * Created by Ilya Evlampiev on 07.11.2015.
 */
public class Route {
    private int id;
    private LinkedList<Point> points;
    private ExcursionPlan plan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Point> getPoints() {
        return points;
    }

    public void setPoints(LinkedList<Point> points) {
        this.points = points;
    }

    public ExcursionPlan getPlan() {
        return plan;
    }

    public void setPlan(ExcursionPlan planId) {
        this.plan = planId;
    }

}
