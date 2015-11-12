package tatar.tourism.pojo;

import java.util.Date;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
public class Token {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private Date deleteDate;

    private User user;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
