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

    private void askForColumn(){
        x = 0;
        y = 0;

        int[] validInputX = new int[sizeX+1];
        for (int i = 1; i <= sizeX; i++) {
            validInputX[i] = i;
        }
        while(x == 0) {
            x = getUserInput("Bitte gib die Spaltennummer ein (1-7):", validInputX);
            if(coordinates[1][x] != ' '){
                System.out.println("Spalte " + x + " ist bereits voll");
                x=0;
            }
        }
    }

    private void newMove (int X){
        if(moves %2 == 1){
            player = player1;
        }else player = player2;

        for(int i = sizeY; i > 0; i--){
            if(coordinates[i][X] == ' '){
                coordinates[i][X] = player;
                moves++;
                break;
            }
        }

    }

    private char checkWinner(){
        //Prüfung ob es einen Gewinner gibt
        //Schleife für vier waagerechte

        int Points = 0;

        //waagerecht
        for(int y = sizeY; y > 0; y--){
            for(int x = 1; x < sizeX+1; x++){
                if(coordinates[y][x] == player){
                    Points++;
                }
                if(coordinates[y][x] != player){
                    Points = 0;
                }
                //System.out.print(Points);
                if(Points == 4 ){
                    return player;
                }
            }
        }
        //Senkrecht
        Points = 0;
        for(int x = sizeX; x > 0; x--){
            for(int y = 1; y < sizeY+1; y++){
                if(coordinates[y][x] == player){
                    Points++;
                }
                if(coordinates[y][x] != player){
                    Points = 0;
                }
                //System.out.print(Points);
                if(Points == 4 ){
                    return player;
                }
            }
        }

/*
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



