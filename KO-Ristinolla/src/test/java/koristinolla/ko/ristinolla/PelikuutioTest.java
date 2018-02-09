package koristinolla.ko.ristinolla;

/*
 * Test update Feb 8, 2018, at 1540.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PelikuutioTest {
    
    public Pelikuutio tek;
    public char [] ps;
    
//    @BeforeClass
//    public static void setUpClass() {
//    }
    
//    @AfterClass
//    public static void tearDownClass() {
//    }
    
    @Before
    public void setUp() {
        
//        String s = "                            ";
//        ps = new char[28];
//        ps = s.toCharArray();
        tek = new Pelikuutio(3);
 //       char [] tek;
    }


//    @After
//    public void tearDown() {
//    }

    
//    public PelikuutioTest() {
//        assertEquals(tek.lkm, 3);
//    }

    
    /**
     * Test of getGetTasoT1, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT1() {
        System.out.println("getGetTasoT1");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT1();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of getGetTasoT2, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT2() {
        System.out.println("getGetTasoT2");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT2();
        assertArrayEquals(expResult, result); 
    }    
    

    /**
     * Test of getGetTasoT3, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT3() {
        System.out.println("getGetTasoT3");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT3();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of setMerkki, of class Pelikuutio.
     */
    @Test
    public void testSetMerkki() {
        System.out.println("setMerkki");
        tek.setMerkki('x',2);
        char[][] expResult = new char[][]{{' ','x',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT1();
        assertArrayEquals(expResult, result);
        
        expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        result = tek.getTasoT2();
        assertArrayEquals(expResult, result);
        
        result = tek.getTasoT3();
        assertArrayEquals(expResult, result);
        
        tek.setMerkki(' ',2);
        result = tek.getTasoT1();
        assertArrayEquals(expResult, result);
    }
    
    
    /**
     * Test of getPst, of class Pelikuutio.
     */
    @Test
    public void testGetPst() {
        System.out.println("getPst");
        char[] expResult = new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
        char[] result = tek.getPst();
        assertArrayEquals(expResult, result);
    }

    
}
