package tatar.tourism.pojo;

/**
 * Created by Ilya Evlampiev on 10.11.2015.
 */
public class Picture {
    String originName;
    String uploadDate;
    User uploader;
    String description;
    Float coordinateLat;
    Float coordinateLong;
    Double degree;

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCoordinateLat() {
        return coordinateLat;
    }

    public void setCoordinateLat(Float coordinateLat) {
        this.coordinateLat = coordinateLat;
    }

    public Float getCoordinateLong() {
        return coordinateLong;
    }

    public void setCoordinateLong(Float coordinateLong) {
        this.coordinateLong = coordinateLong;
    }

    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    public void setCoordinatesAndDegree(Float coordinate1Lat, Float coordinate1Long, Float coordinate2Lat, Float coordinate2Long) {
        this.coordinateLat = coordinate1Lat;
        this.coordinateLong = coordinate1Long;
        this.degree = Math.toDegrees(Math.atan((coordinate2Lat - coordinate1Lat) / (coordinate2Long - coordinate1Long)));
    }
}
