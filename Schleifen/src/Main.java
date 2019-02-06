import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean keep_running = true;

        while(keep_running == true) {

            Scanner Eingabe = new Scanner(System.in);
            char filler = ' ';
            int dicke = 1;

            //Länge und breite abfragen, in beiden fällen muss der wert mind. 2 betragen
            System.out.println("Bitte Gesamtlänge des Rechtecks eingeben");
            int laenge = Eingabe.nextInt();
            while(laenge < 2) {
                System.out.println("Fehler! Die Länge muss mindestens 2 betragen\n");
                laenge = Eingabe.nextInt();
            }

            System.out.println("Bitte Gesamtbreite des Rechtecks eingeben");
            int breite = Eingabe.nextInt();
            while(breite < 2) {
                System.out.println("Die Breite muss mindestens 2 betragen\n");
                breite = Eingabe.nextInt();
            }

            //Abfrage ob Rechteck gefüllt werden soll
            System.out.println("Rechteck ausgefüllt = 1 - Rechteck nicht ausgeüllt = 0");
            int fill = Eingabe.nextInt();

            //Rechteck füllen Nur möglich wenn mehr als 2 lang und breit
            if (fill == 1 && laenge > 2 && breite > 2) {
                //Zeichen zum füllen des Rechtecks abfragen
                System.out.println("Welches Zeichen soll zur Füllung genutzt werden");
                filler = Eingabe.next().charAt(0);

            } else {
                System.out.println("Das Rechteck wird nicht gefüllt\n");
            }

            System.out.println("Bitte Rahmendicke des Rechtecks eingeben");
            dicke = Eingabe.nextInt();

            if(dicke*2 > laenge || dicke*2 > breite ){
                System.out.println("Die eingebenene Rahmen-Dicke kann bei der aktuellen Rechteck größe nicht umgesetzt werden\n Setze Rahmendicke auf 1");
                dicke = 1;
            }

            System.out.println("Erzeuge Rechteck mit Breite" + breite + " und Länge " + laenge);


            //oberen Rand ausgeben (basierend auf breite + ggf dicke)
            for(int d = 0 ; d < dicke; d++) {
                for (int b = 0; b < breite; b++) {
                    System.out.print("#");
                    if (b == breite - 1) {
                        System.out.print("\n");
                    }
                }
            }
            //Alle zeilen zwischen erster und letzter
            for (int l = 0; l < laenge - dicke*2; l++) {
                //Rahmen ausgeben
                //links
                for(int dl = 0; dl < dicke; dl++) {
                    System.out.print("#");
                }
                for (int bb = 0; bb < breite - dicke*2; bb++) {
                    //Füllung ausgeben
                    System.out.print(filler);
                }
                //Rechts
                for(int dr= 0; dr < dicke; dr++) {
                    System.out.print("#");
                }
                System.out.print("\n");
            }
            //letzte Zeile ausgeben
            for(int d = 0 ; d < dicke; d++) {
                for (int b = 0; b < breite; b++) {
                    System.out.print("#");
                    if (b == breite - 1) {
                        System.out.print("\n");
                    }
                }
            }


            System.out.println("\nMöchtest du ein weiteres Rechteck zeichnen? (1 = Ja / 0 = Nein)\n");
            int again = Eingabe.nextInt();
            if(again == 1){
                System.out.println("Und gleich nochmal ...");
            }else{
                keep_running = false;
                System.out.println("Bis zum nächsten mal");
            }
        }
    }
}
