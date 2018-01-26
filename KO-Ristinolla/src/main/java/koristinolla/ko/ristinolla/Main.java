package koristinolla.ko.ristinolla;


import java.util.Scanner;

public class Main {

/**
* Varsinaiset pelitoiminnot ovat luokassa Tekoaly.
*/
    
    public static void main(String[] args) {
        
//        int opt;
        Scanner lukija = new Scanner(System.in);
        Tekoaly taly = new Tekoaly(3);

        System.out.println("RISTINOLLA");
        System.out.println("");
        System.out.println(" Pelaa tietokonetta vastaan     (1)");
        System.out.println(" Tietokone tietokonetta vastaan (2)");
        System.out.println("");
        System.out.print("Valintasi (1-2)? ");
        System.out.println("( toiminto 2 ei vielä käytössä )");
        System.out.println("");
//        opt = Integer.parseInt(lukija.nextLine());
//        while ((opt < 1) || (opt > 2)) {
//            System.out.print("Valintasi (1-2)? ");
//            opt = Integer.parseInt(lukija.nextLine());
//        }

        taly.pelaa();

    }
    
}
