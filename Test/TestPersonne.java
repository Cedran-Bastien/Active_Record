import activeRecord.Personne;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPersonne {
    //attribut
    private Personne p;
    private Personne p1;

    @BeforeEach
    public void preparation(){
        try {
            Personne.createTable();
            this.p = new Personne("BASTIEN","Cèdran");
            this.p1 = new Personne("TRAN", "Maëva");
            p.save();
            p1.save();
        }catch (SQLException e ){
            System.out.println("exeption");
        }

        this.p = new Personne("BASTIEN","Cèdran");
        this.p1 = new Personne("TRAN", "Maëva");

    }

    @AfterEach
    public void fin() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    public void test_findAll() throws SQLException {
        List<Personne> verif = new ArrayList<Personne>();
        //System.out.printf(this.p.toString());
        verif.add(this.p);
        verif.add(this.p1);

        List<Personne> table = Personne.findAll();
        Assertions.assertEquals(verif,table);
    }

}
