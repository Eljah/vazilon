package tatar.tourism.pojo;

import java.util.Date;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public class ExcursionTrip {
    private int databaseId;

    private Guide leadingGuide;

    private ExcursionPlan origin;

    //private ArrayList<Integer> assesment;
    //private Integer averageAssesment;
    private boolean complete;

    private Float vote;

    private Integer voteCount;

    private Date date;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public Guide getLeadingGuide() {
        return leadingGuide;
    }

    public void setLeadingGuide(Guide leadingGuide) {
        this.leadingGuide = leadingGuide;
    }

    public ExcursionPlan getOrigin() {
        return origin;
    }

    public void setOrigin(ExcursionPlan origin) {
        this.origin = origin;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getVote() {
        return vote;
    }

    public void setVote(Float vote) {
        this.vote = vote;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }
}
