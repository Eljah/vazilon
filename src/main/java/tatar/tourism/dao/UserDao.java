package tatar.tourism.dao;

import tatar.tourism.pojo.AverageCountPair;
import tatar.tourism.pojo.ExcursionPlan;
import tatar.tourism.pojo.User;

import java.util.List;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */
public interface UserDao {
    /**
     * Создает новую запись и соответствующий ей объект
     */
    public void create(User user);

    /**
     * Возвращает объект соответствующий записи с первичным ключом key или null
     */
    public User read(int key);

    /**
     * Сохраняет состояние объекта group в базе данных
     */
    public void update(User user);

    public void updateVote(User user, AverageCountPair vote);


    /**
     * Удаляет запись об объекте из базы данных
     */
    public void delete(User user);

    /**
     * Возвращает список объектов соответствующих всем записям в базе данных
     */
    public List<User> getAll();

    public User read(String login, String password);

    public User read(String login);
}
