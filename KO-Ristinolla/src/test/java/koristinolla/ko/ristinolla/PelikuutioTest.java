package koristinolla.ko.ristinolla;

/*
 * Test update Feb 10, 2018, at 0000.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PelikuutioTest {
    
    public Pelikuutio pst;
//    public char [] ps;
    
//    @BeforeClass
//    public static void setUpClass() {
//    }
    
//    @AfterClass
//    public static void tearDownClass() {
//    }

    /**
     * Pelikuutio  alkuasetus muuttujaan pst
     */
    @Before
    public void setUp() {
        pst = new Pelikuutio();
    }


//    @After
//    public void tearDown() {
//    }

    
    /**
     * Test of Pstring, of class Pelikuutio.
     */
    @Test
    public void testGetPstring() {
        System.out.println("Pelikuutio: getPstring");
        String expResult = "                            ";
        String result = pst.getPstring();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getPst, of class Pelikuutio.
     */
    @Test
    public void testGetPst() {
        System.out.println("getPst");
        char[] expResult = new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
        char[] result = pst.getPst();
        assertArrayEquals(expResult, result);
    }
    
    
    /**
     * Test of getGetTasoT1, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT1() {
        System.out.println("getGetTasoT1");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT1();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of getGetTasoT2, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT2() {
        System.out.println("getGetTasoT2");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT2();
        assertArrayEquals(expResult, result); 
    }    
    

    /**
     * Test of getGetTasoT3, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT3() {
        System.out.println("getGetTasoT3");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT3();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of setMerkki, of class Pelikuutio.
     */
    @Test
    public void testSetMerkki() {
        System.out.println("setMerkki");
        pst.setMerkki('x',2);
        char[][] expResult = new char[][]{{' ','x',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT1();
        assertArrayEquals(expResult, result);
        
        expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        result = pst.getTasoT2();
        assertArrayEquals(expResult, result);
        
        result = pst.getTasoT3();
        assertArrayEquals(expResult, result);
        
        pst.setMerkki(' ',2);
        result = pst.getTasoT1();
        assertArrayEquals(expResult, result);
        
        pst.setMerkki('o',2,2,0);
        expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{'o',' ',' '}};
        result = pst.getTasoT3();
        assertArrayEquals(expResult, result);
        pst.setMerkki(' ',2,2,0);
    }
    
    
    /**
     * Test of merkiton, of class Pelikuutio.
     */
    @Test
    public void testMerkiton() {
        System.out.println("merkiton");
        char[][] taso;
        taso = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        boolean result = pst.merkiton(taso);
        assertTrue(result);
        
        char[][] taso2;
        taso2 = new char[][]{{' ','o',' '},{' ',' ',' '},{' ',' ',' '}};
        result = pst.merkiton(taso2);
        assertFalse(result);
    }

    
    /**
     * Test of taynna, of class Pelikuutio.
     */
    @Test
    public void testTaynna() {
        System.out.println("taynna");
        char[][] taso;
        taso = new char[][]{{'x','x','o'},{'o','o','x'},{'x','o','x'}};
        boolean result = pst.taynna(taso);
        assertTrue(result);
        
        char[][] taso2;
        taso2 = new char[][]{{' ','o',' '},{' ',' ',' '},{' ',' ',' '}};
        result = pst.merkiton(taso2);
        assertFalse(result);
    }
    
    
    /**
     * Test of muunna3, of class Pelikuutio.
     */
    @Test
    
    public void testMuunna3() {
        System.out.println("muunna3");
        int t = 2;
        int x = 2;
        int y = 0;
        int expResult = 25;
        int result = pst.muunna3(t, x, y);
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of ristiSuora, of class Pelikuutio.
     */
    @Test
    public void testRistiSuora() {
        System.out.println("ristiSuora");
        char[][] tila;
        tila = new char[][]{{' ','x',' '},{' ','x',' '},{' ','x',' '}};
        boolean result = pst.ristiSuora(tila);
        assertTrue(result);

        char[][] tila2;
        tila2 = new char[][]{{' ','o',' '},{' ','o',' '},{' ','o',' '}};
        result = pst.ristiSuora(tila2);
        assertFalse(result);
        
        char[][] tila3;
        tila3 = new char[][]{{' ','x',' '},{' ','x',' '},{' ',' ',' '}};
        result = pst.ristiSuora(tila3);
        assertFalse(result);
    }

    
    /**
     * Test of nollaSuora, of class Pelikuutio.
     */
    @Test
    public void testNollaSuora() {
        System.out.println("nollaSuora");
        char[][] tila;
        tila = new char[][]{{' ','x',' '},{' ','x',' '},{' ','x',' '}};
        boolean result = pst.nollaSuora(tila);
        assertFalse(result);

        char[][] tila2;
        tila2 = new char[][]{{' ','o',' '},{' ','o',' '},{' ','o',' '}};
        result = pst.nollaSuora(tila2);
        assertTrue(result);
        
        char[][] tila3;
        tila3 = new char[][]{{' ','o',' '},{' ','x',' '},{' ','o',' '}};
        result = pst.nollaSuora(tila3);
        assertFalse(result);
    }
    
}