package koristinolla.ko.ristinolla;

public class Main {

/**
* KO-Ristinollan pääohjelma.
* @param args ei parametreja
*/
    public static void main(String[] args) {
        
        Kayttoliittyma kayttis = new Kayttoliittyma();
        kayttis.alkuEsittely();
        kayttis.aloitaPeli();
    }
}