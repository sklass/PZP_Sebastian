import java.util.Scanner;

public class mitdreieck {

    public static void main(String[] args) {
        boolean keep_running = true;
        Scanner Eingabe = new Scanner(System.in);
        //Schleife um Programm mehrfach ausführen zu können
        while(keep_running == true) {
            //Deklaration + Initialisierung
            System.out.println("Was möchtest du Zeichen - Rechteck = 1  -  Dreieck = 2");
            while (!Eingabe.hasNextInt()) Eingabe.next();
            int form = Eingabe.nextInt();

            //Scanner Eingabe = new Scanner(System.in);

            //Rechteck
            if(form == 1){
                char filler = ' ';
                int dicke = 1;

                //Länge abfragen,
                System.out.println("Bitte Gesamthöhe des Rechtecks eingeben");

                //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
                while (!Eingabe.hasNextInt()) Eingabe.next();
                int laenge = Eingabe.nextInt();

                //Muss mind. 2 betragen. Solange erneut abfragen bis gültiger Wert eingegeben wurde
                while(laenge < 2) {
                    System.out.println("Fehler! Die Höhe muss mindestens 2 betragen\n");
                    laenge = Eingabe.nextInt();
                }

                //Breite abfragen,
                System.out.println("Bitte Gesamtbreite des Rechtecks eingeben");

                //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
                while (!Eingabe.hasNextInt()) Eingabe.next();
                int breite = Eingabe.nextInt();

                //Solange erneut abfragen bis gültiger Wert eingegeben wurde
                while(breite < 2) {
                    System.out.println("Die Breite muss mindestens 2 betragen\n");
                    breite = Eingabe.nextInt();
                }

                //Rahmendicke abfragen wenn laenge und breite größer 2
                if(laenge > 2 && breite > 2){
                    System.out.println("Bitte Rahmendicke des Rechtecks eingeben");

                    //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
                    while (!Eingabe.hasNextInt()) Eingabe.next();
                    dicke = Eingabe.nextInt();

                    //Solange erneut abfragen bis gültiger Wert eingegeben wurde
                    // Rahmendicke mind. 1 und maximal hälfte von höhe und breite
                    while(dicke*2 > laenge || dicke*2 > breite || dicke < 1){
                        System.out.println("Fehlerhafte Eingabe!!!\n");
                        if(dicke < 1){
                            System.out.println("Rahmendicke muss mindestens 1 betragen");
                        }
                        if(dicke*2 > laenge){
                            System.out.println("Rahmendicke " + dicke + "*2  > Höhe " + laenge + "\nRahmendicke kann max. die hälfte der Rechteckhöhe betragen");
                        }
                        if(dicke*2 > breite){
                            System.out.println("Rahmendicke " + dicke + "*2  > Breite " + breite + "\nRahmendicke kann max. die hälfte der Rechteckbreite betragen");
                        }
                        System.out.println("Bitte Rahmendicke des Rechtecks eingeben");

                        //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
                        while (!Eingabe.hasNextInt()) Eingabe.next();
                        dicke = Eingabe.nextInt();
                    }
                }

                //Wenn lange und breite größer 2 und Rahmen nicht so groß wie gesamtes Rechteck
                //kann auf Wunsch eine Füllung für das Rechteck eingefügt werden
                if(laenge > 2 && breite > 2 && breite > dicke*2 && laenge > dicke*2){

                    //Abfrage ob Rechteck gefüllt werden soll
                    System.out.println("Rechteck ausgefüllt = 1 - Rechteck nicht ausgeüllt = 0");

                    //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
                    while (!Eingabe.hasNextInt()) Eingabe.next();
                    int fill = Eingabe.nextInt();

                    //Wenn Rechteck gefüllt werden soll
                    if (fill == 1) {
                        //Zeichen zum füllen des Rechtecks abfragen
                        System.out.println("Welches Zeichen soll zur Füllung genutzt werden");
                        //Nur das erste eingegebene Zeichen wird als Füller verwendet
                        filler = Eingabe.next().charAt(0);
                    } else {
                        System.out.println("Das Rechteck wird nicht gefüllt\n");
                    }
                }else{
                    System.out.println("Kein Platz im Rechteck um mit einem anderen Zeichen gefüllt zu werden");
                }
                rechteck(laenge, breite, dicke, filler);
            }
            //Dreieck
            if(form == 2){
                //Kantenlänge abfragen,
                System.out.println("Bitte Kantenlänge des Dreiecks eingeben");

                //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
                while (!Eingabe.hasNextInt()) Eingabe.next();
                int kantenlaenge = Eingabe.nextInt();
                dreieck(kantenlaenge);
            }

            //Abfrage ob Programm erneut ausgeführt werden soll
            System.out.println("\nMöchtest du noch etwas zeichnen? (1 = Ja / 0 = Nein)");

            //Sicherstellen das ein Integer Wert eingegeben wurde. wird etwas anderes eingegeben wird erneut gefragt
            while (!Eingabe.hasNextInt()) Eingabe.next();
            int again = Eingabe.nextInt();

            if(again == 1){
                System.out.println("Und gleich nochmal ...");
            }else{
                keep_running = false;
                System.out.println("Bis zum nächsten mal");
            }
        }
    }

    public static void rechteck(int laenge, int breite, int dicke, char filler){

        //Zusammenfassung der eingegebenen Daten
        System.out.println("Erzeuge Rechteck mit \nBreite: " + breite + "\nLänge: " + laenge + "\nRahmendicke: " + dicke );

        //Ausgabe des Rechtecks
        //Oberer Rand
        //Erste For Schleife für Rand dicke
        for(int d = 0 ; d < dicke; d++) {
            //Zweite Schleife für breite
            for (int b = 0; b < breite; b++) {
                System.out.print("#");
                if (b == breite - 1) {
                    System.out.print("\n");
                }
            }
        }
        //Alle zeilen zwischen erster und letzter
        for (int l = 0; l < laenge - dicke*2; l++) {

            //links
            //For Schleife für Rand dicke links
            for(int dl = 0; dl < dicke; dl++) {
                System.out.print("#");
            }
            //For Schleife für Füllung
            for (int bb = 0; bb < breite - dicke*2; bb++) {
                //Füllung ausgeben
                System.out.print(filler);
            }
            //Rechts
            //For Schleife für Rahmen rechts
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
    }

    public static void dreieck(int kantenlaenge){

        int rowlenght = 1;
        for(int row = 1; row <= kantenlaenge; row++){
            for(int i = 0; i < rowlenght; i++){
                System.out.print("#");
            }
            System.out.println();
             rowlenght++;
        }


    }
}
