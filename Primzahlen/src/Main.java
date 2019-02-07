import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner Eingabe = new Scanner(System.in);
        System.out.println("Das Programm zeigt die ersten X Primzahlen.");
        System.out.println("Wieviele Primzahlen sollen angezeigt werden?");
        while (!Eingabe.hasNextInt()) Eingabe.next();
        int PrimAnzahl = Eingabe.nextInt();
        int PrimCounter = 0;
        int zaehler = 1;
        //int nenner = 1;

        while (PrimCounter < PrimAnzahl){


            for (int nenner = 1; nenner <= zaehler; nenner++ )
            if( zaehler % nenner == 0){
                System.out.println("Keine Primzahl" + zaehler + ":" + nenner);
            }else{
                System.out.println("Primzahl" + zaehler + ":" + nenner);
                PrimCounter++;
            }
            zaehler++;

        }
    }
}
