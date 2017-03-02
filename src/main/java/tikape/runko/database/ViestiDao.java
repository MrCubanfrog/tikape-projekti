/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

/**
 *
 * @author paavo
 */
public class ViestiDao implements Dao<Viesti, Integer> {
    private Database database;
    
    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Viesti> etsiKeskustelunViestit(int id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE keskustelu_id = ?");
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        List<Viesti> viestit = new ArrayList<>();
        
        while (rs.next()) {
            int viestiID = rs.getInt("id");
            int kayttajaID = rs.getInt("kayttaja_id");
            int keskusteluID = rs.getInt("keskustelu_id");
            String teksti = rs.getString("teksti");
            
            viestit.add(new Viesti(viestiID, kayttajaID, keskusteluID, teksti));
        }
        
        rs.close();
        stmt.close();
        connection.close();
        return viestit;
    }
}
