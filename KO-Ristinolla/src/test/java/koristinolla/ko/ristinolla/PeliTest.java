package koristinolla.ko.ristinolla;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeliTest {
    
    public Peli peli;
    private Pelikuutio kuutio;
    
    /**
     * Peli  alkuasetukset luokan Peli testeihin
     */
    @Before
    public void setUp() {
        peli = new Peli();
        kuutio = new Pelikuutio();  
    }
    
    
    /**
     * Test of getKuutio, of class Pelikuutio.
     */
    @Test
    public void testGetKuutio() {
        System.out.println("Peli: getKuutio");
        String expResult = "                            ";
        String result = this.kuutio.getPstring();
        assertEquals(expResult, result); 
    }

    
    /**
     * Test of alkuEsittely and getOptio, of class Peli.
     */
    @Test
    public void testAlkuEsittelyJaGetOptio() {
        System.out.println("alkuEsittely (tulostuu alle!) ja getOptio");
        peli.alkuEsittely();
        int result = peli.getOptio();
        int expResult  = 1;
        assertEquals(expResult, result); 
    }
    
    
    /**
     * Test of getPeliohi, of class Peli.
     */
    @Test
    public void testGetPeliohi() {
        System.out.println("Peli: getPeliohi");
        boolean expResult = false;
        boolean result = this.peli.getPeliohi();
        assertEquals(expResult, result); 
    }
    
    
    /**
     * Test of aloitaPeli, of class Peli.
     */
    @Test
    public void testAloitaPeli() {
        System.out.println("aloitaPeli");
        peli.setPeliohi(true);
        peli.aloitaPeli();
        Tekoaly res = peli.getTekoaly1();
        char result = res.getVuoro();
        char expResult  = 'x';
        assertEquals(expResult, result); 
    }
 
}