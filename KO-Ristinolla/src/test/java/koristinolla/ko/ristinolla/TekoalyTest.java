
package koristinolla.ko.ristinolla;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TekoalyTest {
    
    public Tekoaly taly1;
    
//    private Pelikuutio kuutio; // pstringiä vastaava char array
    private int[] stat;      // paras tulos siirtovaihtoehdosta
    private int[] tulosstat; // siirtojen paremmuuksien arviointeja varten
    
    private char[] pst;      // pstringiä vastaava char array
    private int ruutuNro;    // kertoo ruudun jota parhaillaan simuloidaan
                             // kun pelipuuta evaluoidaan
//    private char vuoro;      // onko vuoro (o) vai (x)
                             // vuoro: tärkeä kun kone pelaa konetta vastaan
    // private Random rd;       // myöhempää kehittelyä varten
    private boolean peliohi; // true, jos peli on loppuun pelattu!

    
    /**
     * Peli  alkuasetukset luokan Peli testeihin
     */
    @Before
    public void setUp() {
//        Peli peli = new Peli();
        Pelikuutio kuutio = new Pelikuutio();  // tyhja pelikuutio
        char vuoro = 'x';
        this.taly1 = new Tekoaly(kuutio,vuoro);
    }


    /**
     * Test of getVuoro, of class Tekoaly.
     */
    @Test
    public void testGetVuoro() {
        System.out.println("aloitaPeli");

        char result = taly1.getVuoro();
        char expResult  = 'x';
        assertEquals(expResult, result); 
    }
       
}
