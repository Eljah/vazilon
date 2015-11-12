package tatar.tourism.dao;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */

import tatar.tourism.pojo.AverageCountPair;
import tatar.tourism.pojo.ExcursionPlan;
import tatar.tourism.pojo.ExcursionTrip;

import java.sql.SQLException;
import java.util.List;

/**
 * Объект для управления персистентным состоянием объекта Group
 */
public interface ExcursionTripDao {

    /**
     * Создает новую запись и соответствующий ей объект
     */
    public void create(ExcursionTrip excursionTrip);

    /**
     * Возвращает объект соответствующий записи с первичным ключом key или null
     */
    public ExcursionTrip read(int key);

    /**
     * Сохраняет состояние объекта group в базе данных
     */
    public void update(ExcursionTrip excursion);

    public void updateVote(ExcursionTrip excursion, AverageCountPair vote);

    /**
     * Удаляет запись об объекте из базы данных
     */
    public void delete(ExcursionTrip excursion);

    /**
     * Возвращает список объектов соответствующих всем записям в базе данных
     */
    public List<ExcursionTrip> getAll() throws SQLException;

    public List<ExcursionTrip> getAllInFuture() throws SQLException;

    public List<ExcursionTrip> getAllComplete() throws SQLException;

    public List<ExcursionTrip> getAllIncomplete() throws SQLException;

    public List<ExcursionTrip> getForExcusionPlan(ExcursionPlan plan) throws SQLException;

}