package tatar.tourism.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Ilya Evlampiev on 29.10.2015.
 */
public class MySqlDao {

    Connection getConnection()
    {
        try {
            return DaoFactory.getDAOFactory(1).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
