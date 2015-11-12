package tatar.tourism.dao;

import tatar.tourism.pojo.Notification;
import tatar.tourism.pojo.Token;
import tatar.tourism.pojo.User;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 05.11.2015.
 */
public interface NotificationDao {
    public void create(Notification notification);

    public Notification read(int databaseId);

    public List<Notification> readAllForUser(User user);

    public void delete(Notification notification);

    public void deleteAllForUser(User user);

}
