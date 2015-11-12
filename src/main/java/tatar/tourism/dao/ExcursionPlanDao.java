package tatar.tourism.dao;

import tatar.tourism.pojo.AverageCountPair;
import tatar.tourism.pojo.ExcursionPlan;
import tatar.tourism.pojo.ExcursionTrip;
import tatar.tourism.pojo.Guide;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ilya Evlampiev on 26.10.2015.
 */
public interface ExcursionPlanDao {
    /** Создает новую запись и соответствующий ей объект */
    public void create(ExcursionPlan excursionPlan);

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public ExcursionPlan read(int key);

    /** Сохраняет состояние объекта group в базе данных */
    public void update(ExcursionPlan excursion);

    public void updateVote(ExcursionPlan excursion, AverageCountPair vote);

    /** Удаляет запись об объекте из базы данных */
    public void delete(ExcursionPlan excursion);

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<ExcursionPlan> getAll() throws SQLException;

    public List<ExcursionPlan> getForGuide(Guide guide) throws SQLException;
}
