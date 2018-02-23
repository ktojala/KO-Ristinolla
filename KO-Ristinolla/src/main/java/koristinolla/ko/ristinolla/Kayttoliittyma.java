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
        this.optio = 1;              // oletusarvo
    }

    
/**
* Tulostetaan alkutekstejä
*/
    public void alkuEsittely() {

        Scanner lukija = new Scanner(System.in);
        System.out.println("");
        System.out.println("3D-RISTINOLLA");
        System.out.println("");
        System.out.println("HUOM 1 - ohjelma on kehitysvaiheessa: kyseessä ei vielä");
        System.out.println("  ole täydellinen 3x3x3 -peli vaan 3-tasoinen 3x3 -peli.");
        System.out.println("");
        System.out.println("HUOM 2 - ohjelma on kehitysvaiheessa: tulosteessa esiintyy");
        System.out.println("  aika ajoin aputulosteita tyyliin 'i evalstat 1  -1000'");
        System.out.println("----------------------------------------------------------");
    }
 
    
/**
* Tämä metodi luo ja aloittaa uuden 3D-Ristinolla -pelin. 
*/
    public void aloitaPeli() {
    
        String aloita = "1";
        String itseVaiTekoAly;
        
        while (aloita.equals("1")) {

            itseVaiTekoAly = this.kysyKoneVaiTa();
            if (itseVaiTekoAly.equals("1")) this.optio = 1;
            if (itseVaiTekoAly.equals("2")) this.optio = 2;
        
            Peli uuspeli = new Peli(this.optio);   
            uuspeli.aloitaPeli1(this);
            aloita = this.kysyUudesta();
        } 
    }
 
    
/**
* Tämä metodi kysyy pelaajalta, haluaako hän pelata vielä yhden pelin.
* 
* @return uusi peli "1" vai lopeta "2"
*/
    public String kysyUudesta() {
            System.out.println("Haluatko pelata uuden pelin?");
            System.out.println("Kyllä (1)");
            System.out.println("Ei    (2)");
            System.out.println("");
            System.out.print("> ");
            String aloita = lukija.nextLine();
            return aloita;
    }

    
/**
* Tämä metodi kysyy pelaajalta, haluaako hän pelata itse tekoälyä vastaan.
* vai laittaa kaksi tekoälyä pelaamaan toisiaan vastaan
* 
* @return pelaajan vastaus, pelaako itse tekoälyä vastaan (1) vai ei (2)
*/
    public String kysyKoneVaiTa() {
                System.out.println("");
                System.out.println("RISTINOLLA");
                System.out.println("");
                System.out.println(" Pelaa itse tekoälyä vastaan     (1)");
                System.out.println(" Laita tekoäly tekoälyä vastaan  (2)");
                System.out.println("");
                System.out.print("Valintasi (1-2)? ");
                System.out.println("");
                System.out.print("> ");
            String kumpi = lukija.nextLine();
            return kumpi;
    }
    
    
/**
* Palautetaan pelioptio
* @return optio  (pelaaja (1) vai kone (2) konetta vastaan)
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