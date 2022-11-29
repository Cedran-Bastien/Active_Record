package activeRecord;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestPersonne {
    //attribut
    private Personne p;
    private Personne p1;

    @BeforeEach
    public void preparation() throws SQLException {
            Personne.createTable();
            this.p = new Personne("BASTIEN","Cèdran");
            this.p1 = new Personne("BONJOUR", "Selenia");
            p.save();
            p1.save();
    }

    @AfterEach
    public void fin() throws SQLException {
        Personne.deleteTable();
    }

    @Test
    public void test_findAll() throws SQLException {
        List<Personne> verif = new ArrayList<Personne>();
        verif.add(this.p);
        verif.add(this.p1);

        List<Personne> table = Personne.findAll();
        for (int i = 0;i<verif.size();i++){

            Assertions.assertEquals(verif.get(i).getId(),table.get(i).getId());
            Assertions.assertEquals(verif.get(i).getNom(),table.get(i).getNom());
        }
    }

    @Test
    public void test_findById() throws SQLException{
        Personne pe = Personne.findById(1);
        Assertions.assertEquals(this.p.getId(),pe.getId());
    }

    @Test
    public void test_findByName() throws SQLException{
        List<Personne> l = Personne.findByName("BASTIEN");
        Assertions.assertEquals(this.p.getId(),l.get(0).getId());
    }

    @Test
    public void test_save_id_negatif() throws SQLException{
        Assertions.assertEquals(this.p.getId(),Personne.findById(1).getId());
    }

    @Test
    public void test_save_id_positif() throws SQLException {
        this.p.setNom("tete");
        //methode testé
        this.p.save();

        Assertions.assertEquals(this.p.getNom(),Personne.findById(this.p.getId()).getNom());
    }

    @Test
    public void test_delete() throws SQLException {
        this.p.delete();

        //test
        Assertions.assertEquals(null,Personne.findById(1));
    }

    @Test
    public void test_getId(){
        Assertions.assertEquals(1,this.p.getId());
    }


}
