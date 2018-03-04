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
    private Kayttoliittyma kayttis;
    
    
    /**
     * Peli  alkuasetukset luokan Peli testeihin
     */
    @Before
    public void setUp() {
        kayttis = new Kayttoliittyma();
        peli = new Peli(1,3,1); 
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
     * Test of getOptio, of class Peli.
     */
    @Test
    public void testGetOptio() {
        System.out.println("Peli: getOptio");
        int expResult = 1;
        int result = this.peli.getOptio();
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
    public void testAloitaPeli1() {
        System.out.println("Peli: aloitaPeli1");
        peli.setPeliohi(true);
        peli.aloitaPeli1(kayttis);
        Tekoaly res = peli.getTekoaly1();
        char result = res.getmunMerkki();
        char expResult  = 'x';
        assertEquals(expResult, result); 
    }
 
}