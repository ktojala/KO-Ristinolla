
package koristinolla.ko.ristinolla;

import java.util.Scanner;

/**
 * Luokka Peli toimii ristinollapelin tekstikäyttöliittymänä.
 */
public class Peli {
    private int optio;        // pelaaja (1) vai kone (2) konetta vastaan
    private Pelikuutio kuutio; // pstringiä vastaava char array
    private boolean peliohi;  // true, jos peli on loppuun pelattu!
    private char vuoro;       // onko vuoro (o) vai (x)
                              // tarvitaan sitten kun kone pelaa konetta vastaan
    private Scanner lukija = new Scanner(System.in);
    private Tekoaly taly1;     // yksittäinen tekoäly
//    private Tekoaly taly2;     // toinen tekoäly

    
/**
* Luokan Kayttoliittyma tyhjä konstruktori
*/
    public Peli() {
        this.peliohi = false;
        this.kuutio = new Pelikuutio();
//        this.kuutio = new Pelikuutio(3);  // kokomitta mukana
    }


/**
* Tulostetaan alkutekstejä
*/
    public void alkuEsittely() {

        Scanner lukija = new Scanner(System.in);
        
        System.out.println("RISTINOLLA");
        System.out.println("");
        System.out.println(" Pelaa tietokonetta vastaan     (1)");
        System.out.println(" Tietokone tietokonetta vastaan (2)");
        System.out.println("");
        System.out.print("Valintasi (1-2)? ");
        System.out.println("( toiminto 2 ei vielä käytössä )");
        System.out.println("                 ( siirrytään suoraan peliin!   )");
        System.out.println("");
        System.out.println("HUOM - ohjelma on kehitysvaiheessa: tulosteessa esiintyy  ");
        System.out.println("aika ajoin aputulosteita tyyliin 'i tulos stat 1  -1000 0'");
        System.out.println("----------------------------------------------------------");
        System.out.println("");
        System.out.println("");
        
        this.optio = 1;
//      int opt = Integer.parseInt(lukija.nextLine());
//        while ((opt < 1) || (opt > 2)) {
//            System.out.print("Valintasi (1-2)? ");
//            this.optio = Integer.parseInt(lukija.nextLine());
//        }
    }

    
/**
* Pelin avaus
*/
    public void aloitaPeli() {

        this.taly1 = new Tekoaly(this.kuutio);
        pelaa();
    }


/**
* Tämä metodi pelaa yhden 3D-Ristinolla -pelin. 
* Peli jatkuu kunnes muuttuja peliohi saa arvon true.
* Jokainen pelikierros on yksi kierros while-loopissa.
* Lopuksi julistetaan lopputulos.
*/
    public void pelaa() {
           
        while (!peliohi) {

            this.vuoro = 'o';
            pelaajanSiirto();   // ensin kysytään pelaajan siirto
            
            this.kuutio.tulostaPst();
            
            if ((this.kuutio.nollaSuora(this.kuutio.getTasoT1())) ||  
                (this.kuutio.nollaSuora(this.kuutio.getTasoT2())) ||
                (this.kuutio.nollaSuora(this.kuutio.getTasoT3())) ) 
                {
                System.out.println("");
                System.out.println("Onneksi olkoon, voitit koneen!");
                System.out.println("");
                peliohi = true;
                this.kuutio.tulostaPst();
            }
            
            this.vuoro = 'x';     
            
//  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
            if (!this.kuutio.tyhjia()) {
                peliohi = true;
                System.out.println("");
                System.out.println("TASAPELI");
                System.out.println("");
            }

            if (!peliohi) {
                this.taly1.talysiirto(this.kuutio);
                
                if ((this.kuutio.ristiSuora(this.kuutio.getTasoT1())) ||
                    (this.kuutio.ristiSuora(this.kuutio.getTasoT2())) ||
                    (this.kuutio.ristiSuora(this.kuutio.getTasoT3()))) {
                    System.out.println("");
                    System.out.println("Kone voitti!");
                    System.out.println("");
                    this.peliohi = true;
                    this.kuutio.tulostaPst();
                }
            }
        }
    }


/**
 * Metodi kysyy pelaajan siirron, tarkistaa sen kelvollisuuden ja tallettaa
 * siirron luokkamuuttujaan this.taulu1
 */
    public void pelaajanSiirto() {

        boolean okSiirto = false;  // onko pelaajan siirto kelvollinen
        int luku;
        int tt = 0;  // taso
        int rr = 0;  // rivi
        int ss = 0;  // sarake

        while (!okSiirto) {
            System.out.println("");
            System.out.println("Anna siirtosi (o) muodossa (taso rivi sarake)");
            System.out.println("Esim. syote: 112  (= ylätaso 1, rivi 1, sarake 2)");
            System.out.println("");

            this.kuutio.tulostaPst();

            System.out.print("Siirtosi: ");
            luku = Integer.parseInt(lukija.nextLine());
            tt = luku / 100;
            rr = (luku - (tt * 100)) / 10;
            ss = luku % 10;

            switch (tt)
            {
                case 1 :
                    if ((rr > 0) && (rr < 4) && (ss > 0) && (ss < 4)) {
                        
                        if (this.kuutio.getMerkki(0,rr - 1,ss - 1) == ' ') {
                            okSiirto = true;
                        } else {
                            System.out.println("- Epäkelpo siirto - ruutu jo varattu!");
                        }
                        break;
                    } else {
                        System.out.println("- Epäkelpo ruutu!");
                    }
                case 2 :
                    if ((rr > 0) && (rr < 4) && (ss > 0) && (ss < 4)) {

                        if (this.kuutio.getMerkki(1,rr - 1,ss - 1) == ' ') {
                            okSiirto = true;
                        } else {
                            System.out.println("- Epäkelpo siirto - ruutu jo varattu!");
                        }
                    } else {
                        System.out.println("- Epäkelpo ruutu!");
                    }
                    break;
                case 3 :
                    if ((rr > 0) && (rr < 4) && (ss > 0) && (ss < 4)) {

                        if (this.kuutio.getMerkki(2,rr - 1,ss - 1) == ' ') {    
                            okSiirto = true;
                        } else {
                            System.out.println("- Epäkelpo siirto - ruutu jo varattu!");
                        }
                    } else {
                        System.out.println("- Epäkelpo ruutu!");
                    }
                    break;
                    
                default:
                    System.out.println("- Epäkelpo ruutu!");
            }
        }
        switch (tt)
            {
                case 1 :
                    this.kuutio.setMerkki('o',0,rr - 1,ss - 1);
                    break;
                case 2 :
                    this.kuutio.setMerkki('o',1,rr - 1,ss - 1);
                    break;
                case 3 :
                    this.kuutio.setMerkki('o',2,rr - 1,ss - 1);
                    break;
                default:
                    break;
            }
    }
    
}