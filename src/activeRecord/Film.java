package activeRecord;

import javax.swing.plaf.FileChooserUI;
import java.sql.*;
import java.util.List;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    public Film(String t, Personne p ){
        this.titre = t;
        this.id = -1;
        this.id_real = p.getId();
    }

    private Film(String t, int id, int idR){
        this.titre = t;
        this.id = id;
        this.id_real = idR;
    }

    /**
     * cree une film
     * @param id
     *      id correspondant au film voulre
     * @return
     *      un objet personne correspondant au film voulue, null si inexistant
     */
    public static Film findById(int id) throws SQLException {
        //personne a retourner
        Film p = null;

        Connection c = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Film WHERE id=?;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("titre");
            int id_rea = rs.getInt("id_rea");
            int i = rs.getInt("id");
            p = new Film(nom,i,id_rea);
            p.id = i;
        }
        return p;
    }

    /**
     * rechercher les film d'un realisateur
     * @param p
     *      realisateur dont on recherche les film
     * @return
     *      list des film du realisateur
     * @throws SQLException
     */
    public static List<Film> findByRealisateur(Personne p) throws SQLException {
        //personne a retourner
        List<Film> res = null;

        Connection c = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM Film WHERE id_rea=?;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.setInt(1, p.getId());
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("titre");
            int id_rea = rs.getInt("id_rea");
            int i = rs.getInt("id");
            Film f  = new Film(nom,i,id_rea);
            res.add(f);
        }
        return res;
    }

    /**
     * recupere le realisateur
     * @return
     *      le realisateur suos forme d'un  objet Personne
     * @throws SQLException
     */
    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    /**
     * creer la table Film dans la base
     * @throws SQLException
     */
    public static void createTable() throws SQLException {
        //table Personne dois exister pour creer Film
        try {
            Personne.createTable();
        }catch (SQLException q){
            System.out.println("creation table film : table personne deja creer");
        }
        //creation de la table Film
        Connection c = DBConnection.getConnection();
        String SQLPrep = "CREATE TABLE `Film` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `titre` varchar(40) NOT NULL,\n" +
                "  `id_rea` int(11) DEFAULT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n";
        PreparedStatement s = c.prepareStatement(SQLPrep);
        s.execute();
    }

    /**
     * supprime la table Film de la base
     * @throws SQLException
     */
    public static void deleteTable() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "DROP TABLE Film";
        PreparedStatement s = c.prepareStatement(SQLPrep);
        s.execute();
    }

    /**
     * ajoute this a la table Film
     * @throws SQLException
     */
    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id == -1){
            this.saveNew();
        }else {
            this.update();
        }
    }

    /**
     * ajoute une nouvelle ligne a la table Film
     * @throws SQLException
     */
    private void saveNew() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1){
            throw new RealisateurAbsentException();
        }
        Connection c = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Film (titre,id_rea) VALUES (?,?);";
        int i = Statement.RETURN_GENERATED_KEYS;
        this.id = i;
        PreparedStatement prep = c.prepareStatement(SQLPrep, i);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.executeUpdate();
    }

    /**
     * mets a jour une ligne existante de la table Film
     * @throws SQLException
     */
    private void update() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1){
            throw new RealisateurAbsentException();
        }
        Connection c = DBConnection.getConnection();
        String SQLprep = "update Personne set titre=?, id_rea=? where id=?;";
        PreparedStatement prep = c.prepareStatement(SQLprep);
        prep.setString(1, this.titre);
        prep.setInt(2, this.id_real);
        prep.setInt(3, this.id);
        prep.execute();
    }

    /**
     * supprime un film de la table Film
     * @throws SQLException
     */
    public void delete() throws SQLException {
        Connection c = DBConnection.getConnection();
        String SQLPrep = "DELETE * FROM Film WHERE id = ?;";
        PreparedStatement prep1 = c.prepareStatement(SQLPrep);
        prep1.setInt(1, this.id);
        prep1.execute();
        this.id = -1;
    }
}
