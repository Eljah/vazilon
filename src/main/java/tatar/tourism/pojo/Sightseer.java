package tatar.tourism.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public class Sightseer extends User {
    List<ExcursionPlan> desiredExcursions;
    List<ExcursionTrip> plannedExcursions = new ArrayList<ExcursionTrip>() {
    };
    List<ExcursionTrip> visitedExcursions = new ArrayList<ExcursionTrip>();

    public Sightseer() {
        super.setRole(UserTypes.SIGHTSEER.toString());
    }

    public void addPlannedExcursion(ExcursionTrip e) {
        plannedExcursions.add(e);
    }

    public void removePlannedExcursion(ExcursionTrip e) {
        ExcursionTrip flagged = null;
        for (ExcursionTrip p : plannedExcursions) {
            if (e.getDatabaseId() == (p.getDatabaseId())) {
                flagged = p;
            }
            ;

        }
        plannedExcursions.remove(flagged);
    }

    public boolean hasExcursionInPlanned(ExcursionTrip e) {
        for (ExcursionTrip p : plannedExcursions) {
            if (e.getDatabaseId() == (p.getDatabaseId())) {
                return true;
            }
            ;
        }
        return false;
    }

    public List<ExcursionTrip> getPlannedExcursions() {
        return plannedExcursions;
    }

    public void setPlannedExcursions(List<ExcursionTrip> plannedExcursions) {
        this.plannedExcursions = plannedExcursions;
    }

    public int getLevel() {
        if (isBetween(visitedExcursions.size(), 0, 1)) {
            return 1;
        }
        if (isBetween(visitedExcursions.size(), 2, 5)) {
            return 2;
        }
        if (isBetween(visitedExcursions.size(), 5, 10)) {
            return 3;
        }
        if (isBetween(visitedExcursions.size(), 10, 20)) {
            return 4;
        }
        if (visitedExcursions.size() > 20) {
            return 5;
        }
        return 0;
    }

    @Override
    public boolean isBusDriver() {
        return false;
    }

    @Override
    public boolean isGuide() {
        return false;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
}
