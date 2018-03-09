package koristinolla.ko.ristinolla;

import java.util.Scanner;

/**
 * Luokka Kayttoliittyma on ristinollapelin tekstikäyttöliittymä.
 */
public class Kayttoliittyma {
    
        private Scanner lukija;
        private int optio;         // pelaaja (1) vai tekoäly (2) tekoälyä vastaan
        private int pelilaji;      // peli keskikuutiolla vai ilman
        private int vaativuus;     // tekoälyn vaativuustaso
        private boolean pelaajanEkaSiirto;  // 1. siirron ohjetta varten
    
        
/**
* Luokan Kayttoliittyma tyhjä konstruktori
*/
    public Kayttoliittyma() {
        this.optio = 1;    // oletusarvo: pelaaja tekoälyä vastaan
        this.pelilaji = 1; // oletusarvo: keskikuutio mukana pelissä
        this.vaativuus = 3;     // oletusarvo: kohtalaisen älykäs ja nopea
        this.lukija = new Scanner(System.in);
    }


    
/**
* Tulostetaan alkutekstejä
* @param luku haluttu pelilaji 1 tai 2
*/
    public void setPelilaji(int luku) {
        this.pelilaji = luku;
    }
    
        
/**
* Tulostetaan alkutekstejä
*/
    public void alkuEsittely() {

        System.out.println("");
        System.out.println("3D-RISTINOLLA");
        System.out.println("");
        System.out.println("Aito 3-ulotteinen ristinollapeli");
        System.out.println("----------------------------------------------------------");
    }
 
    
/**
* Metodi luo ja aloittaa uuden 3D-Ristinolla -pelin. 
*/
    public void aloitaPeli() {
    
        int aloita = 1;
        int itseVaiTekoAly;
        
        while (aloita==1) {
            itseVaiTekoAly = this.kysyItseVaiTekoaly();
            
            if (itseVaiTekoAly==1) {
                this.optio = 1;
                this.pelaajanEkaSiirto = true;
            }
            if (itseVaiTekoAly==2) {
                this.optio = 2;
            }
            if (itseVaiTekoAly==4) {
                System.out.println("Pelin lopetus - hyvää päivänjatkoa!");
                return;
            }
            Peli uuspeli = new Peli(this.optio, this.vaativuus, this.pelilaji);   
            uuspeli.aloitaPeli1(this);
            aloita = kysyUudestaPelista();

        } 
    }
 
    
/**
* Metodi kysyy pelaajalta, haluaako hän pelata vielä yhden pelin.
* 
* @return uusi peli "1" vai lopeta "0"
*/
    public int kysyUudestaPelista() {
        
        boolean poistu = false;
        String valinta;
        int val = 0;
        
        while (!poistu) {
            System.out.println("Haluatko pelata uuden pelin?");
            System.out.println("Kyllä           (1)");
            System.out.println("Ei              (0)");
            System.out.println("");
            System.out.print("> ");
            valinta = lukija.nextLine();
            
            switch (valinta)
            {
                case "1" :
                    val = 1;
                    poistu = true;
                    break;
                case "0" :
                    val = 0;
                    poistu = true;
                    break;
                default:
                    System.out.println("- EPÄKELPO SYÖTE -");
            }
        }
        return val;
    }

    
/**
* Metodi kysyy pelaajalta, haluaako hän pelata itse tekoälyä vastaan.
* vai laittaa kaksi tekoälyä pelaamaan toisiaan vastaan. Pelaaja voi myös
* valita muiden asetusten tekemisen.
* 
* @return pelaajan vastaus, pelaako itse tekoälyä vastaan (1) vai ei (2) tai Lopetus (4)
*/
    public int kysyItseVaiTekoaly() {
        
        boolean poistu = false;
        String valinta;
        int val = 0;
        
        while (!poistu) {
            System.out.println("");
            System.out.println("3D-RISTINOLLAN PELIVALINTA");
            System.out.println("");
            System.out.println(" Pelaa itse tekoälyä vastaan     (1)");
            System.out.println(" Laita tekoäly tekoälyä vastaan  (2)");
            System.out.println(" Muut asetukset                  (3)");
            System.out.println(" Lopeta                          (4)");
            System.out.println("");
            System.out.println("Valintasi (1-4)? ");
            System.out.print("> ");
            valinta = lukija.nextLine();
            
            switch (valinta)
            {
                case "1" :
                    val = 1;
                    poistu = true;
                    break;
                case "2" :
                    val = 2;
                    poistu = true;
                    break;
                case "3" :
                    muutAsetukset();
                    break;
                case "4" :
                    val = 4;
                    poistu = true;
                    break;
                default:
                    System.out.println("- EPÄKELPO SYÖTE -");
            }
        }
        return val;
    }
    
    
/**
* Palautetaan pelioptio
* @return optio  (pelaaja (1) vai kone (2) konetta vastaan)
*/
    public int getOptio() {
        return this.optio;
    }
    

/**
 * Metodissa pelaaja voi muuttaa muutamia tärkeitä asetuksia.
 * Metodi päivittää suoraan luokkamuuttujia
 */
    public void muutAsetukset() {
        boolean poistu = false;
        String valinta;
        while (!poistu) {
            System.out.println("");
            System.out.println("MUUT ASETUKSET");
            System.out.println("");
            System.out.println(" Pelin vaativuustaso (nyt "+this.vaativuus+")     (1)");
            System.out.println(" Pelilajin valinta   (nyt "+this.pelilaji+")     (2)");
            System.out.println(" Poistu muista asetuksista       (3)");
            System.out.println(""); 
            System.out.print("> ");
            valinta = lukija.nextLine();
        
            switch (valinta)
            {
                case "1" :
                    this.vaativuus = kysyVaativuustaso();
                    break;
                case "2" :
                    this.pelilaji = kysyPelilaji();
                    break;
                case "3" :
                    poistu = true;
                    break;
                default:
                    System.out.println("- EPÄKELPO SYÖTE -");
            }
        }
    }
    

/**
 * Metodissa pelaaja voi muuttaa muutamia tärkeitä asetuksia.
 * 
 * @return tekoälyn (tekoälyjen) älutaso lukuna
 */
    public int kysyVaativuustaso() {
        
        boolean poistu = false;
        String valinta;
        int val = 0;
        while (!poistu) {
            System.out.println("");
            System.out.println("TEKOÄLYN VAATIVUUSTASO");
            System.out.println("");
            System.out.println("Tekoälyn vaativuustaso tarkoittaa sitä, kuinka monta siirtoa");
            System.out.println("tekoäly laskee eteenpäin.  Oletusvaativuustaso on 3 siirtoa.");
            System.out.println("Voit valita vaativuustason väliltä 1..5 ");
            System.out.println("Tekoälyn miettimisaika kasvaa jyrkästi vaativuustason kasvaessa.");
            System.out.println("Vaativuustasolla 3 tekoäly on vielä suhteellisen nopea. ");
            System.out.println("");
            System.out.println(" Valitse vaativuustaso  (luku väliltä 2..5");
            System.out.println("");
            System.out.print("> ");
            valinta = lukija.nextLine();
            
            switch (valinta)
            {
                case "2" :
                    val = 2;
                    poistu = true;
                    break;
                case "3" :
                    val = 3;
                    poistu = true;
                    break;
                case "4" :
                    val = 4;
                    poistu = true;
                    break;
                case "5" :
                    val = 5;
                    poistu = true;
                    break;
                default:
                    System.out.println("- EPÄKELPO SYÖTE -");
            }
        }
        return val;
    }
    

/**
 * Metodissa pelaaja voi muuttaa pelilajia.
 * 
 * @return tekoälyn pelilaji lukuna
 */
    public int kysyPelilaji() {
        
        boolean poistu = false;
        String valinta;
        int val = 0;
        while (!poistu) {
            System.out.println("");
            System.out.println("3D-RISTINOLLAN PELILAJIT");
            System.out.println("");
            System.out.println("Valittavanasi on kaksi peliä");
            System.out.println("Pelilaji 1 tarkoittaa 3x3x3-ristinollaa, jossa pelikuution ");
            System.out.println("kaikki osakuutiot ovat pelissä mukana.");
            System.out.println("Pelilaji 2 tarkoittaa 3x3x3-ristinollaa, jossa pelikuution ");
            System.out.println("keskimmäinen osakuutio ei ole pelissä mukana.");
            System.out.println("");
            System.out.println("HUOM: Keskimmäinen osakuutio on strategisesti merkittävä,");
            System.out.println("joten pelilaji 2 saattaa olla hieman haastavampi.");
            System.out.println("");
            System.out.println(" Valinnat:");
            System.out.println(" Keskikuutio mukana         (1)");
            System.out.println(" Keskikuutio ei mukana      (2)");
            System.out.println("");
            System.out.print("> ");
            valinta = lukija.nextLine();
            
            switch (valinta)
            {
                case "1" :
                    val = 1;
                    poistu = true;
                    break;
                case "2" :
                    val = 2;
                    poistu = true;
                    break;
                default:
                    System.out.println("- EPÄKELPO SYÖTE -");
            }
        }
        return val;
    }
        
        
/**
 * Metodi kysyy pelaajan siirron ja tarkistaa sen kelvollisuuden.
 * Siirto "0" tarkoittaa että pelaaja haluaa lopettaa pelin tähän.
 * 
 * @param kuutio pelistring
 * @return palautetaan siirto pelistringin paikkanumerona
 */
    public int pelaajanSiirto(Pelikuutio kuutio) {

        boolean okSiirto = false; // kertoo, onko pelaajan siirto kelvollinen
        int luku = -1;
        int paikkaNro = 0;        // paikkanumero pelistringissä
                
        while (!okSiirto) {
            System.out.println("");
            System.out.println("Anna siirtosi (o) muodossa (taso rivi sarake)");
            System.out.println("Esim. syote: 112  ( = ylätaso 1, rivi 1, sarake 2)");
            System.out.println("Komento 0 lopettaa tämän pelin.");
            System.out.println("");

// Ensimmäisen siirron jälkeen pelitilanne tulostetaan toisaalla
            if (this.pelaajanEkaSiirto) {
                kuutio.tulostaPst();
                this.pelaajanEkaSiirto = false;
            }

            System.out.print("Siirtosi: ");
            
            boolean gotCorrect = false;
            while(!gotCorrect){
                try{
                    luku = Integer.parseInt(lukija.nextLine());
                    gotCorrect = true;
                } catch (NumberFormatException e) {
                    System.err.println("Ei validi numerosyöte. " + e.getMessage());
                    System.out.print("Siirtosi: ");
                }
            }
            
            if (luku == 0) {
                System.out.println("");
                System.out.println("Pelaajan käskystä peli lopetetaan");
                System.out.println("");
                return 0;
            } 
            
            paikkaNro = onkoSyoteOK(luku);
            if (paikkaNro != -1) {        
                if (kuutio.getMerkki(paikkaNro) == ' ') {
                    okSiirto = true;
                } else {
                    System.out.println("- Epäkelpo siirto - ruutu jo varattu!");
                }
            } 
        }
        return paikkaNro;
    }
    
    
/**
 * Metodi tutkii siirtonumeron kelvollisuuden paikkanumeroksi.
 * 
 * @param luku pelaajan syöte pelisiirroksi
 * 
 * @return -1 jos syöte ei ollut ok, muutoin paikkanumero
 */
    public int onkoSyoteOK(int luku) {
        
        int tt = luku / 100;             // taso
        int rr = (luku - (tt * 100)) / 10;   // rivi
        int ss = luku % 10;                  // sarake
        int paikkaNro = 1+(tt-1)*9+(rr-1)*3+ss-1;
        
        if ((paikkaNro < 1) || (paikkaNro > 27) || (tt < 1) || (tt > 3) || 
            (rr < 1) || (rr > 3) || (ss < 1) || (ss > 3)) {
            System.out.println("- Syöte ei vastaa mitään ruutua");
            return -1;
        }
        if ((this.pelilaji == 2) && (paikkaNro == 14)) {
            System.out.println("- Ruutu ei käytössä tässä pelissä");
            return -1;
        }
        return paikkaNro;
    }
    
    
/**
 * Metodi tulostaa yksittäisen pelin erilaisia lopputuloksia.
 * 
 * @param tulos koodi tulostusta varten
 */
    public void tulostaPelinTulos(int tulos) {
        
            switch (tulos)
            {
                case 1 :
                    System.out.println("");
                    System.out.println("Tekoäly 1 (x) voitti!");
                    System.out.println("");
                    break;
                case 2 :
                    System.out.println("");
                    System.out.println("Tekoäly 2 (o) voitti!");
                    System.out.println("");
                    break;
                case 3 :
                    System.out.println("");
                    System.out.println("TASAPELI");
                    System.out.println("");
                    break;
                case 4 :
                    System.out.println("");
                    System.out.println("Onneksi olkoon, voitit koneen!");
                    System.out.println("");
                    break;
                case 5 :
                    System.out.println("");
                    System.out.println("Tekoäly voitti!");
                    System.out.println("");
                    break;
            }
    }
    
}