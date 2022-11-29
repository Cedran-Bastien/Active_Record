package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestFilm {

    @BeforeEach
    public void preparation_test() throws SQLException {
        Film.createTable();
        Film f = new Film("un bon film", new Personne("BASTIEN","Cedran"));
        Film f1 = new Film("un autre film",new Personne("BONJOUR", "Selenia"));
    }

    @AfterEach
    public void fin_test() throws SQLException {
        Film.deleteTable();
    }


    @Test
    public void test_findById(){

    }
}
