package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String userName = "root";
    private static String password = "";
    private static String serverName = "localhost";
    private static String portNumber = "3306";
    private static String dbName = "testpersonne";
    private static Connection connection;

    /**
     * modifie le nom de la base de donnée
     * @param nomDB
     *      nouveau nom de la base de données
     */
    public static void setNomDB(String nomDB){
        dbName = nomDB;
        connection = null;
    }

    /**
     * gere la creation de connection
     * @return
     *      une connection unique
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if (connection==null){
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            connection = DriverManager.getConnection(urlDB, connectionProps);
        }
        return connection;
    }
}


