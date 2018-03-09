package koristinolla.ko.ristinolla;

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
        System.out.println("Pelikuutio: getPst");
        char[] expResult = new char[]{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
        char[] result = pst.getPst();
        assertArrayEquals(expResult, result);
    }
    
    
    /**
     * Test of getGetTasoT1, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT1() {
        System.out.println("Pelikuutio: getGetTasoT1");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT1();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of getGetTasoT2, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT2() {
        System.out.println("Pelikuutio: getGetTasoT2");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT2();
        assertArrayEquals(expResult, result); 
    }    
    

    /**
     * Test of getGetTasoT3, of class Pelikuutio.
     */
    @Test
    public void testGetTasoT3() {
        System.out.println("Pelikuutio: getGetTasoT3");
        char[][] expResult = new char[][]{{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
        char[][] result = pst.getTasoT3();
        assertArrayEquals(expResult, result); 
    }

    
    /**
     * Test of setMerkki, of class Pelikuutio.
     */
    @Test
    public void testSetMerkki() {
        System.out.println("Pelikuutio: setMerkki");
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
     * Test of merkkeja, of class Pelikuutio.
     */
    @Test
    public void testMerkkeja() {
        System.out.println("Pelikuutio: merkkeja");
        pst.setMerkki('x',2);
        pst.setMerkki('o',16);
        pst.setMerkki('o',17);
        int result = pst.merkkeja(pst);
        int expResult = 3;
        assertEquals(expResult, result);
        pst.setMerkki(' ',2);
        pst.setMerkki(' ',16);
        pst.setMerkki(' ',17);
    }
    
    
    /**
     * Test of merkiton, of class Pelikuutio.
     */
    @Test
    public void testMerkiton() {
        System.out.println("Pelikuutio: merkiton");
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
     * Test of muunna3, of class Pelikuutio.
     */
    @Test
    
    public void testMuunna3() {
        System.out.println("Pelikuutio: muunna3");
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
        System.out.println("Pelikuutio: ristiSuora");
        
        char[][] tila;
        tila = new char[][]{{'x',' ',' '},{'x',' ',' '},{'x',' ',' '}};
        boolean result = pst.ristiSuora(tila);
        assertTrue(result);
        
        char[][] tila2;
        tila2 = new char[][]{{' ','x',' '},{' ','x',' '},{' ','x',' '}};
        result = pst.ristiSuora(tila2);
        assertTrue(result);

        char[][] tila3;
        tila3 = new char[][]{{' ',' ','x'},{' ',' ','x'},{' ',' ','x'}};
        result = pst.ristiSuora(tila3);
        assertTrue(result);

        char[][] tila4;
        tila4 = new char[][]{{'x','x','x'},{' ',' ',' '},{' ',' ',' '}};
        result = pst.ristiSuora(tila4);
        assertTrue(result);
        
        char[][] tila5;
        tila5 = new char[][]{{' ',' ',' '},{'x','x','x'},{' ',' ',' '}};
        result = pst.ristiSuora(tila5);
        assertTrue(result);

        char[][] tila6;
        tila6 = new char[][]{{' ',' ',' '},{' ',' ',' '},{'x','x','x'}};
        result = pst.ristiSuora(tila6);
        assertTrue(result);
        
        char[][] tila7;
        tila7 = new char[][]{{'x',' ',' '},{' ','x',' '},{' ',' ','x'}};
        result = pst.ristiSuora(tila7);
        assertTrue(result);
        
        char[][] tila8;
        tila8 = new char[][]{{' ',' ','x'},{' ','x',' '},{'x',' ',' '}};
        result = pst.ristiSuora(tila8);
        assertTrue(result);
        
        char[][] tila9;
        tila9 = new char[][]{{' ','o',' '},{' ','o',' '},{' ','o',' '}};
        result = pst.ristiSuora(tila9);
        assertFalse(result);
        
        char[][] tila10;
        tila10 = new char[][]{{' ','x',' '},{' ','x',' '},{' ',' ',' '}};
        result = pst.ristiSuora(tila10);
        assertFalse(result);
        
        char[][] tila11;
        tila11 = new char[][]{{' ','x',' '},{' ','x',' '},{' ','o',' '}};
        result = pst.ristiSuora(tila11);
        assertFalse(result);
        
        char[][] tila12;
        tila12 = new char[][]{{' ','x',' '},{' ','x',' '},{' ',' ','x'}};
        result = pst.ristiSuora(tila12);
        assertFalse(result);
    }

    
    /**
     * Test of nollaSuora, of class Pelikuutio.
     */
    @Test
    public void testNollaSuora() {
        System.out.println("Pelikuutio: nollaSuora");
        
        char[][] tila;
        tila = new char[][]{{'o',' ',' '},{'o',' ',' '},{'o',' ',' '}};
        boolean result = pst.nollaSuora(tila);
        assertTrue(result);
        
        char[][] tila2;
        tila2 = new char[][]{{' ','o',' '},{' ','o',' '},{' ','o',' '}};
        result = pst.nollaSuora(tila2);
        assertTrue(result);
        
        char[][] tila3;
        tila3 = new char[][]{{' ',' ','o'},{' ',' ','o'},{' ',' ','o'}};
        result = pst.nollaSuora(tila3);
        assertTrue(result);
        
        char[][] tila4;
        tila4 = new char[][]{{'o','o','o'},{' ',' ',' '},{' ',' ',' '}};
        result = pst.nollaSuora(tila4);
        assertTrue(result);
        
        char[][] tila5;
        tila5 = new char[][]{{' ',' ',' '},{'o','o','o'},{' ',' ',' '}};
        result = pst.nollaSuora(tila5);
        assertTrue(result);

        char[][] tila6;
        tila6 = new char[][]{{' ',' ',' '},{' ',' ',' '},{'o','o','o'}};
        result = pst.nollaSuora(tila6);
        assertTrue(result);
        
        char[][] tila7;
        tila7 = new char[][]{{'o',' ',' '},{' ','o',' '},{' ',' ','o'}};
        result = pst.nollaSuora(tila7);
        assertTrue(result);
        
        char[][] tila8;
        tila8 = new char[][]{{' ',' ','o'},{' ','o',' '},{'o',' ',' '}};
        result = pst.nollaSuora(tila8);
        assertTrue(result);
        
        char[][] tila9;
        tila9 = new char[][]{{' ','x',' '},{' ','x',' '},{' ','x',' '}};
        result = pst.nollaSuora(tila9);
        assertFalse(result);
        
        char[][] tila10;
        tila10 = new char[][]{{' ','o',' '},{' ','o',' '},{' ',' ',' '}};
        result = pst.nollaSuora(tila10);
        assertFalse(result);
        
        char[][] tila11;
        tila11 = new char[][]{{' ','o',' '},{' ','o',' '},{' ','x',' '}};
        result = pst.nollaSuora(tila11);
        assertFalse(result);
        
        char[][] tila12;
        tila12 = new char[][]{{' ','o',' '},{' ','o',' '},{' ',' ','o'}};
        result = pst.nollaSuora(tila12);
        assertFalse(result);
        
    }
    
}