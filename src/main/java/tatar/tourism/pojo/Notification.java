package tatar.tourism.pojo;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Ilya Evlampiev on 05.11.2015.
 */
public class Notification {
    private int databaseId;
    private User addressee;
    private String text;

    public static Queue<Notification> queue = new ArrayDeque<Notification>();

    public Notification(User user, String text) {
        this.addressee = user;
        this.text = text;
        queue.add(this);
 }

    public Notification() {
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public User getAddressee() {
        return addressee;
    }

    public void setAddressee(User addressee) {
        this.addressee = addressee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
