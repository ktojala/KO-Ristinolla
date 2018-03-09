package koristinolla.ko.ristinolla;

/**
 * Luokka Peli on varsinainen ristinollapeli.
 */
public class Peli {
    private int optio;         // pelaaja (1) vai kone (2) konetta vastaan
    private int vaativuus;     // tekoälyn vaativuustaso
    private int pelilaji;      // 3D-ristinollan pelilaji
    private Pelikuutio kuutio; // pstringiä vastaava char array
    private boolean peliohi;   // true, jos peli on loppuun pelattu

    private Tekoaly tekoaly1;     // yksittäinen tekoäly
    private Tekoaly tekoaly2;     // toinen tekoäly

    
/**
* Luokan Peli konstruktori
* 
* @param optio tekoäly tekoalyä vastaan vai pelaajaa vastaan
* @param vaativuus montako siirtoa eteenpäin tekoäly tutkii
* @param pelilaji kuution keskimmäinen osakuutio mukana vai ei
*/
    public Peli(int optio, int vaativuus, int pelilaji) {
        this.optio = optio;
        this.peliohi = false;
        this.vaativuus = vaativuus;
        this.pelilaji = pelilaji;
        this.kuutio = new Pelikuutio();
    }


/**
* Palautetaan kuutio yksinkertaisessa muodossa
* @return kuutio pelistringinä char array -muodossa
*/
    public Pelikuutio getKuutio() {
        return this.kuutio;
    }

    
/**
* Palautetaan pelioptio
* @return optio  pelaaja (1) vai kone (2) konetta vastaa
*/
    public int getOptio() {
        return this.optio;
    }

    
/**
* Palautetaan ensimmainen tekoaly
* @return taly1  tekoalyn merkki
*/
    public Tekoaly getTekoaly1() {
        return this.tekoaly1;
    }
    
    
/**
* Palautetaan tieto, onko peliohi
* @return peliohi
*/
    public boolean getPeliohi() {
        return this.peliohi;
    }

    
/**
* Asetetaan tieto, onko peliohi
* @param arvo onko peli jo ohi
*/
    public void setPeliohi(boolean arvo) {
        this.peliohi = arvo;
    }

    
/**
* Tämä metodi pelaa yhden 3D-Ristinolla -pelin. 
* Peli jatkuu kunnes muuttuja peliohi saa arvon true.
* Jokainen pelikierros on yksi kierros while-loopissa.
* Lopuksi julistetaan lopputulos.
* 
* @param ohjaus pelin käyttöliittymä
*/
    public void aloitaPeli1(Kayttoliittyma ohjaus) {

        this.tekoaly1 = new Tekoaly(this.kuutio,'x',this.vaativuus,this.pelilaji);
        if (this.optio == 2) {
            this.tekoaly2 = new Tekoaly(this.kuutio,'o',this.vaativuus,this.pelilaji);
        }
        
        int paikkanro;   // paikkanumero pelistringissä
        long aika1, aika2; // aikatestaus
        int aikaind = 0;
        long[] aikatau = new long [100];
        
        if (this.pelilaji == 2) {
            this.kuutio.setMerkki('#',14);
        }
        
        while (!peliohi) {

            if (this.optio == 1) {
                paikkanro = ohjaus.pelaajanSiirto(this.kuutio);   // kysytään pelaajan siirto
            
                if (paikkanro == 0) {
                    peliohi = true;
                    return;
                }
            
                this.kuutio.setMerkki('o',paikkanro);
                this.kuutio.tulostaPst();
                
                if ((this.kuutio.nollaSuora(this.kuutio.getTasoT1())) ||  
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT2())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT3())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT4())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT5())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT6())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT7())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT8())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT9())) ||
                    (this.kuutio.nollaSuoraLavistajalla(this.kuutio)) ) 
                    {
                    System.out.println("");
                    System.out.println("Onneksi olkoon, voitit koneen!");
                    System.out.println("");
                    peliohi = true;
                    }
            
//  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
                if (!this.kuutio.tyhjia()) {
                    peliohi = true;
                    System.out.println("");
                    System.out.println("TASAPELI");
                    System.out.println("");
                }

                if (!peliohi) {
                    
                    paikkanro = this.tekoaly1.talysiirto(this.kuutio);
                    
                    this.kuutio.setMerkki('x',paikkanro);
                    this.kuutio.tulostaPst();
                
                    if ((this.kuutio.ristiSuora(this.kuutio.getTasoT1())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT2())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT3())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT4())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT5())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT6())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT7())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT8())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT9())) ||
                        (this.kuutio.ristiSuoraLavistajalla(this.kuutio)) ) 
                    {
                        System.out.println("");
                        System.out.println("Tekoäly voitti!");
                        System.out.println("");
                        this.peliohi = true;
                    }
                }
                
            } else {     // ensimmäinen siirto saadaan tekoälyltä 2 eli 'o'
                aika1 = System.currentTimeMillis();
                paikkanro = this.tekoaly2.talysiirto(this.kuutio);
                aika2 = System.currentTimeMillis();
                aikatau[aikaind]= aika2-aika1;
                aikaind++;
                    
                this.kuutio.setMerkki('o',paikkanro);
                this.kuutio.tulostaPst();
                
                if ((this.kuutio.nollaSuora(this.kuutio.getTasoT1())) ||  
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT2())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT3())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT4())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT5())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT6())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT7())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT8())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT9())) ||
                    (this.kuutio.nollaSuoraLavistajalla(this.kuutio)) ) 
                {
                    System.out.println("");
                    System.out.println("Tekoäly 2 (o) voitti!");
                    System.out.println("");
                    peliohi = true;
                }
            
            //  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
                if (!this.kuutio.tyhjia()) {
                    peliohi = true;
                    System.out.println("");
                    System.out.println("TASAPELI");
                    System.out.println("");
                }

                if (!peliohi) {
                    aika1 = System.currentTimeMillis();
                    paikkanro = this.tekoaly1.talysiirto(this.kuutio);
                    aika2 = System.currentTimeMillis();
                    aikatau[aikaind]= aika2-aika1;
                    aikaind++;
                    
                    this.kuutio.setMerkki('x',paikkanro);
                    this.kuutio.tulostaPst();
                    
                    if ((this.kuutio.ristiSuora(this.kuutio.getTasoT1())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT2())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT3())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT4())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT5())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT6())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT7())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT8())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT9())) ||
                        (this.kuutio.ristiSuoraLavistajalla(this.kuutio)) ) 
                    {
                        System.out.println("");
                        System.out.println("Tekoäly 1 (x) voitti!");
                        System.out.println("");
                        this.peliohi = true;
                    }
                }
            }
        }
// Tulostetaan testausta varten siirron vaatima aika
//        for (int i = 0; i< aikaind; i++) {
//            System.out.println(aikatau[i]);
//        }
    }
}