
import java.util.Scanner;

public class VierFelderTicTacToe {
    static int feldY;         // Zähler für die Anzahl der Reihen
    static int feldX;         // Zähler für Felder in einer Reihe
    static int anzahlFelderX;// Vorgabe wie viele ausfüllbare Felder es pro Reihe geben soll
    static int anzahlFelderY; // Vorgabe wie viele ausfüllbare Reihen es geben soll
    static int anzahlReihen;  // Zähler für Anzahl aller vorhandenen Reihen
    static int spieler;       // Spieler 1 oder 2 ist dran
    static char filler;       // Variable für X bzw O, jenachdem wer dran ist
    static int zuege;         // Zähler für die Anzahl der Spielzüge
    static int zuegeLimit;    // Maximale Anzahl der Spielzüge
    static boolean winner;    // Ist true wenn ein spieler gewonnen hat
    static Scanner Eingabe = new Scanner(System.in);
    static int spiel;         //welches Spiel soll gespielt werden
    // 2D Array indem alle von Spielern gesetzten Zeichen gespeichert werden
    //wird zunächst mit leerzeichen initialisiert
    static char [][] koordinaten;

    public static void main(String[] args) {
        selectGame();
    }
    static void selectGame(){
        spiel = 0;
        System.out.println("Was möchtest du Spielen");
        System.out.println("3 - TicTacToe 3x3");
        System.out.println("4 - TicTacToe 4x4");
        while(spiel != 3 && spiel != 4){
            System.out.println("Bitte 3 oder 4 eingeben");
            while(!Eingabe.hasNextInt()){
                System.out.println("Bitte 3 oder 4 eingeben");
                Eingabe.next();
            }
            spiel = Eingabe.nextInt();
        }
        playGame();
    }
     static void playGame(){
        if (spiel == 3){
            initTicTacToe();
        }else if (spiel == 4){
            initVierGewinnt();
        }

        //Solange kein gewinner
         while(winner == false){
             zeichneSpielfeld();
             zeigeStatus();
             spielerEingabe();
             if (spiel == 3){
                 ermittleGewinner3();
             }else if (spiel == 4){
                 ermittleGewinner4();
             }
             zuege ++;
             checkUnentschieden();
         }
         nochmal();
     }
    static void initTicTacToe(){
        feldY = 1;          // Zähler für die Anzahl der Reihen
        feldX = 1;          // Zähler für Felder in einer Reihe
        anzahlFelderX = 3;  // Vorgabe wie viele ausfüllbare Felder es pro Reihe geben soll
        anzahlFelderY = 3;  // Vorgabe wie viele ausfüllbare Reihen es geben soll
        anzahlReihen = 0;   // Zähler für Anzahl aller vorhandenen Reihen
        spieler = 0;        // Spieler 1 oder 2 ist dran
        filler= ' ';       // Variable für X bzw O, jenachdem wer dran ist
        zuege = 1;          // Zähler für die Anzahl der Spielzüge
        zuegeLimit = 9;
        winner = false;
        // 2D Array indem alle von Spielern gesetzten Zeichen gespeichert werden
        //wird zunächst mit leerzeichen initialisiert
        koordinaten = new char[][] {
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
    }
        static void initVierGewinnt(){
           feldY = 1;          // Zähler für die Anzahl der Reihen
           feldX = 1;          // Zähler für Felder in einer Reihe
           anzahlFelderX = 4;  // Vorgabe wie viele ausfüllbare Felder es pro Reihe geben soll
           anzahlFelderY = 4;  // Vorgabe wie viele ausfüllbare Reihen es geben soll
           anzahlReihen = 0;   // Zähler für Anzahl aller vorhandenen Reihen
           spieler = 0;        // Spieler 1 oder 2 ist dran
           filler= ' ';       // Variable für X bzw O, jenachdem wer dran ist
           zuege = 1;          // Zähler für die Anzahl der Spielzüge
           zuegeLimit = 16;
           winner = false;
            // 2D Array indem alle von Spielern gesetzten Zeichen gespeichert werden
            //wird zunächst mit leerzeichen initialisiert
             koordinaten = new char[][] {
                    {' ', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' ', ' '}
            };
        }

        static void zeichneSpielfeld(){
            //Zähler für Felder auf der Y Achse auf 1 zurücksetzen
            feldY = 1;
            // Die Anzahl der ausgegebenen Reihen wird bei jedem Schleifendurchlauf zurückgesetzt
            anzahlReihen = 0;

            // Anzahl aller Reihen muss kleiner sein als Anzahl der Felder in einer (Spalte*2)-1
            while( anzahlReihen < ((anzahlFelderY*2)+1)){
                //Zähler für Felder auf der X Achse auf 1 setzen
                feldX = 1;
                // Bei ungerader Reihennummer werden befüllbare Felder ausgegeben
                if( anzahlReihen % 2 == 1){
                    for(int b = 0; b < anzahlFelderX; b++){
                        //System.out.print("# " + feldX + " ");
                        System.out.print("# "+ koordinaten[feldY][feldX] + " ");
                        feldX++;
                    }
                    System.out.print("# <-- Reihe " + feldY );
                    System.out.println();
                    feldY++;
                    //System.out.print(feldY);
                }else{ //bei ungerader Reihennummer werden nur Rauten als trenner ausgegeben
                    for(int b = 1; b < (anzahlFelderX*4)+2; b++){
                        System.out.print("#");
                    }
                    System.out.println();
                }
                anzahlReihen++;
            }
        }

        static void zeigeStatus(){
            //System.out.println();
            //System.out.println("Spielzug: " + zuege);
            // Welcher Spieler ist an der Reihe?
            if(zuege % 2 == 1){
                System.out.println("Spieler 1 ist dran (O)");
                filler = 'O';
                spieler = 1;
            }else{
                System.out.println("Spieler 2 ist dran (X)");
                filler = 'X';
                spieler = 2;
            }
        }

        static void spielerEingabe(){

            //System.out.println("Bitte gib die koordinaten des zu befüllenden Feldes ein");
            //Sicherstellen das y 1-anzahlFelderY ist
            int y = 0;
            while(y < 1 || y > anzahlFelderY) {
                if(y < 1 || y > anzahlFelderY){
                    System.out.println("Bitte nur Zahlen zwischen 1 und " + anzahlFelderY + " eingeben!");
                }
                System.out.println("Welche Reihe?");
                //Solange kein Int eingegeben wird, Erneut abfragen
                while (!Eingabe.hasNextInt()) {
                    System.out.println("Bitte nur Zahlen zwischen 1 und " + anzahlFelderY + " eingeben!");
                    Eingabe.next();
                } //Ansonsten INT in variable speichern
                y = Eingabe.nextInt();
            }

            //Sicherstellen das X 1-3 ist
            int x = 0;
            while(x < 1 || x > anzahlFelderX) {
                if(x < 1 || x > anzahlFelderX){
                    System.out.println("Bitte nur Zahlen zwischen 1 und " + anzahlFelderX + " eingeben!");
                }
                System.out.println("Feld?");
                //Solange kein Int eingegeben wird, Erneut abfragen
                while (!Eingabe.hasNextInt()) {
                    System.out.println("Bitte nur Zahlen zwischen 1 und " + anzahlFelderX + " eingeben!");
                    Eingabe.next();
                } //Ansonsten INT in variable speichern
                x = Eingabe.nextInt();
            }

            //Position für X/O prüfen und abspeichern oder erneut abfragen
            boolean valid = false;
            while(valid == false){
                //Filler für die eingegebene Koordinate abspeichern , wenn das Feld nicht bereits belegt ist
                if(koordinaten[y][x] != 'X' && koordinaten[y][x] != 'O'){
                    valid = true;
                    koordinaten[y][x] = filler;
                }else{
                    System.out.println("Das Feld in Reihe " + y +" Feld " + x + " ist voll!");
                    zeichneSpielfeld();
                    System.out.println("Reihe? (1,2,3)");
                    y = Eingabe.nextInt();
                    System.out.println("Feld? (1,2,3)");
                    x = Eingabe.nextInt();
                }
            }
        }
        static void ermittleGewinner3(){

            //Prüfung ob es einen Gewinner gibt
            //Schleife für drei waagerechte
            for(int y = 1; y <= 3; y++){
                int x = 1;
                if(koordinaten[y][x] == filler && koordinaten[y][x+1] == filler && koordinaten[y][x+2] == filler){
                    System.out.println("Spieler " + spieler + " hat gewonnen");
                    winner = true;
                }
            }
            //Schleife für drei senkrechte
            for(int x = 1; x <= 3; x++){
                int y = 1;
                if(koordinaten[y][x] == filler && koordinaten[y+1][x] == filler && koordinaten[y+2][x] == filler){
                    System.out.println("Spieler " + spieler + " hat gewonnen");
                    winner = true;
                }
            }
            //3 von oben links nach unten rechts
            if(koordinaten[1][1] == filler && koordinaten[2][2] == filler && koordinaten[3][3] == filler){
                System.out.println("Spieler " + spieler + " hat gewonnen");
                winner = true;
            }
            //3 von oben rechts nach unten links
            if(koordinaten[1][3] == filler && koordinaten[2][2] == filler && koordinaten[3][1] == filler){
                System.out.println("Spieler " + spieler + " hat gewonnen");
                winner = true;
            }
        }

        static void ermittleGewinner4(){

            //Prüfung ob es einen Gewinner gibt
            //Schleife für 4 waagerechte
            for(int y = 1; y <= anzahlFelderY; y++){
                int x = 1;
                if(koordinaten[y][x] == filler && koordinaten[y][x+1] == filler && koordinaten[y][x+2] == filler && koordinaten[y][x+3] == filler){
                    System.out.println("Spieler " + spieler + " hat gewonnen");
                    winner = true;
                    zeichneSpielfeld();
                }
            }
            //Schleife für 4 senkrechte
            for(int x = 1; x <= anzahlFelderX; x++){
                int y = 1;
                if(koordinaten[y][x] == filler && koordinaten[y+1][x] == filler && koordinaten[y+2][x] == filler && koordinaten[y+3][x] == filler){
                    System.out.println("Spieler " + spieler + " hat gewonnen");
                    winner = true;
                    zeichneSpielfeld();
                }
            }
            //4 von oben links nach unten rechts
            if(koordinaten[1][1] == filler && koordinaten[2][2] == filler && koordinaten[3][3] == filler && koordinaten[4][4] == filler){
                System.out.println("Spieler " + spieler + " hat gewonnen");
                winner = true;
                zeichneSpielfeld();
            }
            //4 von oben rechts nach unten links
            if(koordinaten[1][4] == filler && koordinaten[2][3] == filler && koordinaten[3][2] == filler && koordinaten[4][1] == filler){
                System.out.println("Spieler " + spieler + " hat gewonnen");
                winner = true;
                zeichneSpielfeld();
            }
        }

        static void checkUnentschieden(){
            if(zuege > zuegeLimit) {
                System.out.println("Unentschieden!");
                winner = true;
                zeichneSpielfeld();
            }
        }

        static void nochmal(){
            System.out.println("Revanche? Ja/Nein");
            String nochmal = Eingabe.next();

            if(nochmal.equalsIgnoreCase("ja")){
                zuege = 0;
                winner = false;
                selectGame();
            }else{
                System.out.println("Bye");
            }

        }

}
