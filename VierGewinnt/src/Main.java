import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

            VierGewinnt MyGame = new VierGewinnt();
            MyGame.start();
    }

}

class VierGewinnt {

    char player;                            //welcher Spieler ist gerade dran
    char[][] coordinates = new char[7][8];  //Array das alle von Spielern eingegebenen zeichen behinhaltet
    int moves = 0;                          //Zähler für die Anzahl der getätigten Züge

    public void start(){
        initCoordinates();                         //das array coordinates mit den zeichen der Spieler initial mit leereichen füllen
        printBoard(generateBoard());               // //das Spielfeld inkl. der zeichen aus dem coordinates array erzeugen das Spielfeld ausgeben
        while(checkWinner() == ' ' && checkDraw() == 0){ //Solange kein Gewinner und nicht unentschieden, programm weiter ausführen
            player = whichPlayer();
            newMove(askForColumn());           //nach spalte fragen und in x schreiben //das zeichen des Spielers ins coordinate array eintragen
            printBoard(generateBoard());           //das Spielfeld inkl. der zeichen aus dem coordinates array erzeugen und das Spielfeld ausgeben
         }
    }

    private void initCoordinates(){         //Methode um alle Felder des Koordinaten Arrays mit ' ' zu befüllen
        for(int y = 1; y <= 6; y++){
            for(int x = 1; x <= 7; x++){
                coordinates[y][x] = ' ';
            }
        }
    }

    private char whichPlayer(){
        if(moves %2 == 1){                  //Festellen ob Anzahl Züge gerade oder ungerade
            return 'X';               //Ungerade = Spieler X ist dran
        }else return 'O';             // Gerade = Spieler O ist dran
    }

    private String[][] generateBoard(){           //Methode zum Generieren des Spielfelds in Form des Arrays board
        String[][] board = new String[7][8];    //Array beinhaltet das gesamte Spielfeld
        for(int y = 1; y <= 6; y++){
            for(int x = 1; x <= 7; x++){
                board[y][x] = "[ " + coordinates[y][x] + " ]"; //Alle ggf. gesetzten Spielsteine werden hier bereits eingetragen
            }
        }
        return board;
    }

    private void printBoard(String board[][]){              //Methode zur Ausgabe des Spielfelds in der Konsole
        System.out.println("  1    2    3    4    5    6    7  "); //Spaltennummern ausgeben
        for(int y = 1; y <= 6; y++){
            for(int x = 1; x <= 7; x++){
                System.out.print(board[y][x]);                      //Alle Felder ausgeben
            }
            System.out.println();                                   //Zeilenumbruch nach jeder Reihe
        }
    }

    private int askForColumn(){        //Mehtode zur Abfrage der Spalte bei einem Spielzug

        System.out.println("Spieler "+ player + " ist dran");

        int x = 0; int y = 0;
        int[] validInputX = new int[8]; //Anzahl der gültigen Eingaben festlegen (8 da array immer bei null beginnt, die schleife aber bei 1 starten muss)
        for (int i = 1; i <= 7; i++) {
            validInputX[i] = i;               //Zahlen 1-7 per Schleife ins Array schreiben
        }
        while(x == 0) {                                                                         //solange x null ist
            x = getUserInput("Bitte gib die Spaltennummer ein (1-7):", validInputX);   //Frage Spieler nach Spaltennummer
            if(coordinates[1][x] != ' '){                                                       //wenn oberste Zeile einer spalte nicht leer
                System.out.println("Spalte " + x + " ist bereits voll");                        //gib fehlermeldung aus
                x=0;                                                                            //und setze x wieder auf null
            }
        }
        return x;                                                                               //Gib die eingegebne Spalte zurück
    }

    private void newMove(int X){       //Methode um den Spielzug eines Spielers ins Koordinaten Array einzutragen. Spalte des zu setzenden Steins wird als X übergeben

        for(int i = 6; i > 0; i--){     //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(coordinates[i][X] == ' '){   //Ist der Eintrag im Koordianten Array leer
                coordinates[i][X] = player; //Wird das Zeichen des Spieler eingetragen
                moves++;                    //Anzahl Spielzüge +1
                break;                      //Schleife unterbrechen
            }
        }
    }

    private char checkWinner(){  //Methode zur Prüfung ob es einen Gewinner gibt
        int Points = 0;

        //waagerecht
        for(int y = 6; y > 0; y--){             //Läuft von der untersten bis zur obersten Reihe
            for(int x = 1; x < 8; x++){       //für jedes Feld in der Reihe
                if(coordinates[y][x] == player){    //Prüfung ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    System.out.println("Vier Waagerechte in einer Reihe");
                    System.out.println("Spieler " + player + " hat Gewonnen!");
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;
        }
        //Senkrecht
        Points = 0;
        for(int x = 7; x > 0; x--){             //Läuft von Spalte ganz rechts bis zum anfang
            for(int y = 1; y < 7; y++){       //Prüft jedes Feld in der Spalte
                if(coordinates[y][x] == player){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                //System.out.print(Points);
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    System.out.println("Vier senkrechte in einer Reihe");
                    System.out.println("Spieler " + player + " hat Gewonnen!");
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;                           //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
        //vier diagonale von links unten nach rechts oben
        Points = 0;
        //int x = 1;
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = 6; y >= 3; y--){             //In jeder Spalte Von der untersten Reihe bis zur 4. von unten durchlaufen
                if(coordinates[y][x] == player){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen
                        row--;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (coordinates[row][col] == player) {    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;
                        }else Points = 0;
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            System.out.println("4diagonal links unten ->rechts oben");
                            System.out.println("Spieler " + player + " hat Gewonnen!");
                            return player;                  // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
            }
            Points = 0;
        }

        //vier diagonale von links oben nach rechts unten
        Points = 0;
        //int x = 1;
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = 1; y <= 3; y++){             //In jeder Spalte Von der untersten Reihe bis zur 4. von unten durchlaufen
                if(coordinates[y][x] == player){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen
                        row++;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (coordinates[row][col] == player) {    //Prüfen ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;                             //Einen punkt für das zeichen gutschreiben
                        }else Points = 0;
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            System.out.println("4 Diagonal links oben ->rechts unten");
                            System.out.println("Spieler " + player + " hat Gewonnen!");
                            return player;                  // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
            }
            Points = 0;
        }
        return ' ';
    }

    private int checkDraw(){
        for(int y = 1; y <= 6; y++){
            for(int x = 1; x <= 7; x++){
                if (coordinates[y][x] == ' '){
                    return 0;
                }
            }
        }
        System.out.println("Unentschieden");
        return 1;
    }
    private int getUserInput(String Question, int[] validAnswers){
        Scanner Eingabe = new Scanner(System.in);
        System.out.println(Question);
        while(!Eingabe.hasNextInt()){
            System.out.println("Bitte nur Zahlen eingeben");
            Eingabe.next();
        }
        int Answer = Eingabe.nextInt();
        for(int valid: validAnswers)
        {
            if(Answer == valid) {
                return Answer;
            }
        }
        System.out.println("Bitte nur gültige Zahl eingeben");
        return 0;
    }
}



