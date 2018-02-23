
package koristinolla.ko.ristinolla;

/**
 * Luokka Pelikuutio sisältää 3D-ristinollan ytimen eli pelistringin.
 * Pelikuutio osaa kertoa oman tilansa eri tavoin.
 */
public class Pelikuutio {

//    private int lkm;        // dimension pituus - toistaiseksi tarpeeton
    private String pstring; // merkkijono joka kuvaa koko 3D-pelitilanteen
                            // yhtenä merkkijonona eli "pelistring"
    private char[] pst;     // pstringiä vastaava char array, josta
                            // voidaan 'leikata' erilaisia 2D-taulukoita


/**
* Luokan Pelikuutio konstruktori
*/
//* @param lkm kuution kokotekijä
//    public Pelikuutio(int lkm) {
    public Pelikuutio() {
//        this.lkm = lkm;           
        this.pst = new char[28];
// alustetaan tyhjät pelitaulut pelistringiin josta tehdään char-taulukko
// pst:n numerointi:    0123456789012345678901234567
        this.pstring = "                            ";
        this.pst = this.pstring.toCharArray();
    }

    
/**
*  Metodi palauttaa pelikuution merkkijonona
* 
* @return this.pstring pelitilanne yksinkertaisena merkkijonona
*/
    public String getPstring() {
        return this.pstring;
    }
    
    
/**
*  Metodi antaa pelikuution pelistringinä pst
* 
* @return this.pst pelitilanne pelistringissä
*/
    public char[] getPst() {
        return this.pst;
    }

    
/**
*  Metodi tulostaa peliruudukon ja pelitilanteen pelistringistä pst
* 
*/
    public void tulostaPst() {

        System.out.println("    1   2   3            1   2   3            1   2   3  ");
        System.out.println("  -------------        -------------        -------------");
//        for (int i = 0; i < this.lkm; i++) {
        for (int i = 0; i < 3; i++) {
            System.out.print(i + 1 + " | " + this.pst[3*i+1] + " | " + this.pst[3*i+2] + " | " + this.pst[3*i+3] + " |      ");
            System.out.print(i + 1 + " | " + this.pst[9+3*i+1] + " | " + this.pst[9+3*i+2] + " | " + this.pst[9+3*i+3] + " |      ");
            System.out.println(i + 1 + " | " + this.pst[18+3*i+1] + " | " + this.pst[18+3*i+2] + " | " + this.pst[18+3*i+3] + " |");
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
* Metodi (kuormitettu) asettaa merkin pelistringiin halutulle paikalle
* 
* @param merkki sijoitettava merkki
* @param paikka paikka pelistringissä
 */
    public void setMerkki(char merkki, int paikka) {
        
        this.pst[paikka] = merkki;
    }

    
/**
*  Metodi asettaa merkin pelistringiin halutulle paikalle
* 
* @param merkki sijoitettava merkki
* @param t pelikuution taso
* @param x pelikuution x-taso
* @param y pelikuution y-taso
*/
    public void setMerkki(char merkki, int t, int x, int y) {
        int paikka = muunna3(t,x,y);
        this.pst[paikka] = merkki;
    }

    
/**
* Metodi (kuormitettu) kertoo pelistringin halutusta paikasta merkin
* 
* @param paikka paikka pelistringissä
* 
* @return  kerkki pelistringin paikasta paikka
 */
    public char getMerkki(int paikka) {
        
        return this.pst[paikka];
    } 


/**
* Metodi kertoo pelistringin halutusta paikasta merkin
* 
* @param t kerros 3-ulotteisessa peliruudukossa
* @param x kerroksen vaakarivi 3-ulotteisen peliruudukon 2D-kuvassa
* @param y kerroksen sarake 3-ulotteisen peliruudukon 2D-kuvassa 
* 
* @return  kerkki pelistringin paikasta paikka
 */
    public char getMerkki(int t, int x, int y) {
        int paikka = muunna3(t,x,y);
        return this.pst[paikka];
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
* Metodi tarkistaa, onko annettu 3x3 peliruudukko jo täynnä merkkejä
* 
* @param tila tutkittava pelitilanteen 3x3 ruudukko
* 
* @return true: ruudukossa ei ole tyhjia merkkeja enää
*/
    
    public boolean taynna(char[][] tila) {
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tila[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
       
    
/**
* Metodi selvittää, monessako pelikuution ruudussa on merkki
* 
* @param kuutio pelikuutio
* 
* @return montako pelimerkkiä kuutiossa on
*/
    
    public int merkkeja(Pelikuutio kuutio) {
        
        int lkm = 0;
        for (int i = 1; i < 28; i++) {
            if (kuutio.getMerkki(i) != ' ') {
                lkm++;
            }
        }
        return lkm;
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
}