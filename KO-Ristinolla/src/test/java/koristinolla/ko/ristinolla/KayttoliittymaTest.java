package koristinolla.ko.ristinolla;

// import java.io.ByteArrayInputStream;
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
    
    
    /**
     * Test of onkoSyoteOK, of class Kayttoliittyma.
     */
    @Test
    public void testOnkoSyoteOK() {
        System.out.println("Kayttoliittyma: onkoSyoteOK");
        int result = this.kayttis.onkoSyoteOK(7);
        int expResult  = -1;
        assertEquals(expResult, result);
        
        result = this.kayttis.onkoSyoteOK(111);
        expResult  = 1;
        assertEquals(expResult, result);
        
        result = this.kayttis.onkoSyoteOK(010);
        expResult  = -1;
        assertEquals(expResult, result);
        
        result = this.kayttis.onkoSyoteOK(-1);
        expResult  = -1;
        assertEquals(expResult, result);
        
        result = this.kayttis.onkoSyoteOK(242);
        expResult  = -1;
        assertEquals(expResult, result);
        
    }

    
}
