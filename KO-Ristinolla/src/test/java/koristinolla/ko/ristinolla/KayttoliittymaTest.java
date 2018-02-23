package koristinolla.ko.ristinolla;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KayttoliittymaTest {
    
    public Peli peli;
    private Kayttoliittyma kayttis;
    
    
    /**
     * Kayttoliittyma  alkuasetukset testejä varten
     */
    @Before
    public void setUp() {
        kayttis = new Kayttoliittyma();
        peli = new Peli(1);
    }

    
    /**
     * Test of alkuEsittely and getOptio, of class Peli.
     */
    @Test
    public void testAlkuEsittelyJaGetOptio() {
        System.out.println("alkuEsittely (tulostuu alle!) ja getOptio");
        kayttis.alkuEsittely();
        int result = kayttis.getOptio();
        int expResult  = 1;
        assertEquals(expResult, result); 
    }
    
    
}
