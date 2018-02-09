package koristinolla.ko.ristinolla;


// import java.util.Random;   // Toistaiseksi tarpeeton
import java.util.Scanner;
// import java.util.TreeSet;  // Toistaiseksi tarpeeton


/**
 * Luokka Tekoaly pelaa yhden ristinollapelin.
 */
public class Tekoaly {

    private Pelikuutio kuutio; // pstringiä vastaava char array
    private int[] stat;      // paras tulos siirtovaihtoehdosta
    private int[] tulosstat; // siirtojen paremmuuksien arviointeja varten
    
    private char[] pst;      // pstringiä vastaava char array
    private int ruutuNro;    // kertoo ruudun jota parhaillaan simuloidaan
                             // kun pelipuuta evaluoidaan
    private char vuoro;      // onko vuoro (o) vai (x)
                             // vuoro: tärkeä jos kun kone pelaa konetta vastaan
    // private Random rd;       // myöhempää kehittelyä varten
    private Scanner lukija = new Scanner(System.in);
    private boolean peliohi; // true, jos peli on loppuun pelattu!

    
/**
* Luokan Tekoaly konstruktori
* 
* @param kuutio sisältää pelitilanteen
*/
    public Tekoaly(Pelikuutio kuutio) {

        this.stat   = new int[28];
        this.tulosstat = new int[28];

        this.kuutio = kuutio;
        this.pst = kuutio.getPst();
        
        this.ruutuNro = 0;
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
*  Evaluoi tutkii siirto-optiot ja tallettaa tulokset luokkamuuttujiin
*  minkä jälkeen metodi etsiParas valitsee parhaan siirron joka.
*  asetetaan pelistringiin.
* 
* @param kuutio sisältää pelitilanteen
*/
    public void talysiirto(Pelikuutio kuutio) {

        this.kuutio = kuutio;
        if (!this.peliohi) {
            int isoetu = -1;   // oletusarvo, ei varmaa voittoa
            int et;            // yksittäisen tason hakutermi 'isoetu'
                
            et = etu(0,this.kuutio.getTasoT1());
            if (et != - 1) isoetu = et;
            et = etu(1,this.kuutio.getTasoT2());
            if (et != - 1) isoetu = et;
            et = etu(2,this.kuutio.getTasoT3());
            if (et != - 1) isoetu = et;
                
            if (isoetu != -1) {
                System.out.println("isoetu " + isoetu);
                this.kuutio.setMerkki('x',isoetu);
                tulostaKoneenSiirto(isoetu);
                this.peliohi = true;
            } else {
                int isoriski = -1;  // oletusarvo, ei varmaa häviötä
                int ris;            // yksittäisen tason hakutermi 'isoriski'
                    
                ris = riski(0,this.kuutio.getTasoT1());
                if (ris != - 1) isoriski = ris;
                ris = riski(1,this.kuutio.getTasoT2());
                if (ris != - 1) isoriski = ris;
                ris = riski(2,this.kuutio.getTasoT3());
                if (ris != - 1) isoriski = ris;
                    
                if (isoriski != -1) {
                    System.out.println("isoriski " + isoriski);
                    this.kuutio.setMerkki('x',isoriski);
                    tulostaKoneenSiirto(isoriski);
                    
                } else {
                   
                    evaluoi(this.vuoro);    // TÄRKEÄ !!
                    int siirto = etsiParas();
                    this.kuutio.setMerkki('x',siirto);
                    tulostaKoneenSiirto(siirto);
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
*/
    public void evaluoi(char vuoro) {
        
        for (int i = 0; i < 28; i++) {
            this.stat[i] = 0;
            this.tulosstat[i] = -1000000;
        }

        for (int i = 1; i < 28; i++) { 

// Koneen ei tässä vaiheessa kannata laittaa ristiä tyhjään 2d-ruudukkoon jos
// toiseen 2d-ruudukkoon on avattu peli. Sen takia kaksi if-lausetta alla.

            if ((i<10) && (this.kuutio.merkiton(this.kuutio.getTasoT1()) )) continue;
            if ((i>9) && (i<19) && (this.kuutio.merkiton(this.kuutio.getTasoT2()) )) continue;
            if ((i>18) && (i<28) && (this.kuutio.merkiton(this.kuutio.getTasoT3()) )) continue;

            if (this.kuutio.getMerkki(i) == ' ') {
                this.ruutuNro = i;
                this.kuutio.setMerkki('x',i);

                if (i<10)  {
                    this.tulosstat[this.ruutuNro] = nolla(0,this.kuutio.getTasoT1(),2);
                } else if (i>18)  {
                    this.tulosstat[this.ruutuNro] = nolla(2,this.kuutio.getTasoT3(),2);
                } else {
                    this.tulosstat[this.ruutuNro] = nolla(1,this.kuutio.getTasoT2(),2);
                }
                this.kuutio.setMerkki(' ',i);
            }
        }
        
        for (int i = 1; i < 28; i++) {
            System.out.println("i tulos stat " + i + "  " + this.tulosstat[i] + " " + stat[i]);
        }
    }

    
/**
* Metodi etsii tulosstat taulukosta parhaan siirron
* @return tekoalyn selvittämä paras siirto 
*/
    public int etsiParas() {

        int korkein = -1000000;
        int ind =1;
        for (int i = 1; i < 28; i++) {
            if (this.tulosstat[i] > korkein) {
                korkein = this.tulosstat[i];
                ind = i;
            }
        }
        return ind;
    }

    
/**
*  Nollan pelivuoro pelipuusssa
* 
* @param tasokoodi tutkittavana oleva 3x3 taso
* @param tila pelitilanne tasoruudukolla
* @param taso monesko ruutu täytettävänä simulaatiossa
* 
* @return myworst: minimax-algoritmin mukainen palautus
*/
    public int nolla(int tasokoodi, char[][] tila, int taso) {
      
        if (tasokoodi == 0) {
            if ((!this.kuutio.tyhjia0()) || (this.peliohi)) {
                if (this.kuutio.ristiSuora(tila)) {
                    this.stat[this.ruutuNro]++;
                    return 1;
                }
                return 0;
            }
        } else if (tasokoodi == 1){
            if ((!this.kuutio.tyhjia1()) || (this.peliohi)) {
                if (this.kuutio.ristiSuora(tila)) {
                    this.stat[this.ruutuNro]++;
                    return 1;
                }
                return 0;
            } 
        } else if (tasokoodi == 2){
            if ((!this.kuutio.tyhjia2()) || (this.peliohi)) {
                if (this.kuutio.ristiSuora(tila)) {
                    this.stat[this.ruutuNro]++;
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
            if ((!this.kuutio.tyhjia0()) || (this.peliohi)) {
                if (this.kuutio.nollaSuora(tila)) {
                    this.stat[this.ruutuNro]--;
                    return -1;
                }
                return 0;
            }
        } else if (tasokoodi == 1) {
            if ((!this.kuutio.tyhjia1()) || (this.peliohi)) {
                if (this.kuutio.nollaSuora(tila)) {
                    this.stat[this.ruutuNro]--;
                    return -1;
                }
                return 0;
            }
        } else if (tasokoodi == 2) {
            if ((!this.kuutio.tyhjia2()) || (this.peliohi)) {
                if (this.kuutio.nollaSuora(tila)) {
                    this.stat[this.ruutuNro]--;
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
                return this.kuutio.muunna3(tasokoodi,i, 2);
            }
            if ((tila[i][0] == 'x') && (tila[i][1] == ' ') && (tila[i][2] == 'x')) {
                return this.kuutio.muunna3(tasokoodi,i, 1);
            }
            if ((tila[i][0] == ' ') && (tila[i][1] == 'x') && (tila[i][2] == 'x')) {
                return this.kuutio.muunna3(tasokoodi,i, 0);
            }
            if ((tila[0][i] == 'x') && (tila[1][i] == 'x') && (tila[2][i] == ' ')) {
                return this.kuutio.muunna3(tasokoodi,2, i);
            }
            if ((tila[0][i] == 'x') && (tila[1][i] == ' ') && (tila[2][i] == 'x')) {
                return this.kuutio.muunna3(tasokoodi,1, i);
            }
            if ((tila[0][i] == ' ') && (tila[1][i] == 'x') && (tila[2][i] == 'x')) {
                return this.kuutio.muunna3(tasokoodi,0, i);
            }
        }
        if ((tila[0][0] == ' ') && (tila[1][1] == 'x') && (tila[2][2] == 'x')) {
            return this.kuutio.muunna3(tasokoodi,0, 0);
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == ' ') && (tila[2][2] == 'x')) {
            return this.kuutio.muunna3(tasokoodi,1, 1);
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == 'x') && (tila[2][2] == ' ')) {
            return this.kuutio.muunna3(tasokoodi,2, 2);
        }
        if ((tila[2][0] == ' ') && (tila[1][1] == 'x') && (tila[0][2] == 'x')) {
            return this.kuutio.muunna3(tasokoodi,2, 0);
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == ' ') && (tila[0][2] == 'x')) {
            return this.kuutio.muunna3(tasokoodi,1, 1);
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == 'x') && (tila[0][2] == ' ')) {
            return this.kuutio.muunna3(tasokoodi,0, 2);
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
                return this.kuutio.muunna3(tasokoodi,i, 2);
            }
            if ((tila[i][0] == 'o') && (tila[i][1] == ' ') && (tila[i][2] == 'o')) {
                return this.kuutio.muunna3(tasokoodi,i, 1);
            }
            if ((tila[i][0] == ' ') && (tila[i][1] == 'o') && (tila[i][2] == 'o')) {
                return this.kuutio.muunna3(tasokoodi,i, 0);
            }
            if ((tila[0][i] == 'o') && (tila[1][i] == 'o') && (tila[2][i] == ' ')) {
                return this.kuutio.muunna3(tasokoodi,2, i);
            }
            if ((tila[0][i] == 'o') && (tila[1][i] == ' ') && (tila[2][i] == 'o')) {
                return this.kuutio.muunna3(tasokoodi,1, i);
            }
            if ((tila[0][i] == ' ') && (tila[1][i] == 'o') && (tila[2][i] == 'o')) {
                return this.kuutio.muunna3(tasokoodi,0, i);
            }
        }
        if ((tila[0][0] == ' ') && (tila[1][1] == 'o') && (tila[2][2] == 'o')) {
            return this.kuutio.muunna3(tasokoodi,0, 0);
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == ' ') && (tila[2][2] == 'o')) {
            return this.kuutio.muunna3(tasokoodi,1, 1);
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == 'o') && (tila[2][2] == ' ')) {
            return this.kuutio.muunna3(tasokoodi,2, 2);
        }
        if ((tila[2][0] == ' ') && (tila[1][1] == 'o') && (tila[0][2] == 'o')) {
            return this.kuutio.muunna3(tasokoodi,2, 0);
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == ' ') && (tila[0][2] == 'o')) {
            return this.kuutio.muunna3(tasokoodi,1, 1);
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == 'o') && (tila[0][2] == ' ')) {
            return this.kuutio.muunna3(tasokoodi,0, 2);
        }
        return -1;
    }

    
/**
* Metodi tulostaa tietokoneen tekemän siirron
* 
* @param ind paikka pelistringissä
*/
    public void tulostaKoneenSiirto(int ind) {
        System.out.println("");
        int t = 1+(ind-1)/9;
        int apu = ind-((t-1)*9)-1;
        int x = 1+apu/3;
        int y = 1+apu%3;
        System.out.println("Tietokoneen siirto (x) : "+t+x+y);
    }
}