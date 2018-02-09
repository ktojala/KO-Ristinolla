package koristinolla.ko.ristinolla;

public class Main {

/**
* KO-Ristinollan pääohjelma.
* @param args ei parametreja
*/
    public static void main(String[] args) {
        
        Peli kayttis = new Peli();
        kayttis.alkuEsittely();
        kayttis.aloitaPeli();
    }
}