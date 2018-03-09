package koristinolla.ko.ristinolla;

import java.util.Random;

/**
 * Luokka Tekoaly on tekoälypelaaja
 */
public class Tekoaly {

    private Pelikuutio kuutio;    // pstringiä vastaava char array
    private int[] evalStat;       // evaluoinnin heuristinen tulostaulu
    private final int maxSyvyys;  // kuinka syvälle evaluoinnissa mennään
    private final int pelilaji;   // 3D-ristinollan pelilaji
    private final char munMerkki; // tämän tekoälyn pelimerkki (o) tai (x)
    private final Random rd;      // tämä antaa hieman vaihtelua tekoälyn peliin
    private boolean ekaSiirto;    // true, jos tekoälyn (o) ensimmäinen siirto

    
/**
* Luokan Tekoaly konstruktori
* 
* @param kuutio sisältää pelitilanteen
* @param merkki tämän tekoälyn pelimerkki
* @param vaativuus montako siirtoa eteenpäin pelipuu tutkii
* @param pelilaji 3D-ristinolla keskikuutiolla (1) tai ilman (2)
*/
    public Tekoaly(Pelikuutio kuutio, char merkki, int vaativuus, int pelilaji) {

        this.evalStat  = new int[28];  // päätason siirtojen evaluointitaulu
        this.maxSyvyys = vaativuus;    // oletusarvo 3
        this.pelilaji = pelilaji;      // pelilaji 1 tai 2, oletusarvo 1
        this.munMerkki = merkki;       // tämän tekoälyn pelimerkki
        this.kuutio = kuutio;          // pelikuutio eli pelistring
        this.ekaSiirto = true;         // 2. tekoälyn avaussiirtoa varten
        this.rd = new Random();        // 2. tekoälyn avaussiirto arvotaan
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
*  Tämä metodi asettaa annetun evalstat-taulukon. Hyödyllinen testeissä.
* 
* @param taulu evaluointitaulu testausta varten
*/
    public void setEvalstat(int[] taulu) {
        this.evalStat = taulu;
    }
    
    
/**
*  Metodi etsii heuristisen pelipuun avulla tekoälylle parhaan siirron. 
* 
* @param kuutio sisältää pelitilanteen
* 
* @return tekoalyn seuraava pelisiirto
*/
    public int talysiirto(Pelikuutio kuutio) {

// Tekoaäly 2 eli 'o'-merkillä pelaava tekoäly arpoo oman aloitussiirtonsa

        int paikkanro;
        if (this.ekaSiirto && this.munMerkki=='o') {
            paikkanro = rd.nextInt(27)+1;
            paikkanro = 11;
            
            if (this.pelilaji == 2) {    // pelilajissa 2 paikka 14 ei käytössä
                while (paikkanro == 14) paikkanro = rd.nextInt(27)+1;
            }
            
            this.ekaSiirto = false;
            tulostaKoneenSiirto(this.munMerkki,paikkanro);
            return paikkanro;
        }
        
        this.kuutio = kuutio;

// Tutkitaan onko mahdollista voittaa peli yhdellä siirrolla
        paikkanro = isoEtu();
        if (paikkanro != -1) {
            tulostaKoneenSiirto(this.munMerkki,paikkanro);
            return paikkanro;
        }
        
// Tutkitaan voiko vastapuoli voittaa pelin yhdellä siirrolla, estetään se
        paikkanro = isoRiski();
        if (paikkanro != -1) {
            tulostaKoneenSiirto(this.munMerkki,paikkanro);
            return paikkanro;
        }

// Etsitään paras siirto heuristisen pelipuun avulla
        for (int i = 1; i < 28; i++) {
            this.evalStat[i] = 0;
            if (this.kuutio.getMerkki(i) != ' ') {
                this.evalStat[i] = 999999;   // 999999 ei tutkita;
                continue;
            } 
            
            if (this.munMerkki=='x')
            {
                this.kuutio.setMerkki(this.munMerkki,i);
                this.evalStat[i] +=  uusNolla(1,this.kuutio);      
            } else {                    // munMerkki on 'o'
                this.kuutio.setMerkki(this.munMerkki,i);
                this.evalStat[i] +=  uusRisti(1,this.kuutio);      
            }
             this.kuutio.setMerkki(' ',i);
        }

        paikkanro = etsiParas();
        tulostaKoneenSiirto(this.munMerkki,paikkanro);
        return paikkanro;
    }


/**
* Metodi etsii evalstat taulukosta parhaan siirron
* @return tekoalyn selvittämä paras siirto 
*/
    public int etsiParas() {

        int ind =1;
        int korkein = -10000;
        for (int i = 1; i < 28; i++) {

            if (this.evalStat[i] != 999999) {

                if (this.evalStat[i] > korkein) {
                    korkein = this.evalStat[i];
                    ind = i;
                }
            }
//            System.out.println("merkki i eval " + this.munMerkki + " " + i + " " + this.evalStat[i]);
        }
        return ind;
    }

    
/**
* Metodi tarkistaa, onko tekoälyn omalla puolella mahdollisuus voittaa
* suoraan yhdellä siirrolla. Tämä on käytettävä hyväksi.
* Miksi tämä metodi? Minimax-menetelmän uusRisti ja uusNolla joissakin  
* tilanteissa eivät osaa arvottaa suoraa voittomahdollisuutta oikein.
* 
* @return palauttaa varman "pakkosiirtoruudun" tai -1 jos sellaista ei ole
*/
    public int isoEtu() {
        
        char c = this.munMerkki;       
        return tarkistaRivit(c); 
    }

   
/**
* Metodi tarkistaa, onko vastapuolella mahdollisuus voittaa suoraan yhdellä 
* siirrolla. Tämä on estettävä.
* Miksi tämä metodi? Minimax-menetelmän uusRisti ja uusNolla joissakin  
* tilanteissa eivät osaa arvottaa suoraa voittomahdollisuutta oikein.
* 
* @return palauttaa varman "pakkosiirtoruudun" tai -1 jos sellaista ei ole
*/
    public int isoRiski() {
    
        char c = this.munMerkki;
        char c2;
        
        if (c=='x') { 
            c2 = 'o'; 
        } else {
            c2 = 'x';
        }     
        return tarkistaRivit(c2);
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
// * @param tasokoodi tutkittavana oleva 3x3 taso
// * @param tila pelitilanne tasoruudukolla
* @param kuutio pelitilanne pelistringissä
* 
* @return palautus: minimax-algoritmin mukainen palautus
*/
    public int uusNolla(int syvyys, Pelikuutio kuutio) {
        
        if (syvyys == this.maxSyvyys) {

            return laskePisteet(kuutio);
            
        } else {     // tutkitaan seuraava syvyystaso
            
            int[] evalStat1  = new int[28];  // Tämän kerroksen evaluointi      
            for (int i = 0; i < 28; i++) {
                evalStat1[i] = 0; // **
            }

            for (int i = 1; i < 28; i++) {
                if (kuutio.getMerkki(i) == ' ') {
                    kuutio.setMerkki('o',i);
                    evalStat1[i] = uusRisti(syvyys+1, kuutio);
                    kuutio.setMerkki(' ',i);
                } else {
                    evalStat1[i] = 999999;
                }
            }

// Minimax-algoritmissa tekoäly valitsee parilliselta syvyystasolta palautetuista 
// arvoista pienimmän (myworst) ja parittomalta syvyystasolta palautetuista 
// arvoista suurimman (mybest). (Seuraavat 5 riviä vain kehitysvaiheeseen.)

//            System.out.println("mM Syvyys " + this.munMerkki + " " + syvyys);
//            for (int i = 1; i < 28; i++) {
//                System.out.println("i ev(i) "+ i + " " + evalStat1[i]);
//            }
//             kuutio.tulostaTama(kuutio);

            int palautus;
            if (syvyys %2 == 1) {   // Pariton taso palauttaa pienimmän
                int pienin = 9999;
                for (int i = 1; i < 28; i++) {
                    if (evalStat1[i] != 999999) {
                        if (evalStat1[i] < pienin) pienin = evalStat1[i];
                    }
                }
                palautus = pienin;
            } else {                // Parillinen taso palauttaa suurimman
                int suurin = -9999;
                for (int i = 1; i < 28; i++) {
                    if (evalStat1[i] != 999999) {
                        if (evalStat1[i] > suurin) suurin = evalStat1[i];
                    }    
                } 
                palautus = suurin;
            }
//            System.out.println("uR palauttaa " +  palautus);  
            return palautus; 
        }
    }
    

/**
*  Ristin pelivuoro pelipuussa (heuristinen versio)
* 
* @param syvyys kuinka syvällä pelipuussa ollaan?
// * @param tasokoodi tutkittavana oleva 3x3 taso
// * @param tila pelitilanne tietyllä 3x3 ruudukolla 
* @param kuutio pelitilanne kuutiossa 
* 
* @return palautus: minimax-algoritmin mukainen palautus
*/
    public int uusRisti(int syvyys, Pelikuutio kuutio) {

        if (syvyys == this.maxSyvyys) {

            return laskePisteet(kuutio);
  
        } else {     // tutkitaan seuraava syvyystaso
            
            int[] evalStat1  = new int[28];  // Tämän kerroksen evaluointi
            for (int i = 0; i < 28; i++) {
                evalStat1[i] = 0;
            }

            for (int i = 1; i < 28; i++) {
                if (kuutio.getMerkki(i) == ' ') {
                    kuutio.setMerkki('x',i);
                    evalStat1[i] = uusNolla(syvyys+1, kuutio);
                    kuutio.setMerkki(' ',i);
                } else {
                    evalStat1[i] = 999999;
                }
            }

// Minimax-algoritmissa tekoäly valitsee parilliselta syvyystasolta palautetuista 
// arvoista pienimmän (myworst) ja parittomalta syvyystasolta palautetuista 
// arvoista suurimman (mybest). (Seuraavat 5 riviä vain kehitysvaiheeseen.)

//            System.out.println("Syvyys " + this.munMerkki + " " +  syvyys);
//            for (int i = 1; i < 28; i++) {
//                System.out.println("i ev(i) "+ i + " " + evalStat1[i]);
//            }
//            kuutio.tulostaTama(kuutio);

            int palautus;
            if (syvyys %2 == 1) {     // Pariton taso palauttaa pienimmän
                int pienin = 9999;
                for (int i = 1; i < 28; i++) {
                    if (evalStat1[i] != 999999) {
                        if (evalStat1[i] < pienin) pienin = evalStat1[i];
                    }
                }
                palautus = pienin;
            } else {                 // Parillinen taso palauttaa suurimman
                int suurin = -9999;
                for (int i = 1; i < 28; i++) {
                    if (evalStat1[i] != 999999) {
                        if (evalStat1[i] > suurin) suurin = evalStat1[i];
                    }  
                }
                palautus = suurin;
            }
//            System.out.println("uR palauttaa " +  palautus);
            return palautus;
        }  
    }


/**
*  Muodostetaan kolmen merkin merkkijono
* 
* @param m1 merkin 1 paikka
* @param m2 merkin 2 paikka
* @param m3 merkin 3 paikka
* 
* @return kolmesta merkistä rakennettu String
*/
    public String teeRivi(int m1, int m2, int m3) {
        
        StringBuilder sb = new StringBuilder();
        sb.append(kuutio.getMerkki(m1));
        sb.append(kuutio.getMerkki(m2));
        sb.append(kuutio.getMerkki(m3));

        return sb.toString();
    }

    
/**
*  Metodi laskee pelitilanteen kokonaisarvon
*  Pelilaji 2 ei tutki keskikuutiota 14
* 
* @param kuutio pelitilanne kuutiossa 
* 
* @return pisteet: tämän pelitilanteen kokonaisarvo
*/
    public int laskePisteet(Pelikuutio kuutio) {
        int pisteet = 0;
            // ylataso
                pisteet += rivipisteet(teeRivi(1,2,3)); 
                pisteet += rivipisteet(teeRivi(4,5,6));
                pisteet += rivipisteet(teeRivi(7,8,9));
                pisteet += rivipisteet(teeRivi(1,4,7)); 
                pisteet += rivipisteet(teeRivi(2,5,8));
                pisteet += rivipisteet(teeRivi(3,6,9));
                pisteet += rivipisteet(teeRivi(1,5,9));
                pisteet += rivipisteet(teeRivi(3,5,7));  
                
            // keskitaso
                pisteet += rivipisteet(teeRivi(10,11,12)); 
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(13,14,15));
                pisteet += rivipisteet(teeRivi(16,17,18));
                pisteet += rivipisteet(teeRivi(10,13,16)); 
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(11,14,17));
                pisteet += rivipisteet(teeRivi(12,15,18));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(10,14,18));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(12,14,16)); 
                
            // alataso
                pisteet += rivipisteet(teeRivi(19,20,21)); 
                pisteet += rivipisteet(teeRivi(22,23,24));
                pisteet += rivipisteet(teeRivi(25,26,27));
                pisteet += rivipisteet(teeRivi(19,22,25)); 
                pisteet += rivipisteet(teeRivi(20,23,26));
                pisteet += rivipisteet(teeRivi(21,24,27));
                pisteet += rivipisteet(teeRivi(19,23,27));
                pisteet += rivipisteet(teeRivi(21,23,25));  
                
            // vasen
                pisteet += rivipisteet(teeRivi(1,10,19)); 
                pisteet += rivipisteet(teeRivi(4,13,22));
                pisteet += rivipisteet(teeRivi(7,16,25));
                pisteet += rivipisteet(teeRivi(1,13,25)); 
                pisteet += rivipisteet(teeRivi(7,13,19));
                
            // v-keski-o
                pisteet += rivipisteet(teeRivi(2,11,20)); 
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(5,14,23));
                pisteet += rivipisteet(teeRivi(8,17,26));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(2,14,26));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(8,14,20)); 
                
            // oikea
                pisteet += rivipisteet(teeRivi(3,12,21)); 
                pisteet += rivipisteet(teeRivi(6,15,24));
                pisteet += rivipisteet(teeRivi(9,18,27));
                pisteet += rivipisteet(teeRivi(3,15,27));
                pisteet += rivipisteet(teeRivi(9,15,21)); 
                
            // kolmas suunta vain viistot, muut jo tutkittu yllä
                pisteet += rivipisteet(teeRivi(1,11,21));
                pisteet += rivipisteet(teeRivi(3,11,19));
                
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(4,14,24));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(6,14,22));
                
                pisteet += rivipisteet(teeRivi(7,17,27));
                pisteet += rivipisteet(teeRivi(9,17,25));
                
            // läpi kuution linjat
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(1,14,27));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(3,14,25));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(7,14,21));
                if (this.pelilaji==1) pisteet += rivipisteet(teeRivi(9,14,19));
        return pisteet;
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
        if (rivi.equals("xxx")) pisteet = 600;
        if (rivi.equals("xx ")) pisteet = 25;
        if (rivi.equals("x x")) pisteet = 25;
        if (rivi.equals(" xx")) pisteet = 25;
        if (rivi.equals("x  ")) pisteet = 1;
        if (rivi.equals(" x ")) pisteet = 1;
        if (rivi.equals("  x")) pisteet = 1;
        
        if (rivi.equals("ooo")) pisteet = -600;
        if (rivi.equals("oo ")) pisteet = -25;
        if (rivi.equals("o o")) pisteet = -25;
        if (rivi.equals(" oo")) pisteet = -25;
        if (rivi.equals("o  ")) pisteet = -1;
        if (rivi.equals(" o ")) pisteet = -1;
        if (rivi.equals("  o")) pisteet = -1;
        
        if (this.munMerkki == 'o') pisteet = -pisteet;
        
        return pisteet;
    }
    
    
/**
* Metodi vertaa merkkijonoa kolmeen malliriviin ja tarkistaa ovatko ne samat
* 
* @param c     merkki jolle samuutta tutkitaan
* @param rivi  tutkittava merkkirivi
* 
* @return tyhjän paikan numerokoodi jos merkkijonot ovat samat, muutoin -1
*/
    public int vertaa(char c, String rivi) {
        
        String v1,v2,v3;
        if (c == 'x') {
            v1 = " xx";
            v2 = "x x";
            v3 = "xx ";
        } else {
            v1 = " oo";
            v2 = "o o";
            v3 = "oo ";
        }
        
        if (rivi.equals(v1)) return 0;
        if (rivi.equals(v2)) return 1;
        if (rivi.equals(v3)) return 2;
        return -1;
    }
    
    

    public int[] teeArray(int n1, int n2, int n3) {
        
        int[] p = new int[3];
        p[0] = n1;
        p[1] = n2;
        p[2] = n3;
        return p;
    }
   
    
/**
* Metodin avulla tarkistetaan, onko annetulla merkillä mahdollisuus tehdä kolmen
* suora heti seuraavalla siirrolla. Tässä metodissa lähinnä valitaan tutkittavat
* rivit, jotka annetaan apemetodeille tutkittavaksi.
* 
* @param c tutkittava pelimerkki 'x' tai 'o'
* 
* @return tulos, varma "pakkosiirtoruutu" tai -1 jos sellaista ei ole
*/
    public int tarkistaRivit(char c) {

            // ylataso
            
        int[] p = teeArray(1,2,3);
        int tulos = vertaa(c,teeRivi(1,2,3));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(4,5,6);
        tulos = vertaa(c,teeRivi(4,5,6));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(7,8,9);
        tulos = vertaa(c,teeRivi(7,8,9));
        if (tulos != -1) return p[tulos];

        p = teeArray(1,4,7);
        tulos = vertaa(c,teeRivi(1,4,7));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(2,5,8);
        tulos = vertaa(c,teeRivi(2,5,8));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(3,6,9);
        tulos = vertaa(c,teeRivi(3,6,9));
        if (tulos != -1) return p[tulos];

        p = teeArray(1,5,9);
        tulos = vertaa(c,teeRivi(1,5,9));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(3,5,7);
        tulos = vertaa(c,teeRivi(3,5,7));
        if (tulos != -1) return p[tulos];
         
            // keskitaso
            
        p = teeArray(10,11,12);
        tulos = vertaa(c,teeRivi(10,11,12));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(13,14,15);
        tulos = vertaa(c,teeRivi(13,14,15));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(16,17,18);
        tulos = vertaa(c,teeRivi(16,17,18));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(10,13,16);
        tulos = vertaa(c,teeRivi(10,13,16));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(11,14,17);
        tulos = vertaa(c,teeRivi(11,14,17));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(12,15,18);
        tulos = vertaa(c,teeRivi(12,15,18));
        if (tulos != -1) return p[tulos];        
        
        p = teeArray(10,14,18);
        tulos = vertaa(c,teeRivi(10,14,18));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(12,14,16);
        tulos = vertaa(c,teeRivi(12,14,16));
        if (tulos != -1) return p[tulos]; 

            // alataso
            
        p = teeArray(19,20,21);
        tulos = vertaa(c,teeRivi(19,20,21));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(22,23,24);
        tulos = vertaa(c,teeRivi(22,23,24));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(25,26,27);
        tulos = vertaa(c,teeRivi(25,26,27));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(19,22,25);
        tulos = vertaa(c,teeRivi(19,22,25));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(20,23,26);
        tulos = vertaa(c,teeRivi(20,23,26));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(21,24,27);
        tulos = vertaa(c,teeRivi(21,24,27));
        if (tulos != -1) return p[tulos];        
        
        p = teeArray(19,23,27);
        tulos = vertaa(c,teeRivi(19,23,27));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(21,23,25);
        tulos = vertaa(c,teeRivi(21,23,25));
        if (tulos != -1) return p[tulos];
         
            // vasen
            
        p = teeArray(1,10,19);
        tulos = vertaa(c,teeRivi(1,10,19));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(4,13,22);
        tulos = vertaa(c,teeRivi(4,13,22));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(7,16,25);
        tulos = vertaa(c,teeRivi(7,16,25));
        if (tulos != -1) return p[tulos];        
        
        p = teeArray(1,13,25);
        tulos = vertaa(c,teeRivi(1,13,25));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(7,13,19);
        tulos = vertaa(c,teeRivi(7,13,19));
        if (tulos != -1) return p[tulos];
            
            // v-keski-o
            
        p = teeArray(2,11,20);
        tulos = vertaa(c,teeRivi(2,11,20));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(5,14,23);
        tulos = vertaa(c,teeRivi(5,14,23));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(8,17,26);
        tulos = vertaa(c,teeRivi(8,17,26));
        if (tulos != -1) return p[tulos];        
        
        p = teeArray(2,14,26);
        tulos = vertaa(c,teeRivi(2,14,26));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(8,14,20);
        tulos = vertaa(c,teeRivi(8,14,20));
        if (tulos != -1) return p[tulos];
        
            // oikea
            
        p = teeArray(3,12,21);
        tulos = vertaa(c,teeRivi(3,12,21));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(6,15,24);
        tulos = vertaa(c,teeRivi(6,15,24));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(9,18,27);
        tulos = vertaa(c,teeRivi(9,18,27));
        if (tulos != -1) return p[tulos];        
        
        p = teeArray(3,15,27);
        tulos = vertaa(c,teeRivi(3,15,27));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(9,15,21);
        tulos = vertaa(c,teeRivi(9,15,21));
        if (tulos != -1) return p[tulos];
        
            // kolmas suunta viistot
            
        p = teeArray(1,11,21);
        tulos = vertaa(c,teeRivi(1,11,21));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(3,11,19);
        tulos = vertaa(c,teeRivi(3,11,19));
        if (tulos != -1) return p[tulos];
        

        p = teeArray(4,14,24);
        tulos = vertaa(c,teeRivi(4,14,24));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(6,14,22);
        tulos = vertaa(c,teeRivi(6,14,22));
        if (tulos != -1) return p[tulos];
        

        p = teeArray(7,17,27);
        tulos = vertaa(c,teeRivi(7,17,27));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(9,17,25);
        tulos = vertaa(c,teeRivi(9,17,25));
        if (tulos != -1) return p[tulos];

            // läpi kuution linjat
            
        p = teeArray(1,14,27);
        tulos = vertaa(c,teeRivi(1,14,27));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(3,14,25);
        tulos = vertaa(c,teeRivi(3,14,25));
        if (tulos != -1) return p[tulos];
        
        p = teeArray(7,14,21);
        tulos = vertaa(c,teeRivi(7,14,21));
        if (tulos != -1) return p[tulos]; 
        
        p = teeArray(9,14,19);
        tulos = vertaa(c,teeRivi(9,14,19));
        if (tulos != -1) return p[tulos];
        
        return -1;
    }

}