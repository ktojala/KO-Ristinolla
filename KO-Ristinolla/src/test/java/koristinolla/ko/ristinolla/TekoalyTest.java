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
    private int vaativuus;     // montako siirtoa eteenpäin tutkitaan
    private int pelilaji;      // kumpi laji 3D-ristinollasta
    private int[] stat;       // paras tulos siirtovaihtoehdosta
    private int[] tulosstat;  // siirtojen paremmuuksien arviointeja varten
    
    private char[] pst;      // pstringiä vastaava char array
    private int ruutuNro;    // kertoo ruudun jota parhaillaan simuloidaan
                             // kun pelipuuta evaluoidaan
    // private Random rd;       // myöhempää kehittelyä varten
    private boolean peliohi; // true, jos peli on loppuun pelattu!

    
    /**
     * Peli  alkuasetukset luokan Tekoaly testeihin
     */
    @Before
    public void setUp() {
//        Peli peli = new Peli();
        this.kuutio = new Pelikuutio();  // tyhja pelikuutio
        char merkki = 'x';
        this.vaativuus = 3;
        this.pelilaji = 1;
        this.taly1 = new Tekoaly(kuutio, merkki, this.vaativuus, this.pelilaji);

    }


    /**
     * Test of getmunMerkki, of class Tekoaly.
     */
    @Test
    public void testGetmunMerkki() {
        System.out.println("Tekoaly: getmunMerkki");
        char result = taly1.getmunMerkki();
        char expResult  = 'x';
        assertEquals(expResult, result); 
    }

  
    /**
     * Test of etsiParas, of class Tekoaly.
     */
    @Test
    public void testEtsiParas() {
        System.out.println("Tekoaly: etsiParas");
        int[] taulu = new int[28];
        taulu[2]=5;
        taulu[5]=3;
        taulu[8]=-1;
        taulu[18]=0;
        this.taly1.setEvalstat(taulu);
        int result = this.taly1.etsiParas();
        int expResult = 2;
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of talysiirto, of class Tekoaly.
     * Testataan kaksi perustilannetta, joissa iso etu tai iso riski
     */
    @Test
    public void testTalysiirto() {
        System.out.println("Tekoaly: talysiirto");
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
     * Test of isoEtu, of class Tekoaly.
     * Selvitetään onko tekoalylla (x) iso etu
     */
    @Test
    public void testIsoEtu() {
        System.out.println("Tekoaly: isoEtu");
        this.kuutio.setMerkki('x', 1);
        this.kuutio.setMerkki('x', 2);        
        this.kuutio.setMerkki(' ', 3);  
        this.kuutio.setMerkki(' ', 4);
        this.kuutio.setMerkki(' ', 5);
        int result = this.taly1.isoEtu();
        int expResult  = 3;
        assertEquals(expResult, result); 

        this.kuutio.setMerkki(' ', 1);
        this.kuutio.setMerkki('x', 5);
        result = this.taly1.isoEtu();
        expResult  = 8;
        assertEquals(expResult, result);
 
        this.kuutio.setMerkki(' ', 2);
        result = this.taly1.isoEtu();
        expResult  = -1;
        assertEquals(expResult, result);
        
        this.kuutio.setMerkki(' ', 5);
        result = this.taly1.isoEtu();
        expResult  = -1;
        assertEquals(expResult, result); 

        this.kuutio.setMerkki('o', 1);
        this.kuutio.setMerkki('o', 2); 
        result = this.taly1.isoEtu();
        expResult  = -1;
        assertEquals(expResult, result); 
    }

    
    /**
     * Test of isoRiski, of class Tekoaly.
     * Selvitetään onko tekoalylla (x) iso riski
     */
    @Test
    public void testIsoRiski() {
        System.out.println("Tekoaly: isoRiski");
        this.kuutio.setMerkki('o', 1);
        this.kuutio.setMerkki('o', 2); 
        int result = this.taly1.isoRiski();
        int expResult  = 3;
        assertEquals(expResult, result); 

        this.kuutio.setMerkki(' ', 1);
        this.kuutio.setMerkki('o', 5); 
        result = this.taly1.isoRiski();
        expResult  = 8;
        assertEquals(expResult, result);

        this.kuutio.setMerkki(' ', 2);
        result = this.taly1.isoRiski();
        expResult  = -1;
        assertEquals(expResult, result);
        
        this.kuutio.setMerkki(' ', 5);
        result = this.taly1.isoRiski();
        expResult  = -1;
        assertEquals(expResult, result); 

        this.kuutio.setMerkki('x', 1);
        this.kuutio.setMerkki('x', 10);
        result = this.taly1.isoRiski();
        expResult  = -1;
        assertEquals(expResult, result); 
    }

    
 /**
     * Test of teeRivi, of class Tekoaly.
     * Testataan syntyykö oikea string pelikuution paikkaluvuista
     */
    @Test
    public void testTeeRivi() {
        System.out.println("Tekoaly: teerivi");
        this.kuutio.setMerkki('x', 1);
        this.kuutio.setMerkki(' ', 2);
        this.kuutio.setMerkki('o', 3);
        String result = this.taly1.teeRivi(1,2,3);
        String expResult  = "x o";
        assertEquals(expResult, result); 
    }
    
    
    /**
     * Test of rivipisteet, of class Tekoaly.
     * Testataan syntyykö oikea string pelikuution paikkaluvuista
     */
    @Test
    public void testRivipisteet() {
        System.out.println("Tekoaly: rivipisteet");
        int result = this.taly1.rivipisteet("xxx");
        int expResult  = 600;
        assertEquals(expResult, result);
        
        result = this.taly1.rivipisteet("x x");
        expResult  = 25;
        assertEquals(expResult, result);

        result = this.taly1.rivipisteet(" o ");
        expResult  = -1;
        assertEquals(expResult, result);
    }

}
