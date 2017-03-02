/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author paavo
 */
public class Viesti {
    private int id;
    private int kayttajaID;
    private int keskusteluID;
    private String teksti;
    //private datetime aika;
    
    public Viesti(int id, int kayttajaID, int keskusteluID, String teksti) {
        this.id = id;
        this.kayttajaID = kayttajaID;
        this.keskusteluID = keskusteluID;
        this.teksti = teksti;
    }
    
    public String getTeksti() {
        return this.teksti;
    }
}
