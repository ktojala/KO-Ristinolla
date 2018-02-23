package koristinolla.ko.ristinolla;

import java.util.Scanner;

/**
 * Luokka Kayttoliittyma on ristinollapelin tekstikäyttöliittymä.
 */
public class Kayttoliittyma {
    
        private Scanner lukija = new Scanner(System.in);
        private int optio;         // pelaaja (1) vai kone (2) konetta vastaan
    
        
/**
* Luokan Kayttoliittyma tyhjä konstruktori
*/
    public Kayttoliittyma() {
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
        System.out.println("aika ajoin aputulosteita tyyliin 'i evalstat 1  -1000'");
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
* Tämä metodi luo ja aloittaa uuden 3D-Ristinolla -pelin. 
*/
    public void aloitaPeli() {
    
        String aloita = "1";
        while (aloita.equals("1")) {

            aloita = this.kysyUudesta();
            if (aloita.equals("1")) {
                System.out.println("");
                System.out.println("RISTINOLLA");
                System.out.println("");
                System.out.println(" Pelaa tietokonetta vastaan     (1)");
                System.out.println(" Tietokone tietokonetta vastaan (2)");
                System.out.println("");
                System.out.print("Valintasi (1-2)? "); 
                this.optio = 1;
                Peli uuspeli = new Peli(this.optio);   
                uuspeli.aloitaPeli1(this);
            }
        } 
    }
 
    
/**
* Tämä metodi luo ja aloittaa uuden 3D-Ristinolla -pelin.
* 
* @return uusi peli "1" vai lopeta "2"
*/
    public String kysyUudesta() {
            System.out.println("Haluatko pelata uuden pelin?");
            System.out.println("Kyllä - 1");
            System.out.println("Ei    - 2");
            String aloita = lukija.nextLine();
            return aloita;
    }
    

/**
* Palautetaan pelioptio
* @return optio  pelaaja (1) vai kone (2) konetta vastaa
*/
    public int getOptio() {
        return this.optio;
    }
    
    
/**
 * Metodi kysyy pelaajan siirron ja tarkistaa sen kelvollisuuden.
 * Siirto "0" tarkoittaa että pelaaja haluaa lopettaa pelin tähän.
 * 
 * @param kuutio pelistring
 * @return palautetaan siirto pelistringin paikkanumerona
 */
    public int pelaajanSiirto(Pelikuutio kuutio) {

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

            kuutio.tulostaPst();

            System.out.print("Siirtosi: ");
            luku = Integer.parseInt(lukija.nextLine());
            
            if (luku == 0) return 0;
            
            tt = luku / 100;
            rr = (luku - (tt * 100)) / 10;
            ss = luku % 10;

            switch (tt)
            {
                case 1 :
                    if ((rr > 0) && (rr < 4) && (ss > 0) && (ss < 4)) {
                        
                        if (kuutio.getMerkki(0,rr - 1,ss - 1) == ' ') {
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

                        if (kuutio.getMerkki(1,rr - 1,ss - 1) == ' ') {
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

                        if (kuutio.getMerkki(2,rr - 1,ss - 1) == ' ') {    
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

        int paikkaNro = 1+(tt-1)*9+(rr-1)*3+ss-1;
        return paikkaNro;
    }
}