import java.sql.SQLOutput;
import java.util.Scanner;
public class Main {
    //Deklaration
    static Scanner Eingabe = new Scanner(System.in);
    static int form;
    static int hoehe;
    static int breite;
    static int inhalt = 1;
    static int kantenlaenge;

    public static void main(String[] args) {

        System.out.println("Rechteck = 1 oder Dreieck = 2?");
        form = Eingabe.nextInt();

        if(form == 1){
            zeichneRechteck();
        }else if(form == 2){
            System.out.println("Ausrichtung - L/M/R");
            zeichneDreieck_links();
        }
    }
    static void zeichneRechteck(){
        System.out.println("Breite Rechteck?");
        breite = Eingabe.nextInt();
        System.out.println("Höhe Rechteck?");
        hoehe = Eingabe.nextInt();

        for(int h = 0; h < hoehe; h++){
            //Breite
            for(int b=0; b < breite; b++){
                System.out.print(inhalt);
            }
            System.out.println();
            inhalt++;
            if(inhalt == 10){
                inhalt = 0;
            }
        }
    }
    static void zeichneDreieck_links(){
        System.out.println("Kantenlänge?");
        kantenlaenge = Eingabe.nextInt();

        int reihenlaenge = 1;
        for(int d = 0; d < kantenlaenge; d++){
            for(int i = 0; i < reihenlaenge; i++ ) {
                System.out.print(inhalt);
            }
            System.out.println();
            reihenlaenge++;
            inhalt++;
            if(inhalt > 9){
                inhalt = 0;
            }

        }
    }

    static void zeichneDreieck_rechts(){
        System.out.println("Kantenlänge?");
        kantenlaenge = Eingabe.nextInt();

        int reihenlaenge = 1;
        for(int d = 0; d < kantenlaenge; d++){
            for(int i = 0; i < reihenlaenge; i++ ) {
                System.out.print(inhalt);
            }
            System.out.println();
            reihenlaenge++;
            inhalt++;
            if(inhalt > 9){
                inhalt = 0;
            }

        }
    }

}
