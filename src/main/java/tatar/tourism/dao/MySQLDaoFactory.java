package tatar.tourism.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Ilya Evlampiev on 26.10.2015.
 */
public class MySQLDaoFactory extends DaoFactory {

    //public static final String DRIVER=
    //        "COM.cloudscape.core.RmiJdbcDriver";
    //public static final String DBURL=
    //        "jdbc:cloudscape:rmi://localhost:1099/CoreJ2EEDB";

    public static final String JNDI_MYSQL_RESOURCE = "java:comp/env/jdbc/tourismDS";

    public Connection createConnection() {

        Context ctx = null;
        try {
            ctx = new InitialContext();
            return ((DataSource) ctx.lookup(JNDI_MYSQL_RESOURCE)).getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ;
        // Использовать DRIVER и DBURL для создания соединения
        // Рекомендовать реализацию/использование пула соединений
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return createConnection();
    }

    @Override
    public ExcursionPlanDao getExcursionPlanDao() {
        return new MySQLExcursionPlanDao();
    }

    @Override
    public ExcursionTripDao getExcursionTripDao() {
        return new MySQLExcursionTripDao();
    }

    @Override
    public ExcursionDao getExcursionDao() {
        return new MySQLExcursionDao();
    }

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }

    @Override
    public TokenDao getTokenDao() {
        return new MySqlTokenDao();
    }

    @Override
    public NotificationDao getNotificationDao() {
        return new MySqlNotificationDao();
    }

    @Override
    public PointDao getPointDao() {
        return new MySqlPointDao();
    }


}
