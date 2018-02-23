package koristinolla.ko.ristinolla;

/**
 * Luokka Peli on varsinainen ristinollapeli.
 */
public class Peli {
    private int optio;         // pelaaja (1) vai kone (2) konetta vastaan
    private Pelikuutio kuutio; // pstringiä vastaava char array
    private boolean peliohi;   // true, jos peli on loppuun pelattu
    private char vuoro;        // onko vuoro (o) vai (x)
                               // tarvitaan kun kone pelaa konetta vastaan
    private Tekoaly taly1;     // yksittäinen tekoäly
    private Tekoaly taly2;     // toinen tekoäly

    
/**
* Luokan Peli konstruktori
* @param optio tekoäly tekoalyä vastaan vai pelaajaa vastaan
*/
    public Peli(int optio) {
        this.optio = optio;
        this.peliohi = false;
        this.kuutio = new Pelikuutio();
//        this.kuutio = new Pelikuutio(3);  // kokomitta mukana
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
        return this.taly1;
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

        this.taly1 = new Tekoaly(this.kuutio,'x');
        if (this.optio == 2) {
            this.taly2 = new Tekoaly(this.kuutio,'o');
        }
        
        int paikkanro;   // paikkanumero pelistringissä
        
        while (!peliohi) {

            this.vuoro = 'o';
            if (this.optio == 1) {
                
            paikkanro = ohjaus.pelaajanSiirto(this.kuutio);   // kysytään pelaajan siirto
            
            if (paikkanro == 0) {
                System.out.println("Pelaajan käskystä peli lopetetaan");
                peliohi = true;
                return;
            }
            
            this.kuutio.setMerkki('o',paikkanro);
            
            this.kuutio.tulostaPst();
            if ((this.kuutio.nollaSuora(this.kuutio.getTasoT1())) ||  
                (this.kuutio.nollaSuora(this.kuutio.getTasoT2())) ||
                (this.kuutio.nollaSuora(this.kuutio.getTasoT3())) ) 
                {
                System.out.println("");
                System.out.println("Onneksi olkoon, voitit koneen!");
                System.out.println("");
                peliohi = true;
                this.kuutio.tulostaPst();
            }
            
            this.vuoro = 'x';     // tekoäly1
            
//  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
            if (!this.kuutio.tyhjia()) {
                peliohi = true;
                System.out.println("");
                System.out.println("TASAPELI");
                System.out.println("");
            }

            if (!peliohi) {
                
                int paikka = this.taly1.talysiirto(this.kuutio);
                
                this.kuutio.setMerkki('x',paikka);
                
                if ((this.kuutio.ristiSuora(this.kuutio.getTasoT1())) ||
                    (this.kuutio.ristiSuora(this.kuutio.getTasoT2())) ||
                    (this.kuutio.ristiSuora(this.kuutio.getTasoT3()))) {
                    System.out.println("");
                    System.out.println("Kone voitti!");
                    System.out.println("");
                    this.peliohi = true;
                    this.kuutio.tulostaPst();
                }
            }
            
            
            } else {
                int paikka = this.taly2.talysiirto(this.kuutio);
                this.kuutio.tulostaPst();
                if ((this.kuutio.nollaSuora(this.kuutio.getTasoT1())) ||  
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT2())) ||
                    (this.kuutio.nollaSuora(this.kuutio.getTasoT3())) ) 
                {
                    System.out.println("");
                    System.out.println("Kone 2 voitti!");
                    System.out.println("");
                    peliohi = true;
                    this.kuutio.tulostaPst();
                }
            
                this.vuoro = 'x';     // tekoäly1
            //  Jos tyhjiä ruutuja ei ole, pelin täytyy olla tasapeli.
                if (!this.kuutio.tyhjia()) {
                    peliohi = true;
                    System.out.println("");
                    System.out.println("TASAPELI");
                    System.out.println("");
                }

                if (!peliohi) {
                
                    paikka = this.taly1.talysiirto(this.kuutio);
                
                    this.kuutio.setMerkki('x',paikka);
                
                    if ((this.kuutio.ristiSuora(this.kuutio.getTasoT1())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT2())) ||
                        (this.kuutio.ristiSuora(this.kuutio.getTasoT3()))) {
                        System.out.println("");
                        System.out.println("Kone 1 voitti!");
                        System.out.println("");
                        this.peliohi = true;
                        this.kuutio.tulostaPst();
                    }
                }
                
            }
        }
    }

}