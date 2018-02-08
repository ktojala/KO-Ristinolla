package koristinolla.ko.ristinolla;


// import java.util.Random;   // Toistaiseksi tarpeeton
import java.util.Scanner;
// import java.util.TreeSet;  // Toistaiseksi tarpeeton


/**
 * Luokka Tekoaly pelaa yhden ristinollapelin.
 */
public class Tekoaly {

    private int lkm;
    private int[] stat;      // paras tulos siirtovaihtoehdosta
    private int[] tulosstat; // siirtojen paremmuuksien arviointeja varten
    
    private String pstring;  // merkkijono joka kuvaa koko 3D-pelitilanteen
                             // yhtenä merkkijonona eli "pelistring", josta
                             // voidaan 'leikata' erilaisia 2D-taulukoita
    private char[] pst;      // pstringiä vastaava char array
    private int mones;       // luokkamuuttuja: kun ruutua 'mones' simuloidaan
                             // eli tutkitaan mihin 'x' siinä voi johtaa
    private char vuoro;      // onko vuoro (o) vai (x)
                             // vuoro: tärkeä jos kun kone pelaa konetta vastaan
    // private Random rd;       // myöhempää kehittelyä varten
    private Scanner lukija = new Scanner(System.in);
    private boolean peliohi; // true, jos peli on loppuun pelattu!

    
/**
* Luokan Tekoaly konstruktori
* 
* @param lkm ruudukon sivu koko (luultavasti turha parametri)
*/
    public Tekoaly(int lkm) {

        this.lkm = lkm;   // dimension pituus - toistaiseksi tarpeeton

        this.stat   = new int[28];
        this.tulosstat = new int[28];
        this.pst = new char[28];
        
// alustetaan tyhjät pelitaulut pelistringiin josta tehdään char-taulukko
// pst:n numerointi:    0123456789012345678901234567
        this.pstring = "                            ";
        this.pst = this.pstring.toCharArray();
        
        this.mones = 0;
        for (int i = 0; i < 28; i++) {      // vasta 2 3x3 tasoa käytössä
            this.stat[i] = 0;
            this.tulosstat[i] = -1000000;
        }

        this.peliohi = false;
    }

    
/**
*  Tämä metodi tarkistaa, oliko pelaajan siirto voittava siirto. 
*  Sitten metodi tarkistaa onko voittavia yksinkertaisia siirtoja
*  tarjolla tietokoneelle ja jos ei ole, kutsutaan metodia evaluoi.
*  Evaluoi palauttaa tietokoneen siirron, minkä jälkeen tarkistetaan
*  oliko se pelin voittava siirto.
*/
    public void pelaa() {
        
        System.out.println("testi" + this.pst);
        
        while (!peliohi) {

            this.vuoro = 'o';
            pelaajanSiirto();   // ensin kysytään pelaajan siirto
            tulostaPst(this.pst);
            
            if ((nollaSuora(getTasoT1())) ||  
                (nollaSuora(getTasoT2())) ||
                (nollaSuora(getTasoT3())) ) 
                {
                System.out.println("");
                System.out.println("Onneksi olkoon, voitit koneen!");
                System.out.println("");
                peliohi = true;
                tulostaPst(this.pst);
            }
            
            this.vuoro = 'x';

//  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
            if (!tyhjia()) {
                peliohi = true;
                System.out.println("");
                System.out.println("TASAPELI");
                System.out.println("");
            }

// Selvitetään lopuksi seuraava (yksittäinen) tietokoneen siirto
            if (!peliohi) {
                int isoetu = -1;   // oletusarvo, ei varmaa voittoa
                int et;            // yksittäisen tason hakutermi 'isoetu'
                
                et = etu(0,getTasoT1());
                if (et != - 1) isoetu = et;
                et = etu(1,getTasoT2());
                if (et != - 1) isoetu = et;
                et = etu(2,getTasoT3());
                if (et != - 1) isoetu = et;
                
                if (isoetu != -1) {
                    System.out.println("isoetu " + isoetu);
                    tulostaPst(this.pst);
                    this.pst[isoetu] = 'x';
                    peliohi = true;
                } else {
                    int isoriski = -1;  // oletusarvo, ei varmaa häviötä
                    int ris;            // yksittäisen tason hakutermi 'isoriski'
                    
                    ris = riski(0,getTasoT1());
                    if (ris != - 1) isoriski = ris;
                    ris = riski(1,getTasoT2());
                    if (ris != - 1) isoriski = ris;
                    ris = riski(2,getTasoT3());
                    if (ris != - 1) isoriski = ris;
                    
                    if (isoriski != -1) {
                        System.out.println("isoriski " + isoriski);
                        tulostaPst(this.pst);
                        this.pst[isoriski] = 'x';
                    } else {

//                        evaluoi(this.vuoro, tasoT1(this.pst));    // TÄRKEÄ !!
                        evaluoi(this.vuoro, this.pst);    // TÄRKEÄ !!
                    }
                }
                if ((ristiSuora(getTasoT1())) ||
                    (ristiSuora(getTasoT2())) ||
                    (ristiSuora(getTasoT3()))) {
                    System.out.println("");
                    System.out.println("Kone voitti!");
                    System.out.println("");
                    peliohi = true;
                    tulostaPst(this.pst);
                }
            }
        }
    }

    
/**
*  Evaluointi tapahtuu silloin, jos triviaaleja ratkaisuja pelitilanteeseen ei ollut.
*  Evaluointi käy läpi kaikki mahdolliset pelikehitykset ja niiden tulokset.
*  Seuraava mahdollinen siirto pisteytetään stat-taulukkoon.
*  Tulosstat-taulukko kertoo vain kunkin siirtovaihtoehdon parhaan lopputuloksen
*  eli onko se voitto, tappio vai tasapeli.
* 
* @param vuoro kummalla merkillä pelivuoro (myöhemmin käyttöön!)
* @param cr pelitilanne pelistringissä
*/
    public void evaluoi(char vuoro, char[] cr) {
        
        for (int i = 0; i < 28; i++) {
            this.stat[i] = 0;
            this.tulosstat[i] = -1000000;
        }

        for (int i = 1; i < 28; i++) { 

// Koneen ei tässä vaiheessa kannata laittaa ristiä tyhjään 2d-ruudukkoon jos
// toiseen 2d-ruudukkoon on avattu peli. Sen takia kaksi if-lausetta alla.

            if ((i<10) && (merkiton(getTasoT1()) )) continue;
            if ((i>9) && (i<19) && (merkiton(getTasoT2()) )) continue;
            if ((i>18) && (i<28) && (merkiton(getTasoT3()) )) continue;
            
            if (this.pst[i] == ' ') {
                this.mones = i;
                this.pst[i] = 'x';

                if (i<10)  {
                    this.tulosstat[this.mones] = nolla(0,getTasoT1(),2);
                } else if (i>18)  {
                    this.tulosstat[this.mones] = nolla(2,getTasoT3(),2);
                } else {
                    this.tulosstat[this.mones] = nolla(1,getTasoT2(),2);
                }
                this.pst[i] = ' ';
            }
        }
        
        for (int i = 1; i < 28; i++) {
            System.out.println("i tulos stat " + i + "  " + this.tulosstat[i] + " " + stat[i]);
        }

        etsijaAsetaParas();
    }

    
/**
*  Nollan pelivuoro pelipuusssa
* 
* @param tasokoodi tutkittavana oleva 3x3 taso
* @param tila pelitilanne ruudukolla
* @param taso monesko ruutu täytettävänä simulaatiossa
* 
* @return myworst: minimax-algoritmin mukainen palautus
*/
    public int nolla(int tasokoodi, char[][] tila, int taso) {
      
        if (tasokoodi == 0) {
            if ((!tyhjia0()) || (this.peliohi)) {
                if (ristiSuora(tila)) {
                    this.stat[this.mones]++;
                    return 1;
                }
                return 0;
            }
        } else if (tasokoodi == 1){
            if ((!tyhjia1()) || (this.peliohi)) {
                if (ristiSuora(tila)) {
                    this.stat[this.mones]++;
                    return 1;
                }
                return 0;
            } 
        } else if (tasokoodi == 2){
            if ((!tyhjia2()) || (this.peliohi)) {
                if (ristiSuora(tila)) {
                    this.stat[this.mones]++;
                    return 1;
                }
                return 0;
            } 
        }
        
        int myworst = 1000;
        int newval;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (tila[i][j] == ' ') {
                    tila[i][j] = 'o';
                    taso++;
                    newval = risti(tasokoodi, tila, taso);

                    if (newval < myworst) {
                        myworst = newval;
                    }
                    tila[i][j] = ' ';
                    taso--;
                }
            }
        }

        return myworst;
    }
    

/**
*  Ristin pelivuoro pelipuussa
* 
* @param tasokoodi tutkittavana oleva 3x3 taso
* @param tila pelitilanne tietyllä 3x3 ruudukolla 
* @param taso monesko ruutu täytettävänä simulaatiossa
* 
* @return mybest: minimax-algoritmin mukainen palautus
*/
    public int risti(int tasokoodi, char[][] tila, int taso) {

        if (tasokoodi == 0) {
            if ((!tyhjia0()) || (this.peliohi)) {
                if (nollaSuora(tila)) {
                    this.stat[this.mones]--;
                    return -1;
                }
                return 0;
            }
        } else if (tasokoodi == 1) {
            if ((!tyhjia1()) || (this.peliohi)) {
                if (nollaSuora(tila)) {
                    this.stat[this.mones]--;
                    return -1;
                }
                return 0;
            }
        } else if (tasokoodi == 2) {
            if ((!tyhjia2()) || (this.peliohi)) {
                if (nollaSuora(tila)) {
                    this.stat[this.mones]--;
                    return -1;
                }
                return 0;
            }
        }

        int mybest = -1000;
        int newval;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (tila[i][j] == ' ') {

                    tila[i][j] = 'x';
                    taso++;
                    newval = nolla(tasokoodi, tila, taso);
                    if (newval > mybest) {
                        mybest = newval;
//                        System.out.println("R i j " + mybest + " " + i + " " +j+" " + taso);
//                        tulosta(tila);
                    }
                    tila[i][j] = ' ';
                    taso--;
                }
            }
        }

        return mybest;
    }

    
/**
*  Metodi tarkistaa, onko merkillä 'x' ruudukossa voittolinja
* 
* @param tila tutkittava pelitilanteen 3x3 ruudukko
* 
* @return true: ruudukosta löytyy kolme ristiä peräkkäin
*/
    public boolean ristiSuora(char[][] tila) {

        for (int i = 0; i < 3; i++) {
            if ((tila[i][0] == 'x') && (tila[i][1] == 'x') && (tila[i][2] == 'x')) {
                return true;
            }
            if ((tila[0][i] == 'x') && (tila[1][i] == 'x') && (tila[2][i] == 'x')) {
                return true;
            }
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == 'x') && (tila[2][2] == 'x')) {
            return true;
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == 'x') && (tila[0][2] == 'x')) {
            return true;
        }
        return false;
    }


/**
*  Metodi tarkistaa, onko merkillä 'o' ruudukossa voittolinja
* 
* @param tila tutkittava pelitilanteen 3x3 ruudukko
* 
* @return true: ruudukosta löytyy kolme nollaa peräkkäin
*/
    public boolean nollaSuora(char[][] tila) {

        for (int i = 0; i < 3; i++) {
            if ((tila[i][0] == 'o') && (tila[i][1] == 'o') && (tila[i][2] == 'o')) {
                return true;
            }
            if ((tila[0][i] == 'o') && (tila[1][i] == 'o') && (tila[2][i] == 'o')) {
                return true;
            }
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == 'o') && (tila[2][2] == 'o')) {
            return true;
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == 'o') && (tila[0][2] == 'o')) {
            return true;
        }
        return false;
    }
    
    
/**
*  Metodi tarkistaa, onko peliruudukossa vielä tyhjiä ruutuja
* 
* @return true: ruudukon kaikissa kentissä on risti tai nolla
*/
    public boolean tyhjia() {

        for (int i = 1; i < 28; i++) {
            if (this.pst[i] == ' ') {
                return true;
            }
        }
        return false;
    }

    
/**
*  Metodi tarkistaa, onko peliruudukossa vielä tyhjiä ruutuja
* 
* @return true: ruudukon kaikissa kentissä on risti tai nolla
*/
    public boolean tyhjia0() {

        for (int i = 1; i < 10; i++) {
            if (this.pst[i] == ' ') {
                return true;
            }
        }
        return false;
    }
    
    
/**
*  Metodi tarkistaa, onko peliruudukossa vielä tyhjiä ruutuja
* 
* @return true: ruudukon kaikissa kentissä on risti tai nolla
*/
    public boolean tyhjia1() {

        for (int i = 10; i < 19; i++) {
            if (this.pst[i] == ' ') {
                return true;
            }
        }
        return false;
    }
    
    
/**
*  Metodi tarkistaa, onko peliruudukossa vielä tyhjiä ruutuja
* 
* @return true: ruudukon kaikissa kentissä on risti tai nolla
*/
    public boolean tyhjia2() {

        for (int i = 19; i < 28; i++) {
            if (this.pst[i] == ' ') {
                return true;
            }
        }
        return false;
    }

    
/**
* Metodi tarkistaa, onko annettu 3x3 peliruudukko merkitön
* 
* @param tila tutkittava pelitilanteen 3x3 ruudukko
* 
* @return true: ruudukossa ei ole merkkejä vielä
*/
    public boolean merkiton(char[][] tila) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tila[i][j] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    
    
/**
*  Metodi tulostaa peliruudukon ja pelitilanteen pelistringistä pst
* 
* @param cr pelitilanne char arrayssa
*/
    public void tulostaPst(char[] cr) {

        System.out.println("    1   2   3            1   2   3            1   2   3  ");
        System.out.println("  -------------        -------------        -------------");
        for (int i = 0; i < this.lkm; i++) {
            System.out.print(i + 1 + " | " + cr[3*i+1] + " | " + cr[3*i+2] + " | " + cr[3*i+3] + " |      ");
            System.out.print(i + 1 + " | " + cr[9+3*i+1] + " | " + cr[9+3*i+2] + " | " + cr[9+3*i+3] + " |      ");
            System.out.println(i + 1 + " | " + cr[18+3*i+1] + " | " + cr[18+3*i+2] + " | " + cr[18+3*i+3] + " |");
        }
        System.out.println("  -------------        -------------        -------------");
        System.out.println("  ---ylätaso---        ----keski----        ---alataso---");
        System.out.println("");
    }


/**
* Apumetodi muuntaa peliruudun 3D-koordinaatit yhdeksi yksiselitteiseksi 
* ja yksiulotteiseksi ruutuluvuksi
* 
* @param t kerros 3-ulotteisessa peliruudukossa
* @param x kerroksen vaakarivi 3-ulotteisen peliruudukon 2D-kuvassa
* @param y kerroksen sarake 3-ulotteisen peliruudukon 2D-kuvassa 
* 
* @return ruudun paikka yksiulotteisessa taulukossa
*/
    public int muunna3(int t, int x, int y) {
        if (t==0) return 1 + 9 * t + 3 * x + y;  // ylätaso
        if (t==1) return 1 + 9 * t + 3 * x + y;  // keskitaso
        if (t==2) return 1 + 9 * t + 3 * x + y;  // alataso
        return -1;
    }
    
    
/**
*  Metodi tutkii tulosstat taulukon ja suorittaa sen mukaaan tietokoneen siirron
*/
    public void etsijaAsetaParas() {

        int korkein = -1000000;
        int ind =1;
        for (int i = 1; i < 28; i++) {
            if (this.tulosstat[i] > korkein) {
                korkein = this.tulosstat[i];
                ind = i;
            }
        }
        this.pst[ind] = 'x';

        System.out.println("");
        System.out.println("Tietokoneen siirto (x)");

        tulostaPst(this.pst);
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

            tulostaPst(this.pst);
            System.out.print("Siirtosi: ");
            luku = Integer.parseInt(lukija.nextLine());
            tt = luku / 100;
            rr = (luku - (tt * 100)) / 10;
            ss = luku % 10;

            switch (tt)
            {
                case 1 :
                    if ((rr > 0) && (rr < 4) && (ss > 0) && (ss < 4)) {

                        if (this.pst[muunna3(0,rr - 1,ss - 1)] == ' ') {
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

                        if (this.pst[muunna3(1,rr - 1,ss - 1)] == ' ') {
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

                        if (this.pst[muunna3(2,rr - 1,ss - 1)] == ' ') {    
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
                    this.pst[muunna3(0,rr - 1,ss - 1)] = 'o';
//                    this.pst[1+(rr-1)*3+(ss-1)] = 'o';
                    break;
                case 2 :
                    this.pst[muunna3(1,rr - 1,ss - 1)] = 'o';
                    break;
                case 3 :
                    this.pst[muunna3(2,rr - 1,ss - 1)] = 'o';
                    break;
                default:
                    break;
            }
    }

    
/**
* Metodi tarkistaa, onko omalla puolella (= kone) mahdollisuus voittaa
* suoraan seuraavalla siirrolla
* 
* @param tasokoodi kyseessä oleva taso
* @param tila pelitilanne ruudukolla
* 
* @return palauttaa varman voittoruudun tai -1 jos sellaista ei ole
*/
    public int etu(int tasokoodi, char[][] tila) {

        for (int i = 0; i < 3; i++) {
            if ((tila[i][0] == 'x') && (tila[i][1] == 'x') && (tila[i][2] == ' ')) {
                return muunna3(tasokoodi,i, 2);
            }
            if ((tila[i][0] == 'x') && (tila[i][1] == ' ') && (tila[i][2] == 'x')) {
                return muunna3(tasokoodi,i, 1);
            }
            if ((tila[i][0] == ' ') && (tila[i][1] == 'x') && (tila[i][2] == 'x')) {
                return muunna3(tasokoodi,i, 0);
            }
            if ((tila[0][i] == 'x') && (tila[1][i] == 'x') && (tila[2][i] == ' ')) {
                return muunna3(tasokoodi,2, i);
            }
            if ((tila[0][i] == 'x') && (tila[1][i] == ' ') && (tila[2][i] == 'x')) {
                return muunna3(tasokoodi,1, i);
            }
            if ((tila[0][i] == ' ') && (tila[1][i] == 'x') && (tila[2][i] == 'x')) {
                return muunna3(tasokoodi,0, i);
            }
        }
        if ((tila[0][0] == ' ') && (tila[1][1] == 'x') && (tila[2][2] == 'x')) {
            return muunna3(tasokoodi,0, 0);
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == ' ') && (tila[2][2] == 'x')) {
            return muunna3(tasokoodi,1, 1);
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == 'x') && (tila[2][2] == ' ')) {
            return muunna3(tasokoodi,2, 2);
        }
        if ((tila[2][0] == ' ') && (tila[1][1] == 'x') && (tila[0][2] == 'x')) {
            return muunna3(tasokoodi,2, 0);
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == ' ') && (tila[0][2] == 'x')) {
            return muunna3(tasokoodi,1, 1);
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == 'x') && (tila[0][2] == ' ')) {
            return muunna3(tasokoodi,0, 2);
        }
        return -1;
    }

    
/**
* Metodi tarkistaa, onko vastapuolella (= pelaaja) mahdollisuus voittaa
* suoraan seuraavalla siirrolla
* 
* @param tasokoodi kyseessä oleva taso
* @param tila pelitilanne ruudukolla
* 
* @return palauttaa varman voittoruudun tai -1 jos sellaista ei ole
 */
    public int riski(int tasokoodi, char[][] tila) {

        for (int i = 0; i < 3; i++) {
            if ((tila[i][0] == 'o') && (tila[i][1] == 'o') && (tila[i][2] == ' ')) {
                return muunna3(tasokoodi,i, 2);
            }
            if ((tila[i][0] == 'o') && (tila[i][1] == ' ') && (tila[i][2] == 'o')) {
                return muunna3(tasokoodi,i, 1);
            }
            if ((tila[i][0] == ' ') && (tila[i][1] == 'o') && (tila[i][2] == 'o')) {
                return muunna3(tasokoodi,i, 0);
            }
            if ((tila[0][i] == 'o') && (tila[1][i] == 'o') && (tila[2][i] == ' ')) {
                return muunna3(tasokoodi,2, i);
            }
            if ((tila[0][i] == 'o') && (tila[1][i] == ' ') && (tila[2][i] == 'o')) {
                return muunna3(tasokoodi,1, i);
            }
            if ((tila[0][i] == ' ') && (tila[1][i] == 'o') && (tila[2][i] == 'o')) {
                return muunna3(tasokoodi,0, i);
            }
        }
        if ((tila[0][0] == ' ') && (tila[1][1] == 'o') && (tila[2][2] == 'o')) {
            return muunna3(tasokoodi,0, 0);
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == ' ') && (tila[2][2] == 'o')) {
            return muunna3(tasokoodi,1, 1);
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == 'o') && (tila[2][2] == ' ')) {
            return muunna3(tasokoodi,2, 2);
        }
        if ((tila[2][0] == ' ') && (tila[1][1] == 'o') && (tila[0][2] == 'o')) {
            return muunna3(tasokoodi,2, 0);
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == ' ') && (tila[0][2] == 'o')) {
            return muunna3(tasokoodi,1, 1);
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == 'o') && (tila[0][2] == ' ')) {
            return muunna3(tasokoodi,0, 2);
        }
        return -1;
    }
    
/**
* Metodi tuottaa pelistring-merkkijonotaulusta halutun 3x3-taulukon
* 
* @return  3x3x3 pelin ylätaso T1, joka on char[3][3]-taulukko
 */
    public char[][] getTasoT1() {
        
        char[][] taso = new char[3][3];
        for (int i=0;i<3; i++) {
            for (int j=0;j<3; j++) {
                taso[i][j] = this.pst[1+3*i+j];
            }
        }
        return taso;
    } 

    
/**
* Metodi tuottaa pelistring-merkkijonotaulusta halutun 3x3-taulukon
* 
* @return  3x3x3 pelin keskitaso T2, joka on char[3][3]-taulukko
 */
    public char[][] getTasoT2() {
        
        char[][] taso = new char[3][3];
        for (int i=0;i<3; i++) {
            for (int j=0;j<3; j++) {
                taso[i][j] = this.pst[10+3*i+j];
            }
        }
        return taso;
    } 

    
/**
* Metodi tuottaa pelistring-merkkijonotaulusta halutun 3x3-taulukon
* 
* @return  3x3x3 pelin alataso T3, joka on char[3][3]-taulukko
 */
    public char[][] getTasoT3() {
        
        char[][] taso = new char[3][3];
        for (int i=0;i<3; i++) {
            for (int j=0;j<3; j++) {
                taso[i][j] = this.pst[19+3*i+j];
            }
        }
        return taso;
    }
    
    
// Seuraava metodi ei vielä käytössä    
/**
* Metodi tuottaa pelistring-merkkijonosta tietyn 3x3-taulukon
* Metodin kutsu on tyypillisesti: tasoY3(this.pst)
* Taso Y3 muodostuu ruuduista, joilla y = 3;
* 
* @param cr 1-ulotteinen merkkijonotaulukko char[28]
* 
* @return  3x3x3 pelin ylätaso Y3, joka on char[3][3]-taulukko
 */
    public char[][] tasoY3(char[] cr) {
        
        char[][] taso = new char[3][3];
        for (int i=0;i<3; i++) {
            for (int j=0;j<3; j++) {
                taso[i][j] = cr[3+3*i+j*9];
            }
        }
        return taso;
    } 

}