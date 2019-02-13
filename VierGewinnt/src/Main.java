import java.util.Scanner;

public class Main {

    static String ErrorMsg;
    static String GameModeList = "\n3: TicTacToe\n4: VierGewinnt";
    static int[] validGameModes = new int[]{3, 4};
    static int Gamemode;

    public static int getUserInput(String Question, int[] validAnswers){
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
            //MyGame.start();
        }
    }

}
class TicTacToe {
    char player1 = 'X';
    char player2 = 'O';
    char player;
    int sizeX = 3;
    int sizeY = 3;
    int x;
    int y;
    char[][] coordinates = new char[sizeY+1][sizeX+1];
    String[][] board = new String[sizeY+1][sizeX+1];
    int moves = 0;
    char winner = ' ';
    String Msg;
    String ErrorMsg = "";

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

    public void initCoordinates(){
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                coordinates[y][x] = ' ';
            }
        }
    }

    public void generateBoard(){
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                board[y][x] = "[ " + coordinates[y][x] + " ]";
            }
        }
    }

    public void printBoard(){
        for(int y = 1; y <= sizeY; y++){
            for(int x = 1; x <= sizeX; x++){
                System.out.print(board[y][x]);
            }
            System.out.println();
        }
    }
    public void printErrors(){
        System.out.println(ErrorMsg);
    }
    public void askForCoordinates(){
        int[] validInputY = new int[sizeY+1];
        for (int i = 1; i <= sizeY; i++) {
            validInputY[i] = i;
        }
        y = getUserInput("Bitte gib die Y-Koordinate ein:", validInputY);
        int[] validInputX = new int[sizeX+1];
        for (int i = 1; i <= sizeX; i++) {
            validInputX[i] = i;
        }
        x = getUserInput("Bitte gib die X-Koordinate ein:", validInputX);

    }

    public void newMove (int Y, int X){
        if(moves %2 == 1){
            player = player1;
        }else player = player2;
        coordinates[Y][X] = player;
        moves++;
    }

    public char checkWinner(){

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


    public int getUserInput(String Question, int[] validAnswers){
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
                return Answer;
            }
        }
        return 0;
    }
}

class VierGewinnt{
    char player1 = 'X';
    char player2 = 'O';
    int sizeX = 7;
    int sizeY = 6;
    int x;
    int y;
    char[][] coordinates = new char[sizeY+1][sizeX+1];
    String[][] board = new String[sizeY+1][sizeX+1];
    int moves = 0;
    int winner = 0;
    String ErrorMsg;
}



