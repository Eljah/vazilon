package tatar.tourism.pojo;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
public class Excursion {
    private int databaseId;
    private User user;
    private ExcursionPlan excursionPlan;
    private ExcursionTrip excursionTrip;
    private Guide guide;
    private String planFeedback;
    private Integer planStars;
    private String tripFeedback;
    private Integer tripStars;
    private String guideFeedback;
    private Integer guideStars;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExcursionPlan getExcursionPlan() {
        return excursionPlan;
    }

    public void setExcursionPlan(ExcursionPlan excursionPlan) {
        this.excursionPlan = excursionPlan;
    }

    public ExcursionTrip getExcursionTrip() {
        return excursionTrip;
    }

    public void setExcursionTrip(ExcursionTrip excursionTrip) {
        this.excursionTrip = excursionTrip;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public String getPlanFeedback() {
        return planFeedback;
    }

    public void setPlanFeedback(String planFeedback) {
        this.planFeedback = planFeedback;
    }

    public Integer getPlanStars() {
        return planStars;
    }

    public void setPlanStars(Integer planStars) {
        this.planStars = planStars;
    }

    public String getTripFeedback() {
        return tripFeedback;
    }

    public void setTripFeedback(String tripFeedback) {
        this.tripFeedback = tripFeedback;
    }

    public Integer getTripStars() {
        return tripStars;
    }

    public void setTripStars(Integer tripStars) {
        this.tripStars = tripStars;
    }

    public String getGuideFeedback() {
        return guideFeedback;
    }

    public void setGuideFeedback(String guideFeedback) {
        this.guideFeedback = guideFeedback;
    }

    public Integer getGuideStars() {
        return guideStars;
    }

    public void setGuideStars(Integer guideStars) {
        this.guideStars = guideStars;
    }
}
