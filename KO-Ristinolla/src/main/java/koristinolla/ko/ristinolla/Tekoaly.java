package koristinolla.ko.ristinolla;

import java.util.Random;

/**
 * Luokka Tekoaly on tekoälypelaaja
 */
public class Tekoaly {

    private Pelikuutio kuutio; // pstringiä vastaava char array
    private int[] evalStat;  // evaluoinnin heuristinen tulostaulu
    private int maxSyvyys;   // kuinka syvälle evaluoinnissa mennään
    
    private char[] pst;      // pstringiä vastaava char array
    private char munMerkki;  // tämän tekoälyn oma pelimerkki (o) tai (x)
    
    private Random rd;       // tämä antaa hieman vaihtelua tekoälyn peliin
    private boolean peliohi;  // true, jos peli on loppuun pelattu!
    private boolean ekaSiirto; // true, jos tekoälyn (o) ensimmäinen siirto

    
/**
* Luokan Tekoaly konstruktori
* 
* @param kuutio sisältää pelitilanteen
* @param merkki tämän tekoälyn pelimerkki
*/
    public Tekoaly(Pelikuutio kuutio, char merkki) {

        this.evalStat  = new int[28];  // **
        this.maxSyvyys = 4;
        
        this.munMerkki = merkki;
        this.kuutio = kuutio;
        this.pst = kuutio.getPst();

        this.peliohi = false;
        this.ekaSiirto = true;
        this.rd = new Random();
    }


/**
*  Tämä metodi esittelee tekoälyn.
* 
* @return tämän tekoälyn pelimerkki
*/
    public char getmunMerkki() {
        return this.munMerkki;
    }

       
/**
*  Tämä metodi asettaa annetun evalstat-taulukon.
* 
* @param taulu evaluointitaulu testausta varten
*/
    public void setEvalstat(int[] taulu) {
        this.evalStat = taulu;
    }
    
    
/**
*  Tämä metodi tarkistaa, oliko pelaajan siirto voittava siirto. 
*  Sitten metodi tarkistaa onko voittavia yksinkertaisia siirtoja
*  tarjolla tietokoneelle ja jos ei ole, aletaan tutkia siirtoja.
*  pelipuussa. Tämän jälkeen metodilla etsiParas etsitään paras
*  siirto.
* 
* @param kuutio sisältää pelitilanteen
* 
* @return tekoalyn pelisiirto
*/
    public int talysiirto(Pelikuutio kuutio) {

// Tekoaäly 2 eli 'o'-merkillä pelaava tekoäly arpoo aloitussiirtonsa

        if (this.ekaSiirto && this.munMerkki=='o') {
            int paikkanro = rd.nextInt(27)+1;
            this.ekaSiirto = false;
            return paikkanro;
        }
        
        this.kuutio = kuutio;
        
        if (!this.peliohi) {
                          
            int isoetu = -1;   // oletusarvo, ei varmaa voittoa
            int et;            // yksittäisen tason hakutermi 'isoetu'
                
            if (this.munMerkki=='x') { // allaoleva koodi alunperin ristille
                et = etu(0,this.kuutio.getTasoT1());
                if (et != - 1) isoetu = et;
                et = etu(1,this.kuutio.getTasoT2());
                if (et != - 1) isoetu = et;
                et = etu(2,this.kuutio.getTasoT3());
                if (et != - 1) isoetu = et;
            } else {                  // ristin etu on nollan riski
                et = riski(0,this.kuutio.getTasoT1());
                if (et != - 1) isoetu = et;
                et = riski(1,this.kuutio.getTasoT2());
                if (et != - 1) isoetu = et;
                et = riski(2,this.kuutio.getTasoT3());
                if (et != - 1) isoetu = et;
            }
                
            if (isoetu != -1) {
                System.out.println("isoetu " + isoetu);
                tulostaKoneenSiirto(this.munMerkki, isoetu);
                this.peliohi = true;
                return isoetu;
            } else {
                int isoriski = -1;  // oletusarvo, ei varmaa häviötä
                int ris;            // yksittäisen tason hakutermi 'isoriski'

                if (this.munMerkki=='x') { // allaoleva koodi alunperin ristille
                    ris = riski(0,this.kuutio.getTasoT1());
                    if (ris != - 1) isoriski = ris;
                    ris = riski(1,this.kuutio.getTasoT2());
                    if (ris != - 1) isoriski = ris;
                    ris = riski(2,this.kuutio.getTasoT3());
                    if (ris != - 1) isoriski = ris;
                } else {                   // ristin riski on nollan etu
                    ris = etu(0,this.kuutio.getTasoT1());
                    if (ris != - 1) isoriski = ris;
                    ris = etu(1,this.kuutio.getTasoT2());
                    if (ris != - 1) isoriski = ris;
                    ris = etu(2,this.kuutio.getTasoT3());
                    if (ris != - 1) isoriski = ris;  
                }    
                if (isoriski != -1) {
                    System.out.println("isoriski " + isoriski);
                    tulostaKoneenSiirto(this.munMerkki,isoriski);
                    return isoriski;
                    
                } else {
                    
// alla perustoimintavaihtoehto
                    
                    for (int i = 1; i < 28; i++) {
                        if (this.kuutio.getMerkki(i) != ' ') {
                            this.evalStat[i] = 999999;   // ei tutkita;
                            continue;
                        }
                        
// 999999 tarkoittaa, ettei ruutu ole mukana parhaan siirron valinnassa.
// Tekoälyn ei tässä vaiheessa kannata laittaa merkkiä tyhjään 2d-ruudukkoon jos
// toiseen 2d-ruudukkoon on avattu peli.
// Jos joku ruudukko on täynnä, ruudukon voi avata ellei kolmatta ruudukkoa ole 
// jo "avattu". Jos kolmas ruudukko on avattu, jatko on tehtävä sinne.

// (Jos kaksi muuta ruudukkoa on täynnä, ruudukon voi taas avata.)
// Tämä pohdinta koskee vain pelin 3 tason versiota 3x3 ristinollasta.
            
                        if ((i<10)  && (this.kuutio.merkiton(this.kuutio.getTasoT1())) ) {
                            this.evalStat[i] = 999999;
                            if (this.kuutio.taynna(this.kuutio.getTasoT2())) {
                                if (this.kuutio.merkiton(this.kuutio.getTasoT3())) {
                                    this.evalStat[i] = 0;
                                    continue;
                                }
                            }
                            if (this.kuutio.taynna(this.kuutio.getTasoT3())) {
                                if (this.kuutio.merkiton(this.kuutio.getTasoT2())) {
                                    this.evalStat[i] = 0;
                                    continue;
                                }
                            }
                            continue;
                        } 
                        if ((i>9) && (i<19)  && (this.kuutio.merkiton(this.kuutio.getTasoT2())) ) {
                            this.evalStat[i] = 999999;
                            if (this.kuutio.taynna(this.kuutio.getTasoT1())) {
                                if (this.kuutio.merkiton(this.kuutio.getTasoT3())) {
                                    this.evalStat[i] = 0;
                                    continue;
                                }
                            }
                            if (this.kuutio.taynna(this.kuutio.getTasoT3())) {
                                if (this.kuutio.merkiton(this.kuutio.getTasoT1())) {
                                    this.evalStat[i] = 0;
                                    continue;
                                }
                            }
                            continue;
                        } 
                        if ((i>18) && (i<28)  && (this.kuutio.merkiton(this.kuutio.getTasoT3())) ){
                            this.evalStat[i] = 999999;
                            if (this.kuutio.taynna(this.kuutio.getTasoT1())) {
                                if (this.kuutio.merkiton(this.kuutio.getTasoT2())) {
                                    this.evalStat[i] = 0;
                                    continue;
                                }
                            }
                            if (this.kuutio.taynna(this.kuutio.getTasoT2())) {
                                if (this.kuutio.merkiton(this.kuutio.getTasoT1())) {
                                    this.evalStat[i] = 0;
                                    continue;
                                }
                            }
                            continue;
                        } 

                        if (this.munMerkki=='x') {  // koodi alunperin ristille
                            if (i<10) {
                                this.kuutio.setMerkki(this.munMerkki,i);
                                this.evalStat[i] +=  uusNolla(0,0,this.kuutio.getTasoT1(),this.kuutio);      
                            } else if ((i>9) && (i<19))  {
                                this.kuutio.setMerkki(this.munMerkki,i);
                                this.evalStat[i] +=  uusNolla(0,1,this.kuutio.getTasoT2(),this.kuutio);
                            } else if ((i>18) && (i<28))  {
                                this.kuutio.setMerkki(this.munMerkki,i);
                                this.evalStat[i] +=  uusNolla(0,2,this.kuutio.getTasoT3(),this.kuutio);
                            }
                        } else {
                            if (i<10) {
                                this.kuutio.setMerkki(this.munMerkki,i);
                                this.evalStat[i] +=  uusRisti(0,0,this.kuutio.getTasoT1(),this.kuutio);      
                            } else if ((i>9) && (i<19))  {
                                this.kuutio.setMerkki(this.munMerkki,i);
                                this.evalStat[i] +=  uusRisti(0,1,this.kuutio.getTasoT2(),this.kuutio);
                            } else if ((i>18) && (i<28))  {
                                this.kuutio.setMerkki(this.munMerkki,i);
                                this.evalStat[i] +=  uusRisti(0,2,this.kuutio.getTasoT3(),this.kuutio);
                            } 
                        }
                        this.kuutio.setMerkki(' ',i);
                    }

                    int siirto = etsiParas();
                    tulostaKoneenSiirto(this.munMerkki,siirto);
                    return siirto;
                }
            }
        
        }
        return 0;   // tämä rivi ei toteudu koskaan, mutta metodi vaatii
    }


/**
* Metodi etsii tulosstat taulukosta parhaan siirron
* @return tekoalyn selvittämä paras siirto 
*/
    public int etsiParas() {

        int korkein = -10000;
        int ind =1;
        for (int i = 1; i < 28; i++) {

// annetaan pieni lisäpainotus keskiruudulle!
            if (this.evalStat[i] != 999999) {
                if (i==5) this.evalStat[5]++;
                if (i==14) this.evalStat[14]++;
                if (i==23) this.evalStat[23]++;
            
                if (this.evalStat[i] > korkein) {
                    korkein = this.evalStat[i];
                    ind = i;
                }
            }
            System.out.println("i evalstat " + i + " " + this.evalStat[i]);
        }
        for (int i = 1; i < 28; i++) {
            this.evalStat[i] = 0;
        }
        return ind;
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
* @param merkki tekoälyn pelimerkki
* @param ind paikka pelistringissä
*/
    public void tulostaKoneenSiirto(char merkki, int ind) {
        System.out.println("");
        int t = 1+(ind-1)/9;
        int apu = ind-((t-1)*9)-1;
        int x = 1+apu/3;
        int y = 1+apu%3;
        System.out.println("Tekoälyn siirto ("+merkki+") : "+t+x+y);
    }
    
    
/**
*  Nollan pelivuoro pelipuusssa (heuristinen versio)
* 
* @param syvyys kuinka syvällä pelipuussa ollaan?
* @param tasokoodi tutkittavana oleva 3x3 taso
* @param tila pelitilanne tasoruudukolla
* @param kuutio pelitilanne pelistringissä
* 
* @return myworst: minimax-algoritmin mukainen palautus
*/
    public int uusNolla(int syvyys, int tasokoodi, char[][] tila, Pelikuutio kuutio) {
        
        int lisapisteet = 0;   //
                
        if (syvyys == this.maxSyvyys) {
            if (tasokoodi == 0) {               // ylataso
                lisapisteet += rivipisteet(teeRivi(1,2,3)); 
                lisapisteet += rivipisteet(teeRivi(4,5,6));
                lisapisteet += rivipisteet(teeRivi(7,8,9));
                lisapisteet += rivipisteet(teeRivi(1,4,7)); 
                lisapisteet += rivipisteet(teeRivi(2,5,8));
                lisapisteet += rivipisteet(teeRivi(3,6,9));
                lisapisteet += rivipisteet(teeRivi(1,5,9));
                lisapisteet += rivipisteet(teeRivi(3,5,7));  
            } else if (tasokoodi == 1) {        // keskitaso
                lisapisteet += rivipisteet(teeRivi(10,11,12)); 
                lisapisteet += rivipisteet(teeRivi(13,14,15));
                lisapisteet += rivipisteet(teeRivi(16,17,18));
                lisapisteet += rivipisteet(teeRivi(10,13,16)); 
                lisapisteet += rivipisteet(teeRivi(11,14,17));
                lisapisteet += rivipisteet(teeRivi(12,15,18));
                lisapisteet += rivipisteet(teeRivi(10,14,18));
                lisapisteet += rivipisteet(teeRivi(12,14,16)); 
            } else if (tasokoodi == 2){         // alataso
                lisapisteet += rivipisteet(teeRivi(19,20,21)); 
                lisapisteet += rivipisteet(teeRivi(22,23,24));
                lisapisteet += rivipisteet(teeRivi(25,26,27));
                lisapisteet += rivipisteet(teeRivi(19,22,25)); 
                lisapisteet += rivipisteet(teeRivi(20,23,26));
                lisapisteet += rivipisteet(teeRivi(21,24,27));
                lisapisteet += rivipisteet(teeRivi(19,23,27));
                lisapisteet += rivipisteet(teeRivi(21,23,25));  
            }
            if (this.munMerkki == 'o') lisapisteet = -lisapisteet;
//            System.out.println("Nol lisäpist "+ lisapisteet );
            return lisapisteet;
            
        } else {     // tutkitaan seuraava syvyystaso
            
            int[] evalStat1  = new int[28];  // **      
            for (int i = 0; i < 28; i++) {      // vasta 3 3x3 tasoa käytössä
                evalStat1[i] = 0; // **
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tila[i][j] == ' ') {
                        tila[i][j] = 'o';
                        evalStat1[i] = uusRisti(syvyys+1,tasokoodi, tila, kuutio);
                        tila[i][j] = ' ';
                    }
                }
            }
            
            int pienin = 999999;
            for (int i = 1; i < 28; i++) {
                if (evalStat1[i] < pienin) pienin = evalStat1[i];
//                System.out.println("Nol i evals "+ i + " " + evalStat1[i]);
            }
//            System.out.println("Nol syvyys ja pienin " + syvyys + " " +pienin);
            return pienin; 
        }
    }
    

/**
*  Ristin pelivuoro pelipuussa (heuristinen versio)
* 
* @param syvyys kuinka syvällä pelipuussa ollaan?
* @param tasokoodi tutkittavana oleva 3x3 taso
* @param tila pelitilanne tietyllä 3x3 ruudukolla 
* 
* @return mybest: minimax-algoritmin mukainen palautus
*/
    public int uusRisti(int syvyys, int tasokoodi, char[][] tila, Pelikuutio kuutio) {

        int lisapisteet = 0;   //

        if (syvyys == this.maxSyvyys) {
            if (tasokoodi == 0) {               // ylataso
                lisapisteet += rivipisteet(teeRivi(1,2,3)); 
                lisapisteet += rivipisteet(teeRivi(4,5,6));
                lisapisteet += rivipisteet(teeRivi(7,8,9));
                lisapisteet += rivipisteet(teeRivi(1,4,7)); 
                lisapisteet += rivipisteet(teeRivi(2,5,8));
                lisapisteet += rivipisteet(teeRivi(3,6,9));
                lisapisteet += rivipisteet(teeRivi(1,5,9));
                lisapisteet += rivipisteet(teeRivi(3,5,7));  
            } else if (tasokoodi == 1) {        // keskitaso
                lisapisteet += rivipisteet(teeRivi(10,11,12)); 
                lisapisteet += rivipisteet(teeRivi(13,14,15));
                lisapisteet += rivipisteet(teeRivi(16,17,18));
                lisapisteet += rivipisteet(teeRivi(10,13,16)); 
                lisapisteet += rivipisteet(teeRivi(11,14,17));
                lisapisteet += rivipisteet(teeRivi(12,15,18));
                lisapisteet += rivipisteet(teeRivi(10,14,18));
                lisapisteet += rivipisteet(teeRivi(12,14,16)); 
            } else if (tasokoodi == 2){         // alataso
                lisapisteet += rivipisteet(teeRivi(19,20,21)); 
                lisapisteet += rivipisteet(teeRivi(22,23,24));
                lisapisteet += rivipisteet(teeRivi(25,26,27));
                lisapisteet += rivipisteet(teeRivi(19,22,25)); 
                lisapisteet += rivipisteet(teeRivi(20,23,26));
                lisapisteet += rivipisteet(teeRivi(21,24,27));
                lisapisteet += rivipisteet(teeRivi(19,23,27));
                lisapisteet += rivipisteet(teeRivi(21,23,25));  
            }
            if (this.munMerkki == 'x') lisapisteet = -lisapisteet;
//            System.out.println("Ris lisäpist "+ lisapisteet );
            return lisapisteet;
            
        } else {     // tutkitaan seuraava syvyystaso
            int[] evalStat1  = new int[28];  // **
            
            for (int i = 0; i < 28; i++) {      // vasta 3 3x3 tasoa käytössä
                evalStat1[i] = 0; // **
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tila[i][j] == ' ') {
                        tila[i][j] = 'x';
                        evalStat1[i] = uusNolla(syvyys+1,tasokoodi, tila, kuutio);
                        tila[i][j] = ' ';
                    }
                }
            }
            
            int suurin = -999999;
            for (int i = 1; i < 28; i++) {
                if (evalStat1[i] > suurin) suurin = evalStat1[i];
//                System.out.println("Ris i evals "+ i + " " + evalStat1[i]);
            }
//            System.out.println("Ris syvyys ja suurin " + syvyys + " " +suurin);
            return suurin;
        }  
    }


// Seuraavat kaksi metodia tulevat käyttöön 3D-heuristiikassa
/**
*  Muodostetaan kolmen merkin merkkijono
* 
* @param m1 merkin 1 paikka
* @param m2 merkin 2 paikka
* @param m3 merkin 3 paikka
* 
* @return merkeistä rakennettu String
*/
    public String teeRivi(int m1, int m2, int m3) {
        
        StringBuilder sb = new StringBuilder();
        sb.append(kuutio.getMerkki(m1));
        sb.append(kuutio.getMerkki(m2));
        sb.append(kuutio.getMerkki(m3));

        return sb.toString();
    }


/**
*  Perusheuristiikka rivin "arvolle"
* 
* @param rivi jokin 3 merkin rivi
* 
* @return pisteet: rivin pisteet
*/
    public int rivipisteet(String rivi) {
        
        int pisteet = 0;
        if (rivi.equals("xxx")) pisteet = 500;
        if (rivi.equals("xx ")) pisteet = 20;
        if (rivi.equals("x x")) pisteet = 20;
        if (rivi.equals(" xx")) pisteet = 20;
        if (rivi.equals("x  ")) pisteet = 1;
        if (rivi.equals(" x ")) pisteet = 1;
        if (rivi.equals("  x")) pisteet = 1;
        
        if (rivi.equals("ooo")) pisteet = -500;
        if (rivi.equals("oo ")) pisteet = -20;
        if (rivi.equals("o o")) pisteet = -20;
        if (rivi.equals(" oo")) pisteet = -20;
        if (rivi.equals("o  ")) pisteet = -1;
        if (rivi.equals(" o ")) pisteet = -1;
        if (rivi.equals("  o")) pisteet = -1;
        
        // muut yhdistelmät palauttavat 0
        
        return pisteet;
    }
}