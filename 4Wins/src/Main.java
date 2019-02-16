import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FourWins MyGame = new FourWins(); //objekt vom Typ FourWins wird erzeugt
        MyGame.start();
    }

}
class FourWins{
    //Attribute der Klasse FourWins
    //innerhalb der Klasse nutzbar
    private player player1 = new player();
    private player player2 = new player();
    private player activePlayer = new player();
    private board Board = new board();
    private UserInterface Console = new UserInterface();
    private player winner;
    private String WinCondition;
    private int[][] WinCoordinates;

    public void start(){
        GameStateHandler(0);
    }

    //Constructor Methode beinhaltet den gesamten SPielablauf
    public void GameStateHandler(int GameStatus){

        while(GameStatus <=5){
            switch(GameStatus){
                case 0: // Vor dem Spiel die Regeln und bedienung erklären
                        this.Console.printWelcome();
                        this.Console.printHelp();
                        GameStatus = 1;
                    break;
                case 1: //Spiel wird gestartet
                        this.initGame();
                    break;
                case 2: //Spiel ist im Gange
                        this.playGame();
                    break;
                case 3: //Ein Spieler hat gewonnen
                        this.showWinner();
                        //this.showBoard();
                        GameStatus = 5;
                    break;
                case 4: // Unentschieden
                        this.showDraw();
                        GameStatus = 5;
                    break;
                case 5: //Nochmal spielen
                       Playagain();
                    break;
                default: //Beenden
                    System.out.println("Bye");
                    break;
            }
        }
    }

    private void initGame(){
        this.player1.setPlayerID(1);
        this.player1.setPlayerSymbol('X');

        //player player2 = new player();
        this.player2.setPlayerID(-1);
        this.player2.setPlayerSymbol('O');

        this.Board.setSize(7,6);
        this.Board.initialize(0);

        GameStateHandler(2);
    }

    private void playGame(){
        //Aktiven Spieler wechseln (sorgt bei erneutem spielen dafür das der verlierer anfangen darf :))
        toggleActivePlayer();
        showBoard();
        showActivePlayer();
        makeAMove();
        checkWinner(Board.getRows(), Board.getCols(), Board.getCoordinates(), getActivePlayer());
        checkDraw(Board.getRows(), Board.getCols(), Board.getCoordinates());
    }

    private void showBoard(){ //Methode die die Methode zum anzeigen des Spielfelds auruft
        Console.printBoard(Board.getCols(),Board.getRows(),Board.getHeader(),Board.getCoordinates(), player1, player2); //Hole anzahl spalten, anzahl Reihen sowie Überschrift und FeldEinträge und gib diese aus
    }

    private void showActivePlayer(){
        this.Console.printActivePlayer(getActivePlayer());
    }

    //Methode um neuen Spielstein aufs Feld zu setzen
    private void makeAMove(){

        //Der User wird nach einer Spalte gefragt, Board Koordianten werden mit übergeben um zu prüfen ob in der eingegebenen Spalte noch platz ist ->
        //Eingegebene Spalte wird an NewMove übergeben, Gesamtanzahl der Zeilen im Spielfled, alle Spielstein Koordinaten und der aktive spieler werden an NewMove übergeben
        //NewMove gibt ein Koordinaten Array zurück, das alle bisherigen und den neu gesetzten Spielstein enthält
        // Die neuen Koordinaten werden in das Board objekt geschrieben.
        Board.setCoordinates(newMove(Console.askColumn(Board.getCoordinates()),Board.getRows(),Board.getCoordinates(),getActivePlayer()));
    }

    //Methode zum wechseln des aktiven Spielers
    private void toggleActivePlayer(){

        if (getActivePlayer().getPlayerID() == player1.getPlayerID()){      //Wenn der aktive Spieler derzeit Spieler1 ist, setze Spieler2 als aktiven Spieler
            getActivePlayer().setPlayerID(player2.getPlayerID());
            getActivePlayer().setPlayerSymbol(player2.getPlayerSymbol());
        }else{                                                               //Wenn der aktive Spieler derzeit nicht Spieler1 ist, setze Spieler1 als aktiven Spieler
            getActivePlayer().setPlayerID(player1.getPlayerID());
            getActivePlayer().setPlayerSymbol(player1.getPlayerSymbol());
        }
    }

    private player getActivePlayer(){   //Methode gibt den aktuell aktiven Spieler zurück
        return activePlayer;
    }

    private boolean checkField(int row, int col, int[][]coordinates) {  //Methode um zu prüfen ob ein Spielfeld noch frei ist. Die Größe des Spielfelds sowie die bisher gesetzen Spielsteine werden übergeben
        //TODO : Größe des Spielfeld anhand der Array größe ermitteln -> weniger parameter erforderlich
        if (coordinates[row][col] == 0) {                               //Befindet sich im angegebenen Feld eine 0 ist das Feld leer
            return true;
        }
        return false;
    }

    public int[][] newMove(int col, int rows,int[][] coordinates, player player){   //Methode um einen Spielstein auf das Spielfeld zu setzen. Die größe des Spielfelds, sowie die aktuell gesetzten Steine und der Spieler der den Spielzug durchführt werden übergeben
        //TODO : Größe des Spielfeld anhand der Array größe ermitteln -> weniger parameter erforderlich
        for(int row = rows; row > 0; row--){   //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(checkField(row,col,coordinates)){                //Ist der Eintrag im Koordianten Array leer
                coordinates[row][col] = player.getPlayerID();     //Wird das Zeichen des Spieler eingetragen
                break;                             //Schleife unterbrechen
            }
        }
        return coordinates;
    }


    private void checkWinner(int rows, int cols, int[][] coordinates,player player){  //Methode zur Prüfung ob es einen Gewinner gibt
        //TODO : Größe des Spielfeld anhand der Array größe ermitteln -> weniger parameter erforderlich (rows,cols fallen weg)
        int Points = 0;
        String WinCondition;
        //waagerecht
        for(int y = rows; y > 0; y--){             //Läuft von der untersten bis zur obersten Reihe (6-1)
            int[][] WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
            for(int x = 1; x <= cols; x++){       //für jedes Feld in der Reihe
                if(coordinates[y][x] == player.getPlayerID()){    //Prüfung ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                                     //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                    WinCoordinates[0][Points-1] = y;
                    WinCoordinates[1][Points-1] = x;
                }
                if(coordinates[y][x] != player.getPlayerID()){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 setzen
                }
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    WinCondition = "Four in one row";
                    setWinner(player,WinCondition,WinCoordinates);                  // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;
        }

        //Senkrecht
        Points = 0;
        for(int x = cols; x > 0; x--){             //Läuft von Spalte ganz rechts bis zum anfang
            int[][] WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
            for(int y = 1; y <= rows; y++){       //Prüft jedes Feld in der Spalte
                if(coordinates[y][x] == player.getPlayerID()){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                    WinCoordinates[0][Points-1] = y;
                    WinCoordinates[1][Points-1] = x;
                }
                if(coordinates[y][x] != player.getPlayerID()){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                //System.out.print(Points);
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    WinCondition = "Four in one Column";
                    setWinner(player,WinCondition,WinCoordinates);                   // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;                           //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
        //vier diagonale von links unten nach rechts oben
        Points = 0;
        int[][] WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = rows; y >= 3; y--){             //In jeder Spalte Von der untersten Reihe bis zur 4. von unten durchlaufen
                if(coordinates[y][x] == player.getPlayerID()){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    WinCoordinates[0][Points-1] = y;
                    WinCoordinates[1][Points-1] = x;
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen bis drei spalten weiter rechts erreicht wurden
                        row--;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (coordinates[row][col] == player.getPlayerID()) {    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;
                            WinCoordinates[0][Points-1] = row;
                            WinCoordinates[1][Points-1] = col;
                        }else{ Points = 0; break;}
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            WinCondition = "Four diagonal -> lower left to upper right";
                            setWinner(player,WinCondition,WinCoordinates);                   // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
                Points = 0; //Nach jeder Zeile wird der Punktezähler zurückgesetzt
            }
            Points = 0; //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }

        //vier diagonale von links oben nach rechts unten
        Points = 0;
        //int x = 1;
        WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = 1; y <= 3; y++){             //In jeder Spalte Von der obersten Reihe bis zur 3. von oben durchlaufen

                if(coordinates[y][x] == player.getPlayerID()){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    WinCoordinates[0][Points-1] = y;
                    WinCoordinates[1][Points-1] = x;
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen
                        row++;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (coordinates[row][col] == player.getPlayerID()) {    //Prüfen ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;                             //Einen punkt für das zeichen gutschreiben
                            WinCoordinates[0][Points-1] = row;
                            WinCoordinates[1][Points-1] = col;
                        }else {Points = 0; break;}
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            WinCondition = "4 diagonal -> from upper left to lower right";
                            setWinner(player,WinCondition,WinCoordinates);                   // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
                Points = 0; //Nach jeder Zeile wird der Punktezähler zurückgesetzt
            }
            Points = 0; //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
    }

    public void setWinner(player winner, String WindCondition, int[][] WinCoordinates) {
        this.winner = winner;
        this.WinCondition = WindCondition;
        this.setWinCoordinates(WinCoordinates);
        GameStateHandler(3);
    }

    public void setWinCoordinates(int[][] WinCoordinates){
        this.WinCoordinates = WinCoordinates;
    }

    public void showWinner(){
        Console.printWinner(getWinner(),getWinCondition());
        Console.printWinnerBoard(Board.getCols(),Board.getRows(),Board.getHeader(),Board.getCoordinates(), player1, player2,WinCoordinates);
    }

    public player getWinner(){
        return this.winner;
    }

    public String getWinCondition(){
        return this.WinCondition;
    }

    public void showDraw(){
        Console.printDraw();
    }

    public void Playagain(){
        if ( Console.askPlayAgain() == 1){
            GameStateHandler(1);
        }else{
            GameStateHandler(6); //TODO anderen weg finden das programm zu beenden, auf die akuelle art endet es nicht
        }
    }

    private void checkDraw(int rows, int cols, int[][] coordinates){            //Methode zur Prüfung bo es noch freie felder auf dem spielfeld gibt -> True bei unentschieden
        for(int y = 1; y <= rows; y++){    //Alle Zeilen
            for(int x = 1; x <= cols; x++){    // Und alle Felder in der Reihe abarbeiten
                if (coordinates[y][x] == 0){ //Prüfen ob leer
                 return;                      //Wenn leer, kein unentschieden
                }
            }
        }
       // System.out.println("Draw!");    //Wenn kein leeres feld gefunden, unentschieden
       // System.out.println("Game OVER!");
        GameStateHandler(4);
    }
}

class board {
    private int rows;
    private int cols ;
   // private int[][] coordinates = new int[this.rows+1][this.cols+1]; //+1 damit wir bei 1 und nicht bei null starten können
   private int[][] coordinates;

    public void setSize(int cols, int rows){
        this.rows = rows;
        this.cols = cols;
        this.coordinates = new int[this.rows+1][this.cols+1];
    }

    public void initialize(int FillWithValue){

        for(int y  = 1; y < this.rows+1; y++ ){
            for(int x  = 1; x < this.cols+1; x++ ){
                this.coordinates[y][x] = FillWithValue;
            }
        }
    }

    public int[][] getCoordinates(){
        return this.coordinates;
    }

    public void setCoordinates(int[][] coordinates) {
        this.coordinates = coordinates;
    }

    public int getCols(){
        return this.cols;
    }
    public int getRows(){
        return this.rows;
    }

    public String getHeader(){
        String header = "";
        for(int i = 1; i<=getCols();i++ ){
            header = header + "  " + i + "  ";
        }
        return header;
    }
}

class player {
    private int ID;
    private char symbol;

    public void setPlayerID(int playerID){
        this.ID =  playerID;
    }
    public void setPlayerSymbol( char playerSymbol){
        this.symbol = playerSymbol;
    }
    public int getPlayerID(){
        return this.ID;
    }
    public char getPlayerSymbol(){
        return this.symbol;
    }

}

class UserInterface {

    public void printWelcome(){
        System.out.println("Welcome to 4Wins v1.0");
    }

    public void printHelp(){
        System.out.println();
        System.out.println("To win this game you need to get 4 of your marks in a row. The row can be vertical, horizontal or diagonal");
        System.out.println("When its your turn, you are able to place your mark by typing in the number of the column where you want to place it");
    }

    public void printBoard(int cols, int rows, String Header, int[][]coordinates, player player1, player player2){
        System.out.println();
        System.out.println(Header);
        for(int y  = 1; y < rows+1; y++ ){
            for(int x  = 1; x < cols+1; x++ ){
                System.out.print("[ ");
                if(coordinates[y][x] == player1.getPlayerID() ){
                    System.out.print(player1.getPlayerSymbol());
                }else if(coordinates[y][x] == player2.getPlayerID()) {
                    System.out.print(player2.getPlayerSymbol());
                }else{
                    System.out.print(" ");
                }
                System.out.print(" ]");
            }
            System.out.println();
        }
    }

    public void printWinnerBoard(int cols, int rows, String Header, int[][]coordinates, player player1, player player2, int[][]WinCoordinates){
        String ANSI_RED = "\u001B[31m";
        String ANSI_WHITE = "\033[0m";
        int i = 0;
        System.out.println();
        System.out.println(Header);
        for(int y  = 1; y < rows+1; y++ ){
            for(int x  = 1; x < cols+1; x++ ) {
                System.out.print("[ ");
                //for (int i = 0; i <= 3; i++) {
                    if(i>3) i =0;
                    if (y == WinCoordinates[0][i] && x == WinCoordinates[1][i]) {
                        i++;
                        if (coordinates[y][x] == player1.getPlayerID()) {
                            printPlayerSymbol(player1.getPlayerSymbol(), ANSI_RED);
                        } else if (coordinates[y][x] == player2.getPlayerID()) {
                            printPlayerSymbol(player2.getPlayerSymbol(), ANSI_RED);
                        } else {
                            System.out.print(" ");
                        }

                    } else {
                        if (coordinates[y][x] == player1.getPlayerID()) {
                            printPlayerSymbol(player1.getPlayerSymbol(), ANSI_WHITE);
                        } else if (coordinates[y][x] == player2.getPlayerID()) {
                            printPlayerSymbol(player2.getPlayerSymbol(), ANSI_WHITE);
                        } else {
                            System.out.print(" ");
                        }
                    }
               // }
                System.out.print(" ]");
            }
            System.out.println();
        }
    }

    private void printPlayerSymbol(char PlayerSymbol, String ANSIColor){
        String ANSI_WHITE = "\033[0m";
        System.out.print(ANSIColor + PlayerSymbol + ANSI_WHITE);
    }

    public void printWinner(player winner, String WinCondition){
        System.out.println("Congrats, Player "+ winner.getPlayerSymbol() +" has won the game");
        System.out.println(WinCondition);
    }

    public void printDraw(){
        System.out.println("Draw! The Game is over because all fields on the board are full");
    }

    public int askPlayAgain(){
        int answer;
        int[] validAnswers = new int[]{0,1};
        answer = ask("Play again? 1 = Yes / 0 = No",validAnswers);
        //System.out.println(answer);
        return answer;
    }

    public void printActivePlayer(player activePlayer){
        System.out.println("Its your turn Player " + activePlayer.getPlayerSymbol());
    }

    public int askColumn(int[][]coordinates){
        //userInput myInput = new userInput();
        int answer;
        int[] validAnswers = new int[]{1,2,3,4,5,6,7};

        while(true) {
            answer = ask("Column: ",validAnswers);
            //TODO: Prüfung ob die Spalte noch frei sit in der FourWins klasse vornehmen! KEINE LOGIK IM USER Interface
            if (checkColumn(answer,coordinates)) {
                break;
            }
        }
        //System.out.println(answer);
        return answer;
    }
    // TODO: siehe obiges TODO, muss in klasse FourWins verlagert werden
    private boolean checkColumn(int col, int[][]coordinates){
        if(coordinates[1][col] == 0){
            return true;
        }else {
            System.out.println("Column " + col + " is full");
            System.out.println("Please choose another one");
        }
        return false;
    }

    private int ask(String question, int[] validAnswers ){
        Scanner Input = new Scanner(System.in);
        System.out.print(question);
        while(!Input.hasNextInt()){                               //Falls kein Int eingegeben wird
            System.out.println("Invalid Input! Numbers only!");
            Input.next();
        }
        int answer = Input.nextInt();

        while(!validate(answer,validAnswers)){
            System.out.println("Invalid Input!");
            System.out.println(question);
            answer = Input.nextInt();
        }
        return answer;
    }

    private boolean validate(int answer, int[] validAnswers){
        for(int valid: validAnswers)                                //Prüfen ob es sich um eine gültige Antwort handelt
        {
            if(answer == valid) {
                return true;                                      //Wenn gültig, antwort übergeben
            }
        }
        return false;
    }
}