import activeRecord.DBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {

    @Test
    public void test_1_seul_connection() throws SQLException {
        Connection c = DBConnection.getConnection();
        Connection c1 = DBConnection.getConnection();
        Assertions.assertEquals(c,c1);
    }

    /**
     *
     * @throws SQLException
     */
    @Test
    public void test_changer_connection() throws SQLException {
        Connection c = DBConnection.getConnection();
        DBConnection.setNomDB("test");
        Connection c1 = DBConnection.getConnection();

        Assertions.assertFalse(c==c1);
    }
}
