package tatar.tourism.pojo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public class ExcursionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    private int databaseId;
    private String map;
    private ArrayList<Point> pointChain;
    private String explanation;
    private String shortExplanation;
    private Float vote;
    private Integer voteCount;

    public Guide getAuthor() {
        return author;
    }

    public void setAuthor(Guide author) {
        this.author = author;
    }

    public String getShortExplanation() {

        return shortExplanation;
    }

    public void setShortExplanation(String shortExplanation) {
        this.shortExplanation = shortExplanation;
    }

    public String getExplanation() {

        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    private Guide author;

    //private ArrayList<Guide> certifiedGuides;
    //private ArrayList<Guide> requestedCertificationGuides;

    //private ArrayList<ExcursionTrip> allTrips;
    //private ArrayList<Integer> assesment;
    //private Integer averageAssesment;


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
