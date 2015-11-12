package tatar.tourism.dao;

/**
 * Created by Ilya Evlampiev on 20.10.2015.
 */

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Фабрика объектов для работы с базой данных
 */
public abstract class DaoFactory {

    public static final int MYSQL = 1;

    public static DaoFactory getDAOFactory(
            int whichFactory) {

        switch (whichFactory) {
            case MYSQL:
                return new MySQLDaoFactory();
            default           :
                return null;
        }
    }
    /**
     * Возвращает подключение к базе данных
     */
    public abstract Connection getConnection() throws SQLException;

    public abstract ExcursionTripDao getExcursionTripDao();

    public abstract ExcursionPlanDao getExcursionPlanDao();

    public abstract ExcursionDao getExcursionDao();

    public abstract UserDao getUserDao();

    public abstract TokenDao getTokenDao();

    public abstract NotificationDao getNotificationDao();

    public abstract PointDao getPointDao();
}
