import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner Eingabe = new Scanner(System.in);
        System.out.println("Das Programm zeigt die Primzahlen bis zum Eingegebnen Wert.");
        //System.out.println("Wieviele Primzahlen sollen angezeigt werden?");
        while (!Eingabe.hasNextInt()) Eingabe.next();
        int Ende = Eingabe.nextInt();
        //int PrimCounter = 0;
        //int zaehler = 1;
        //int nenner = 1;
        //Schleife zählt von 2 bis eingegebener Wert
        for (int zahl = 2; zahl <= Ende ; zahl++ ){

            boolean istPrimzahl = true;

            for(int teiler = 2; teiler < zahl && istPrimzahl; teiler++){
                if((zahl % teiler) == 0){
                    istPrimzahl = false;
                }
            }
            if(istPrimzahl){
                System.out.println(zahl + " ist eine Primzahl");
            }
        }
    }
}
