/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

public class Alue {
    private int id;
    private String otsikko;
    private String kuvaus;
    
    public Alue(int id, String otsikko, String kuvaus) {
        this.id = id;
        this.otsikko = otsikko;
        this.kuvaus = kuvaus;
    }
}