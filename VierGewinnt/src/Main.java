import java.util.Scanner;

public class Main {

    private static String ErrorMsg;
    private static String GameModeList = "\n3: TicTacToe\n4: VierGewinnt";
    private static int[] validGameModes = new int[]{3, 4};
    private static int Gamemode;

    private static int getUserInput(String Question, int[] validAnswers){
        Scanner Eingabe = new Scanner(System.in);
        System.out.println(Question);
        while(!Eingabe.hasNextInt()){
            ErrorMsg = "Bitte nur Zahlen eingeben";
            Eingabe.next();
        }
        int Answer = Eingabe.nextInt();
        for(int valid: validAnswers)
        {
            if(Answer == valid) {
                return Answer;
            }
        }
        return 0;
    }

    public static void main(String[] args) {

        Gamemode = getUserInput("Was möchtest du Spielen?" + GameModeList, validGameModes);
        if(Gamemode == 3 ){
            TicTacToe MyGame = new TicTacToe();
            MyGame.start();
        }
        if(Gamemode == 4){
            VierGewinnt MyGame = new VierGewinnt();
            MyGame.start();
        }
    }

}
class TicTacToe {
    private char player1 = 'X';
    private char player2 = 'O';
    private char player;
    private int sizeX = 3;
    private int sizeY = 3;
    private int x;
    private int y;
    private char[][] coordinates = new char[sizeY+1][sizeX+1];
    private String[][] board = new String[sizeY+1][sizeX+1];
    private int moves = 0;
    private char winner = ' ';
    //private String Msg;
    private String ErrorMsg = "";

    public void start(){
        initCoordinates();          //das array coordinates mit den zeichen der Spieler initial mit leereichen füllen
        generateBoard();            //das Spielfeld inkl. der zeichen aus dem coordinates array erzeugen
        printBoard();               //das Spielfeld ausgeben

        while(winner == ' '){

            askForCoordinates();    //nach Koordinaten fragen und in y und x schreiben
            newMove(y,x);           //das zeichen des Spielers ins coordinate array eintragen
            generateBoard();        //das Spielfeld inkl. der zeichen aus dem coordinates array erzeugen
            printBoard();           //das Spielfeld ausgeben
            winner = checkWinner(); //prüfen obe jemand gewonnen hat und den Rückgabewert in winner speichern
        }
    }

    private void initCoordinates(){
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                coordinates[y][x] = ' ';
            }
        }
    }

    private void generateBoard(){
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                board[y][x] = "[ " + coordinates[y][x] + " ]";
            }
        }
    }

    private void printBoard(){
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                System.out.print(board[y][x]);
            }
            System.out.println();
        }
    }
    private void printErrors(){
        System.out.println(ErrorMsg);
    }

    private void askForCoordinates(){
        x = 0;
        y = 0;
        int[] validInputY = new int[sizeY+1];
        for (int i = 1; i <= sizeY; i++) {
            validInputY[i] = i;
        }
        while (y == 0) {
            y = getUserInput("Bitte gib die Y-Koordinate ein:", validInputY);
        }
        int[] validInputX = new int[sizeX+1];
        for (int i = 1; i <= sizeX; i++) {
            validInputX[i] = i;
        }
        while(x == 0) {
            x = getUserInput("Bitte gib die X-Koordinate ein:", validInputX);
        }
    }

    private void newMove (int Y, int X){
        if(moves %2 == 1){
            player = player1;
        }else player = player2;
        coordinates[Y][X] = player;
        moves++;
    }

    private char checkWinner(){
        //Prüfung ob es einen Gewinner gibt
        //Schleife für drei waagerechte
        for(int y = 1; y <= 3; y++){
            int x = 1;
            if(coordinates[y][x] == player && coordinates[y][x+1] == player && coordinates[y][x+2] == player){
                //System.out.println("Spieler " + player + " hat gewonnen");
                return player;
            }
        }
        //Schleife für drei senkrechte
        for(int x = 1; x <= 3; x++){
            int y = 1;
            if(coordinates[y][x] == player && coordinates[y+1][x] == player && coordinates[y+2][x] == player){
                //System.out.println("Spieler " + player + " hat gewonnen");
                return player;
            }
        }
        //3 von oben links nach unten rechts
        if(coordinates[1][1] == player && coordinates[2][2] == player && coordinates[3][3] == player){
            //System.out.println("Spieler " + player + " hat gewonnen");
            return player;
        }
        //3 von oben rechts nach unten links
        if(coordinates[1][3] == player && coordinates[2][2] == player && coordinates[3][1] == player){
            //System.out.println("Spieler " + player + " hat gewonnen");
            return player;
        }
        return ' ';
    }


    private int getUserInput(String Question, int[] validAnswers){
        Scanner Eingabe = new Scanner(System.in);
        System.out.println(Question);
        while(!Eingabe.hasNextInt()){
            ErrorMsg = "Bitte nur Zahlen eingeben";
            printErrors();
            Eingabe.next();
        }
        int Answer = Eingabe.nextInt();
        for(int valid: validAnswers)
        {
            if(Answer == valid) {
                //System.out.println("Valid: " + valid);

                return Answer;
            }
        }
        ErrorMsg = "Bitte nur gültige Zahl eingeben";
        printErrors();
        return 0;
    }
}

class VierGewinnt {
    char player1 = 'X';
    char player2 = 'O';
    char player = ' ';
    int sizeX = 7;
    int sizeY = 6;
    int x;
    int y;
    char[][] coordinates = new char[sizeY+1][sizeX+1];
    String[][] board = new String[sizeY+1][sizeX+1];
    int moves = 0;
    int winner = ' ';
    String ErrorMsg;

    public void start(){
        initCoordinates();          //das array coordinates mit den zeichen der Spieler initial mit leereichen füllen
        generateBoard();            //das Spielfeld inkl. der zeichen aus dem coordinates array erzeugen
        printBoard();               //das Spielfeld ausgeben

        while(winner == ' '){

            askForColumn();    //nach spalte fragen und in x schreiben
            newMove(x);           //das zeichen des Spielers ins coordinate array eintragen
            generateBoard();        //das Spielfeld inkl. der zeichen aus dem coordinates array erzeugen
            printBoard();           //das Spielfeld ausgeben
            winner = checkWinner2(); //prüfen obe jemand gewonnen hat und den Rückgabewert in winner speichern
        }
    }

    private void initCoordinates(){         //Methode um alle Felder des Koordinaten Arrays mit ' ' zu befüllen
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                coordinates[y][x] = ' ';
            }
        }
    }

    private void generateBoard(){           //Methode zum Generieren des Spielfelds in Form des Arrays board
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                board[y][x] = "[ " + coordinates[y][x] + " ]"; //Alle ggf. gesetzten Spielsteine werden hier bereits eingetragen
            }
        }
    }

    private void printBoard(){              //Methode zur Ausgabe des Spielfelds in der Konsole
        System.out.println("  1    2    3    4    5    6    7  "); //Spaltennummern ausgeben
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                System.out.print(board[y][x]);                      //Alle Felder ausgeben
            }
            System.out.println();                                   //Zeilenumbruch nach jeder Reihe
        }
    }
    private void printErrors(){         //Bisher eher nutzlose funktion TODO
        System.out.println(ErrorMsg);
    }

    private void askForColumn(){        //Mehtode zur Abfrage der Spalte bei einem Spielzug
        x = 0; y = 0;
        int[] validInputX = new int[sizeX+1]; //Anzahl der gültigen Eingaben festlegen (sizeX+1 da array immer bei null beginnt, die schleife aber bei 1 starten muss)
        for (int i = 1; i <= sizeX; i++) {
            validInputX[i] = i;               //Alle gültigen Zahlen per Schleife ins Array schreiben
        }
        while(x == 0) {                                                                         //solange x null ist
            x = getUserInput("Bitte gib die Spaltennummer ein (1-7):", validInputX);   //Frage Spieler nach Spaltennummer
            if(coordinates[1][x] != ' '){                                                       //wenn oberste Zeile einer spalte nicht leer
                System.out.println("Spalte " + x + " ist bereits voll");                        //gib fehlermeldung aus
                x=0;                                                                            //und setze x wieder auf null
            }
        }
    }

    private void newMove (int X){           //Methode um den Spielzug eines Spielers ins Koordinaten Array einzutragen. Spalte des zu setzenden Steins wird als X übergeben
        if(moves %2 == 1){                  //Festellen ob Anzahl Züge gerade oder ungerade
            player = player1;               //Ungerade = Spieler 1
        }else player = player2;             // Gerade = Spieler 2

        for(int i = sizeY; i > 0; i--){     //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(coordinates[i][X] == ' '){   //Ist der Eintrag im Koordianten Array leer
                coordinates[i][X] = player; //Wird das Zeichen des Spieler eingetragen
                moves++;                    //Anzahl Spielzüge +1
                break;                      //Schleife unterbrechen
            }
        }
    }

    private char checkWinner(){  //Methode zur Prüfung ob es einen Gewinner gibt
        int Points = 0;

        for(int y = sizeY; y > 0; y--){             //Schleife für vier waagerechte
            for(int x = 1; x < sizeX+1; x++){       //Prüft Reiheweise jedes Feld
                if(coordinates[y][x] == player){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }

                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
            }
        }
        //Senkrecht
        Points = 0;
        for(int x = sizeX; x > 0; x--){             //Schleife für vier waagerechte
            for(int y = 1; y < sizeY+1; y++){       //Prüft Reiheweise jedes Feld
                if(coordinates[y][x] == player){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                //System.out.print(Points);
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
            }
        }



/*

        //3 von oben links nach unten rechts
        if(coordinates[1][1] == player && coordinates[2][2] == player && coordinates[3][3] == player){
            //System.out.println("Spieler " + player + " hat gewonnen");
            return player;
        }
        //3 von oben rechts nach unten links
        if(coordinates[1][3] == player && coordinates[2][2] == player && coordinates[3][1] == player){
            //System.out.println("Spieler " + player + " hat gewonnen");
            return player;
        }
        */
        return ' ';
    }

    private char checkWinner2(){  //Methode zur Prüfung ob es einen Gewinner gibt
        int Points = 0;
        //int DiagPoints = 0;

        for(int y = sizeY; y > 0; y--){             //Schleife für vier waagerechte
            for(int x = 1; x < sizeX+1; x++){       //Prüft Reiheweise jedes Feld
                if(coordinates[y][x] == player){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }

                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
            }
        }
        //Senkrecht
        Points = 0;
        for(int x = sizeX; x > 0; x--){             //Schleife für vier waagerechte
            for(int y = 1; y < sizeY+1; y++){       //Prüft Reiheweise jedes Feld
                if(coordinates[y][x] == player){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                //System.out.print(Points);
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
            }
        }
        Points = 0;
        int x = 1;
        for(int y = sizeY; y > 0; y--){             //Schleife für vier dieagonale links nach rechts TODO!!!!!!
            System.out.println("X: " + x);
            System.out.println("Y: " + y);
                if(coordinates[y][x] == player){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(coordinates[y][x] != player){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }

                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    return player;                  // Gibt die Schleife den Gewinner zurück
                }
                x++;
                if(x == sizeY) x = 1;
            }


/*

        //3 von oben links nach unten rechts
        if(coordinates[1][1] == player && coordinates[2][2] == player && coordinates[3][3] == player){
            //System.out.println("Spieler " + player + " hat gewonnen");
            return player;
        }
        //3 von oben rechts nach unten links
        if(coordinates[1][3] == player && coordinates[2][2] == player && coordinates[3][1] == player){
            //System.out.println("Spieler " + player + " hat gewonnen");
            return player;
        }
        */
        return ' ';
    }

    private int getUserInput(String Question, int[] validAnswers){
        Scanner Eingabe = new Scanner(System.in);
        System.out.println(Question);
        while(!Eingabe.hasNextInt()){
            ErrorMsg = "Bitte nur Zahlen eingeben";
            printErrors();
            Eingabe.next();
        }
        int Answer = Eingabe.nextInt();
        for(int valid: validAnswers)
        {
            if(Answer == valid) {
                //System.out.println("Valid: " + valid);

                return Answer;
            }
        }
        ErrorMsg = "Bitte nur gültige Zahl eingeben";
        printErrors();
        return 0;
    }
}



