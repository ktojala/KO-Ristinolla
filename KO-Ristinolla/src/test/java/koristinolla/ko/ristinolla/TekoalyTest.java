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

public class TekoalyTest {
    
    public Tekoaly tek;
    
//    @BeforeClass
//    public static void setUpClass() {
//    }
    
//    @AfterClass
//    public static void tearDownClass() {
//    }
    
    @Before
    public void setUp() {
        tek = new Tekoaly(3);
    }


//    @After
//    public void tearDown() {
//    }

    
//    public TekoalyTest() {
//        assertEquals(tek.lkm, 3);
//    }

    
    /**
     * Test of pelaa getGetTasoT1, of class Tekoaly.
     */
    @Test
    public void testGetTasoT1() {
        System.out.println("getGetTasoT1");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT1();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of pelaa getGetTasoT1, of class Tekoaly.
     */
    @Test
    public void testGetTasoT2() {
        System.out.println("getGetTasoT2");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT2();
        assertArrayEquals(expResult, result); 
    }    
    

    /**
     * Test of pelaa getGetTasoT1, of class Tekoaly.
     */
    @Test
    public void testGetTasoT3() {
        System.out.println("getGetTasoT3");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = tek.getTasoT3();
        assertArrayEquals(expResult, result); 
    }

    /**
     * Test of pelaa method, of class Tekoaly.
     */


    /**
     * Test of evaluoi method, of class Tekoaly.
     */


    /**
     * Test of risti method, of class Tekoaly.
     */


    /**
     * Test of nolla method, of class Tekoaly.
     */


    /**
     * Test of ristiSuora method, of class Tekoaly.
     */


    /**
     * Test of nollaSuora method, of class Tekoaly.
     */


    /**
     * Test of tyhjia method, of class Tekoaly.
     */


    /**
     * Test of tulosta method, of class Tekoaly.
     */


    /**
     * Test of muunna method, of class Tekoaly.
     */


    /**
     * Test of etsijaAsetaParas method, of class Tekoaly.
     */


    /**
     * Test of riski method, of class Tekoaly.
     */


    /**
     * Test of etu method, of class Tekoaly.
     */


    /**
     * Test of pelaajanSiirto method, of class Tekoaly.
     */

    
}
