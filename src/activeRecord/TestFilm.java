package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

public class TestFilm {
    private Personne rea1 ;
    private Film f1;
    private Personne rea2;
    private Film f2;

    @BeforeEach
    public void preparation_test() throws SQLException, RealisateurAbsentException {
        Film.createTable();
        rea1= new Personne("BASTIEN","Cedran");
        rea2 = new Personne("BONJOUR", "Selenia");
        rea1.save();
        rea2.save();
        f1 = new Film("un bon film", rea1);
        f2 = new Film("un autre film", rea2);
        f1.save();
        f2.save();
    }

    @AfterEach
    public void fin_test() throws SQLException {
        Film.deleteTable();
    }


    @Test
    public void test_findById() throws SQLException{
        Film pe = Film.findById(1);
        Assertions.assertEquals(this.f1.getId(),pe.getId());
    }

    @Test
    public void test_findByRealisateur() throws SQLException {
        List<Film> f = Film.findByRealisateur(this.rea2);
        Assertions.assertEquals(this.f2.getId(),f.get(0).getId());
    }

    @Test
    public void test_getRealisateur() throws SQLException {
        Assertions.assertEquals(this.rea1.getId(),this.f1.getRealisateur().getId());
    }

    @Test
    public void test_save_id_negatif() throws SQLException{
        Assertions.assertEquals(this.f1.getId(),Film.findById(1).getId());
    }

    @Test
    public void test_save_id_positif() throws SQLException, RealisateurAbsentException {
        this.f1.setTitre("tete");
        //methode testé
        this.f1.save();

        Assertions.assertEquals(this.f1.getTitre(),Film.findById(this.f1.getId()).getTitre());
    }

    @Test
    public void test_delete() throws SQLException {
        this.f1.delete();

        //test
        Assertions.assertEquals(null,Film.findById(1));
    }
}
