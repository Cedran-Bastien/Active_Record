package activeRecord;

import jdk.jshell.PersistentSnippet;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    /**
     * construit une personne
     * @param n
     *      nom de la personne
     * @param p
     *      prenom de la personne
     */
    public Personne(String n, String p){
        this.id = -1;
        this.nom = n;
        this.prenom = p;
    }

    /**
     * recupere les donnée de la table personne
     * @return
     *      une liste de Personne
     * @throws SQLException
     */
    public static ArrayList<Personne> findAll() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Personne> res = new ArrayList<Personne>();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            Personne p = new Personne(nom,prenom);
            p.id = id;
            res.add(p);
        }
        return res;
    }

    /**
     * cree une personne
     * @param id
     *      id correspondant a la personne voulre
     * @return
     *      un objet personne correspondan,t a la personne voulue, null si inexistante
     */
    public static Personne findById(int id) throws SQLException {
        //personne a retourner
        Personne p = null;

        Connection c = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int i = rs.getInt("id");
            p = new Personne(nom,prenom);
            p.id = i;
        }
        return p;
    }

    /**
     * liste de personne par nom
     * @param name
     *      nom des personne a rechecher
     * @return
     *      liste de personne avec le nom correspondant
     * @throws SQLException
     */
    public static ArrayList<Personne> findByName(String name) throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Personne WHERE nom = ?;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.setString(1, name);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        ArrayList<Personne> res = new ArrayList<Personne>();
        // s'il y a un resultat
        while (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            Personne p = new Personne(nom,prenom);
            p.id = id;
            res.add(p);
        }
        return res;
    }

    /**
     * ajoute this a la table personne
     * @throws SQLException
     */
    public void save() throws SQLException {
        if (this.id == -1){
            this.saveNew();
        }else {
            this.update();
        }
    }

    /**
     * ajoute une nouvelle ligne a la table personne
     * @throws SQLException
     */
    private void saveNew() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        PreparedStatement prep = c.prepareStatement(SQLPrep,Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.executeUpdate();
        final var resultSet = prep.getGeneratedKeys();
        if (resultSet.next()) {
            this.id = resultSet.getInt(1);
        }

    }

    /**
     * mets a jour une ligne existante de la table personne
     * @throws SQLException
     */
    private void update() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = c.prepareStatement(SQLprep);
        prep.setString(1, this.nom);
        prep.setString(2, this.prenom);
        prep.setInt(3, this.id);
        prep.execute();
    }

    /**
     * supprime une personne de la table personne
     * @throws SQLException
     */
    public void delete() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "DELETE FROM Personne WHERE id = ?;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.setInt(1, this.id);
        prep1.execute();
        this.id = -1;
    }

    /**
     * creer la table personne dans la base
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        PreparedStatement s = c.prepareStatement(SQLPrep);
        s.execute();
    }

    /**
     * supprime la table personne de la base
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "DROP TABLE Personne";
        PreparedStatement s = c.prepareStatement(SQLPrep);
        s.execute();
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     *
     * @return
     *      l'id de la personne this
     */
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
}
