package tatar.tourism.pojo;

import java.io.Serializable;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    boolean privatePoint;

    Float latitude;
    Float longitude;

    private String name;
    private String description;

    private Guide author;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrivatePoint() {
        return privatePoint;
    }

    public void setPrivatePoint(boolean privatePoint) {
        this.privatePoint = privatePoint;
    }

    public Guide getAuthor() {
        return author;
    }

    public void setAuthor(Guide author) {
        this.author = author;
    }
}
