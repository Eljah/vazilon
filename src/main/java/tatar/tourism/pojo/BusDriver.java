package tatar.tourism.pojo;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public class BusDriver  extends Sightseer {
    List<ExcursionTrip> plannedExcursions;
    List<ExcursionTrip> visitedExcursions;

    public BusDriver() {
        super.setRole(UserTypes.BUSDRIVER.toString());
    }

    public int getLevel() {
        if (isBetween(visitedExcursions.size(), 0, 19)) {
            return 1;
        }
        if (isBetween(visitedExcursions.size(), 20, 200)) {
            return 2;
        }
        if (isBetween(visitedExcursions.size(), 201, 500)) {
            return 3;
        }
        if (isBetween(visitedExcursions.size(), 501, 1000)) {
            return 4;
        }
        if (visitedExcursions.size() > 1000) {
            return 5;
        }
        return 0;
    }

    @Override
    public boolean isBusDriver() {
        return true;
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
