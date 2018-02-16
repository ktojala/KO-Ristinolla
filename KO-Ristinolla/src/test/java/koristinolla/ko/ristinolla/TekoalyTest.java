
package koristinolla.ko.ristinolla;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TekoalyTest {
    
    public Tekoaly taly1;
    
    private Pelikuutio kuutio; // pstringiä vastaava char array
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
        this.kuutio = new Pelikuutio();  // tyhja pelikuutio
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

  
    /**
     * Test of talysiirto, of class Tekoaly.
     * Testataan kaksi perustilannetta, joissa iso etu tai iso riski
     */
    @Test
    public void talysiirto() {
        this.kuutio.setMerkki('o', 1);
        this.kuutio.setMerkki('o', 2);
        this.peliohi = false;
        int result = this.taly1.talysiirto(this.kuutio);
        int expResult  = 3;
        assertEquals(expResult, result); 
        
        this.kuutio.setMerkki(' ', 1);
        this.kuutio.setMerkki(' ', 2);        
        this.kuutio.setMerkki(' ', 3);  
        this.kuutio.setMerkki('x', 4);
        this.kuutio.setMerkki('x', 5);
        result = this.taly1.talysiirto(this.kuutio);
        expResult  = 6;
        assertEquals(expResult, result);  
    }

    
    /**
     * Test of etu, of class Tekoaly.
     * Selvitetään onko tekoalylla (x) iso etu
     */
    @Test
    public void etu() {

        char[][] rivi = new char[][]{{'x','x',' '},{' ',' ',' '},{' ',' ',' '}};
        int result = this.taly1.etu(0,rivi);
        int expResult  = 3;
        assertEquals(expResult, result); 

        rivi = new char[][]{{' ','x',' '},{' ','x',' '},{' ',' ',' '}};
        result = this.taly1.etu(0,rivi);
        expResult  = 8;
        assertEquals(expResult, result);
        
        rivi = new char[][]{{' ',' ',' '},{' ','x',' '},{' ',' ',' '}};
        result = this.taly1.etu(0,rivi);
        expResult  = -1;
        assertEquals(expResult, result);
        
        rivi = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        result = this.taly1.etu(1,rivi);
        expResult  = -1;
        assertEquals(expResult, result); 
        
        result = this.taly1.etu(2,rivi);
        expResult  = -1;
        assertEquals(expResult, result); 
    }

    
    /**
     * Test of riski, of class Tekoaly.
     * Selvitetään onko tekoalylla (x) iso riski
     */
    @Test
    public void riski() {

        char[][] rivi = new char[][]{{'o','o',' '},{' ',' ',' '},{' ',' ',' '}};
        int result = this.taly1.riski(0,rivi);
        int expResult  = 3;
        assertEquals(expResult, result); 

        rivi = new char[][]{{' ','o',' '},{' ','o',' '},{' ',' ',' '}};
        result = this.taly1.riski(0,rivi);
        expResult  = 8;
        assertEquals(expResult, result);
        
        rivi = new char[][]{{' ',' ',' '},{' ','o',' '},{' ',' ',' '}};
        result = this.taly1.riski(0,rivi);
        expResult  = -1;
        assertEquals(expResult, result);
        
        rivi = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        result = this.taly1.riski(1,rivi);
        expResult  = -1;
        assertEquals(expResult, result); 
        
        result = this.taly1.riski(2,rivi);
        expResult  = -1;
        assertEquals(expResult, result); 
    }
  
}
