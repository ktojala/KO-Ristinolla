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
        peli = new Peli(1,3,1);
    }

    
    /**
     * Test of getOptio, of class Kayttoliittyma.
     */
    @Test
    public void testGetOptio() {
        System.out.println("Kayttoliittyma: getOptio");
        
//        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
//        System.setIn(in);
        int result = kayttis.getOptio();

//        System.setIn(System.in);
        
        int expResult  = 1;
        assertEquals(expResult, result); 
    }
    
}
