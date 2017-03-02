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
import tikape.runko.domain.Alue;
import tikape.runko.domain.Kayttaja;
import tikape.runko.domain.Keskustelu;

/**
 *
 * @author paavo
 */
public class KeskusteluDao implements Dao<Keskustelu, Integer> {
    private Database database;
    
    public KeskusteluDao(Database database) {
        this.database = database;
    }
    

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next())
            return null;
    
        int id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        int alueID = rs.getInt("alue_id");
        
        rs.close();
        stmt.close();
        connection.close();
        
        return new Keskustelu(id, otsikko, alueID);
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Keskustelu> etsiAlueenKeskustelut(int id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE alue_id = ?");
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        List<Keskustelu> keskustelut = new ArrayList<>();
        
        while (rs.next()) {
            int keskusteluID = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            int alueID = rs.getInt("alue_id");
            
            keskustelut.add(new Keskustelu(keskusteluID, otsikko, alueID));
        }
        
        rs.close();
        stmt.close();
        connection.close();
        return keskustelut;
    }
    
}
