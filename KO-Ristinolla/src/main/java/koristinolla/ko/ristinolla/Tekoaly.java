package koristinolla.ko.ristinolla;

import java.util.Random;   // Toistaiseksi tarpeeton
import java.util.Scanner;
import java.util.TreeSet;  // Toistaiseksi tarpeeton

/**
* Luokka Tekoaly pelaa yhden ristinollapelin.
*/
public class Tekoaly {

    private int lkm;         // ruudukon sivun pituus
    private char[][] taulu;  // pelitilanne
    private int[] stat;      // paras tulos siirtovaihtoehdosta
    private int[] tulosstat; // siirtojen paremmuuksien arviointeja varten
    private int mones;       // luokkamuuttuja: siirtoa ollaan "pelaamassa"
                             // kun tutkitaan mihin ko. siirto voi johtaa
    private char vuoro;      // onko vuoro (o) vai (x)
                             // tärkeä sitten kun kone pelaa konetta vastaan
    private Random rd;       // myöhempää kehittelyä varten
    private Scanner lukija = new Scanner(System.in);
    
    private boolean peliohi; // true, jos peli on loppuun pelattu!

/**
* Luokan Tekoaly konstruktori
* 
* @param lkm ruudukon sivu koko (luultavasti turha parametri)
*/
    public Tekoaly(int lkm) {
    
        this.lkm = lkm; 
        this.taulu = new char[this.lkm][this.lkm];
        this.stat = new int[10];
        this.tulosstat = new int[10];
        this.mones = 0;
        for (int i = 0; i < 10; i++) {
            this.stat[i] = 0;
            this.tulosstat[i] = -1000000;
        }
        
// alustetaan tyhjä pelitaulu:

        for (int i = 0; i < this.lkm; i++) {
            this.taulu[i][0] = ' ';
            this.taulu[i][1] = ' ';
            this.taulu[i][2] = ' ';
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
      
        while (!peliohi) {
            
            this.vuoro = 'o';
            pelaajanSiirto();   // ensi kysytään pelaajan siirto
            tulosta(this.taulu);
            
            if (nollaSuora(this.taulu)) {
                System.out.println("");
                System.out.println("Onneksi olkoon, voitit koneen!");
                System.out.println("");
                peliohi=true;
                tulosta(this.taulu);
            }
            this.vuoro = 'x';
            
//  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
            
            if (!tyhjia()) {
                peliohi=true;
                System.out.println("");
                System.out.println("TASAPELI");
                System.out.println("");
            }

// Selvitetään lopuksi yksittäinen tietokoneen siirto
            
            if (!peliohi) {

                int isoetu = etu(this.taulu);
                if (isoetu != -1) {
                    System.out.println("isoetu "+isoetu);
                    tulosta(this.taulu);
                    int ii = (isoetu-1)/3;
                    int jj = (isoetu-1)%3;
                    this.taulu[ii][jj]='x';
                    peliohi=true;
                } else {
                    int isoriski = riski(this.taulu);
                    if (isoriski != -1) {
                        System.out.println("isoriski "+isoriski);
                        tulosta(this.taulu);
                        int ii = (isoriski-1)/3;
                        int jj = (isoriski-1)%3;
                        this.taulu[ii][jj]='x';
                    } else {
                        
                        evaluoi(this.vuoro,this.taulu);    // ***
                    }
                }
                if (ristiSuora(this.taulu)) {
                    System.out.println("");
                    System.out.println("Kone voitti!");
                    System.out.println("");
                    peliohi=true;
                    tulosta(this.taulu);
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
* 
* @param tila pelitilanne ruudukolla
*/
    
    public void evaluoi(char vuoro,char[][] tila) {

        for (int i = 0; i < 10; i++) {
            this.stat[i] = 0;
            this.tulosstat[i] = -1000000;
        }
        
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {

                if (tila[i][j] == ' ')  {
                    this.mones = muunna(i,j);
                    tila[i][j] = 'x';
                    this.tulosstat[this.mones] = nolla(tila,2);
                    tila[i][j] = ' ';
                }
            }
        }
        
        for (int i=1;i<10;i++) {
            System.out.println("i, tulosstat, stat: " + i + "  " + this.tulosstat[i] + " " + stat[i]);
        }
        
        etsijaAsetaParas();
    }
    
/**
*  Ristin pelivuoro
* 
* @param tila pelitilanne ruudukolla
* 
* @param taso monesko ruutu täytettävänä simulaatiossa
* 
* @return mybest: minimax-algoritmin mukainen palautus
*/
    public int risti(char[][] tila, int taso) {
        
        int ind;

        if ((!tyhjia()) || (this.peliohi)) {
            if (ristiSuora(tila)) {
                this.stat[this.mones]++;
                return 1;
            }
            if (nollaSuora(tila)) {
                this.stat[this.mones]--;
                return -1;
            }
            return 0;
        }
        int mybest = -1000;
        int newval;

        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {

                if (tila[i][j] == ' ')  {

                    tila[i][j] = 'x';
                    taso++;             
                    newval = nolla(tila,taso);
                    if (newval > mybest) {
                        mybest = newval;
                    }

                    tila[i][j] = ' ';
                    taso--;    
                }
            }
        }
        return mybest;
    }

/**
*  Nollan pelivuoro
* 
* @param tila pelitilanne ruudukolla
* 
* @param taso monesko ruutu täytettävänä simulaatiossa
* 
* @return myworst: minimax-algoritmin mukainen palautus
*/
    public int nolla(char[][] tila, int taso) {

        if ((!tyhjia()) || (this.peliohi)) {
            if (ristiSuora(tila)) {
                this.stat[this.mones]++;
                return 1;
            }
            if (nollaSuora(tila)) {
                this.stat[this.mones]--;
                return -1;
            }
            return 0;
        }
        
        int myworst = 1000;
        int newval;
         
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {

                if (tila[i][j] == ' ')  {
                    tila[i][j] = 'o';
                    taso++;           
                    newval = risti(tila,taso);
                    
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
*  Metodi tarkistaa, onko merkillä 'x' ruudukossa voittolinja
* 
* @param tila pelitilanne ruudukolla
* 
* @return true: ruudukosta löytyy kolme ristiä peräkkäin
*/ 
    public boolean ristiSuora(char[][] tila) {
        
        for (int i=0;i<3;i++) {
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
* @param tila pelitilanne ruudukolla
* 
* @return true: ruudukosta löytyy kolme nollaa peräkkäin
*/
    public boolean nollaSuora(char[][] tila) {
        
        for (int i=0;i<3;i++) {
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
        
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                if (this.taulu[i][j] == ' ')  {
                    return true;
                }
            }
        }
        return false;
    }
    
/**
*  Metodi tulostaa peliruudukon ja pelitilanteen
* 
* @param tila pelitilanne ruudukolla
*/
    public void tulosta(char[][] tila) {
        
        System.out.println("    1   2   3  " );
        System.out.println("  -------------" );
        for (int i = 0; i < this.lkm; i++) {
            System.out.println(i+1 + " | " + tila[i][0] + " | " + tila[i][1] + " | " + tila[i][2]+ " |" );
        }
        System.out.println("  -------------" );
        System.out.println("");
    }

/**
* Apumetodi muuntaa peliruudun koordinaatit yhdeksi yksiselitteiseksi ruutuluvuksi
* 
* @param x vaakarivi 2-ulotteisessa peliruudukossa 
* 
* @param y sarake 2-ulotteisessa peliruudukossa 
* 
* @return ruudun paikka yksiulotteisessa taulukossa
*/
    public int muunna(int x, int y) {
        
        return(1+3*x+y);
    }
    
/**
*  Metodi tutkii tulosstat taulukon ja suorittaa sen mukaaan tietokoneen siirron
*/
    public void etsijaAsetaParas() {
        
        int korkein = -1000000;
        int ii = -1;
        int jj = -1;
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                this.mones = muunna(i,j);
                if (this.tulosstat[this.mones] > korkein) {
                        korkein = this.tulosstat[this.mones];
                        ii=i;
                        jj=j;
                }
            }
        }
        this.taulu[ii][jj] = 'x';
        System.out.println("");
        System.out.println("Tietokoneen siirto (x)");
        tulosta(this.taulu);
    }

    
    
    public int riski(char[][] tila) {

//  Metodi tarkistaa, onko vastapuolella (= pelaaja) mahdollisuus voittaa 
//  seuraavalla siirrolla ja jos on, palauttaa yhden sellaisen riskiruudun
        
        for (int i=0;i<3;i++) {
            if ((tila[i][0] == 'o') && (tila[i][1] == 'o') && (tila[i][2] == ' ')) {
                return muunna(i,2);
            }
            if ((tila[i][0] == 'o') && (tila[i][1] == ' ') && (tila[i][2] == 'o')) {
                return muunna(i,1);
            }
            if ((tila[i][0] == ' ') && (tila[i][1] == 'o') && (tila[i][2] == 'o')) {
                return muunna(i,0);
            }
            
            if ((tila[0][i] == 'o') && (tila[1][i] == 'o') && (tila[2][i] == ' ')) {
                return muunna(2,i);
            }
            if ((tila[0][i] == 'o') && (tila[1][i] == ' ') && (tila[2][i] == 'o')) {
                return muunna(1,i);
            }
            if ((tila[0][i] == ' ') && (tila[1][i] == 'o') && (tila[2][i] == 'o')) {
                return muunna(0,i);
            }
            
        }
        if ((tila[0][0] == ' ') && (tila[1][1] == 'o') && (tila[2][2] == 'o')) {
            return muunna(0,0);
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == ' ') && (tila[2][2] == 'o')) {
            return muunna(1,1);
        }
        if ((tila[0][0] == 'o') && (tila[1][1] == 'o') && (tila[2][2] == ' ')) {
            return muunna(2,2);
        }
        
        if ((tila[2][0] == ' ') && (tila[1][1] == 'o') && (tila[0][2] == 'o')) {
            return muunna(2,0);
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == ' ') && (tila[0][2] == 'o')) {
            return muunna(1,1);
        }
        if ((tila[2][0] == 'o') && (tila[1][1] == 'o') && (tila[0][2] == ' ')) {
            return muunna(0,2);
        }
        
        return -1;
    }
    
    public int etu(char[][] tila) {

//  Metodi tarkistaa, onko omalla puolella (= kone) mahdollisuus voittaa 
//  seuraavalla siirrolla ja jos on, palauttaa yhden sellaisen voittoruudun  
        
        for (int i=0;i<3;i++) {
            if ((tila[i][0] == 'x') && (tila[i][1] == 'x') && (tila[i][2] == ' ')) {
                return muunna(i,2);
            }
            if ((tila[i][0] == 'x') && (tila[i][1] == ' ') && (tila[i][2] == 'x')) {
                return muunna(i,1);
            }
            if ((tila[i][0] == ' ') && (tila[i][1] == 'x') && (tila[i][2] == 'x')) {
                return muunna(i,0);
            }
            
            if ((tila[0][i] == 'x') && (tila[1][i] == 'x') && (tila[2][i] == ' ')) {
                return muunna(2,i);
            }
            if ((tila[0][i] == 'x') && (tila[1][i] == ' ') && (tila[2][i] == 'x')) {
                return muunna(1,i);
            }
            if ((tila[0][i] == ' ') && (tila[1][i] == 'x') && (tila[2][i] == 'x')) {
                return muunna(0,i);
            }
            
        }
        if ((tila[0][0] == ' ') && (tila[1][1] == 'x') && (tila[2][2] == 'x')) {
            return muunna(0,0);
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == ' ') && (tila[2][2] == 'x')) {
            return muunna(1,1);
        }
        if ((tila[0][0] == 'x') && (tila[1][1] == 'x') && (tila[2][2] == ' ')) {
            return muunna(2,2);
        }
        
        if ((tila[2][0] == ' ') && (tila[1][1] == 'x') && (tila[0][2] == 'x')) {
            return muunna(2,0);
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == ' ') && (tila[0][2] == 'x')) {
            return muunna(1,1);
        }
        if ((tila[2][0] == 'x') && (tila[1][1] == 'x') && (tila[0][2] == ' ')) {
            return muunna(0,2);
        }
        
        return -1;
    }
    
    public void pelaajanSiirto() {
        
//  Metodi kysyy pelaajan siirron, tarkistaa sen kelvollisuuden ja tallettaa
//  siirron luokkamuuttujaan this.taulu

        boolean okSiirto = false;  // onko pelaajan siirto kelvollinen
        int luku;
        int rr = 0;
        int ss = 0;
            
        while (!okSiirto) {
            System.out.println("");
            System.out.println("Anna siirtosi (o) muodossa (rivi sarake)");
            System.out.println("Esim. syote: 12   (= rivi 1 ja sarake 2)");
            System.out.println("");
            tulosta(this.taulu);   
                
            luku = Integer.parseInt(lukija.nextLine());
            rr=luku/10;
            ss=luku%10;
                
            if ( (rr>0) && (rr<4) && (ss>0) && (ss<4) ) {
                if (this.taulu[rr-1][ss-1] == ' ') {
                    okSiirto = true;
                } else {
                    System.out.println("");
                    System.out.println("Epäkelpo siirto - ruutu jo varattu!");
                }
            } else {
                System.out.println("");
                System.out.println("Epäkelpo ruutu!");
            }
        }
        this.taulu[rr-1][ss-1] = 'o';
    }
    
}
