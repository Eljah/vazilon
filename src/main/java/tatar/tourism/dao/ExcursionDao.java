package tatar.tourism.dao;

import tatar.tourism.pojo.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 02.11.2015.
 */
public interface ExcursionDao {
    /** Создает новую запись и соответствующий ей объект */
    public void create(Excursion excursion);

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public Excursion read(int key);

    public Excursion read(Excursion excursion);

    public Excursion read(ExcursionTrip excursionTrip, User user);

    /** Сохраняет состояние объекта group в базе данных */
    public void update(Excursion excursion);

    public void updateVoteTrip(Excursion excursion);
    public void updateVotePlan(Excursion excursion);
    public void updateVoteGuide(Excursion excursion);

    /** Удаляет запись об объекте из базы данных */
    public void delete(Excursion excursion);

    /** Удаляет запись об объекте из базы данных */
    public void delete(ExcursionTrip excursionTrip, User user);

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<ExcursionPlan> getAll() throws SQLException;

    public List<ExcursionPlan> getForGuide(Guide guide) throws SQLException;

    public List<ExcursionTrip> getExcursionTripsIds(User user);

    public AverageCountPair averageVote(ExcursionTrip excursionTrip);

    public AverageCountPair averageVote(ExcursionPlan excursionPlan);

    public AverageCountPair averageVote(Guide guide);

}
