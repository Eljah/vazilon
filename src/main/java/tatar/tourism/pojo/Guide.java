package tatar.tourism.pojo;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public class Guide extends BusDriver {
    List<ExcursionTrip> plannedLeadedExcursions;
    List<ExcursionPlan> managedExcursions;

    public List<ExcursionPlan> getManagedExcursions() {
        return managedExcursions;
    }

    public void setManagedExcursions(List<ExcursionPlan> managedExcursions)  throws Exception{
        if (managedExcursions.size()>5) throw new Exception("Impossible to handle more than 5 excursions");
        this.managedExcursions = managedExcursions;
    }

    public List<ExcursionTrip> getPlannedLeadedExcursions() {

        return plannedLeadedExcursions;
    }

    public void setPlannedLeadedExcursions(List<ExcursionTrip> plannedLeadedExcursions) {
        this.plannedLeadedExcursions = plannedLeadedExcursions;
    }

    public Guide() {

        super.setRole(UserTypes.GUIDE.toString());
    }

      public int getLevel() {
        if (isBetween(managedExcursions.size(), 0, 1)) {
            return 1;
        }
        if (isBetween(managedExcursions.size(), 2, 5)) {
            return 2;
        }
        if (isBetween(managedExcursions.size(), 5, 10)) {
            return 3;
        }
        if (isBetween(managedExcursions.size(), 10, 20)) {
            return 4;
        }
        if (managedExcursions.size() > 20) {
            return 5;
        }
        return 0;
    }

    @Override
    public boolean isGuide() {
        return true;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

}
