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
    //aber nur über setter und getter methoden
    private player player1 = new player();
    private player player2 = new player();
    private player activePlayer = new player();
    private board Board = new board();
    private UserInterface Console = new UserInterface();
    private player winner;
    private String WinCondition;
    private int[][] WinCoordinates;
    private int GameStatus = 0;

    void start(){
        this.GameStateHandler();    //Ruft den Zustandsautomaten auf
    }

      private void GameStateHandler(){ //Zustandsautomat zuständig für die Regelung des Spielablaufs in Schritten

        while(GameStatus <=6){
            switch(GameStatus){
                case 0: // Vor dem Spiel die Regeln und bedienung erklären
                        this.Console.printWelcome();
                        this.Console.printHelp();
                        this.changeGameState(1); //spiel initialisieren
                    break;
                case 1: //Spiel wird gestartet
                        this.initGame();
                        this.changeGameState(2);
                    break;
                case 2: //Spiel ist im Gange
                        this.playGame();
                    break;
                case 3: //Ein Spieler hat gewonnen
                        this.showWinner(); //zeige den Gewinner an und hebe die spielsteine hervor die zum sieg geführt haben
                        this.changeGameState(5); //nochmal spielen?
                    break;
                case 4: // Unentschieden
                        this.showDraw();
                        this.showBoard();
                        this.changeGameState(5);
                    break;
                case 5: //Nochmal spielen
                       Playagain();
                    break;
                default: //Beenden
                    //System.out.println("Bye");
                    changeGameState(7);
                    break;
            }
        }
    }

    private void changeGameState(int newGameState){
        this.GameStatus = newGameState;
    }

    private void initGame(){
        this.player1.setPlayerID(1);
        this.player1.setPlayerSymbol('X');

        //player player2 = new player();
        this.player2.setPlayerID(-1);
        this.player2.setPlayerSymbol('O');

        this.Board.setSize(7,6);
        this.Board.initialize(0);
    }

    private void playGame(){
        this.toggleActivePlayer();//Aktiven Spieler wechseln (sorgt bei erneutem spielen dafür das der verlierer anfangen darf :))
        this.showBoard();         //Spielfeld anzeigen
        this.showActivePlayer();  //Meldung wer dran ist ausgeben
        this.makeAMove();         //User nach neuem Spielzug fragen und diesen durchführen
        this.checkWinner( Board.getCoordinates(), getActivePlayer()); //Prüfen ob jemand gewonnen hat
        this.checkDraw(Board.getRows(), Board.getCols(), Board.getCoordinates());                      //Prüfen ob Unentschieden
    }

    //Methode zum wechseln des aktiven Spielers
    private void toggleActivePlayer(){

        if (this.getActivePlayer().getPlayerID() == this.player1.getPlayerID()){      //Wenn der aktive Spieler derzeit Spieler1 ist, setze Spieler2 als aktiven Spieler
            this.getActivePlayer().setPlayerID(this.player2.getPlayerID());
            this.getActivePlayer().setPlayerSymbol(this.player2.getPlayerSymbol());
        }else{                                                               //Wenn der aktive Spieler derzeit nicht Spieler1 ist, setze Spieler1 als aktiven Spieler
            this.getActivePlayer().setPlayerID(this.player1.getPlayerID());
            this.getActivePlayer().setPlayerSymbol(this.player1.getPlayerSymbol());
        }
    }

    //aktiven Spieler anzeigen
    private void showActivePlayer(){
        this.Console.printActivePlayer(this.getActivePlayer());
    }
    //aktiven spieler abfragen
    private player getActivePlayer(){   //Methode gibt den aktuell aktiven Spieler zurück
        return this.activePlayer;
    }

    private void showBoard(){ //Methode die die Methode zum anzeigen des Spielfelds auruft
        int cols = this.getColCount();
        int rows = this.getRowCount();
        String header = this.Board.getHeader();
        int[][] coordinates = this.getCoordinates();
        this.Console.printBoard(cols,rows,header,coordinates, this.player1, this.player2); //Hole anzahl spalten, anzahl Reihen sowie Überschrift und FeldEinträge und gib diese aus
    }

    //Methode um neuen Spielstein aufs Feld zu setzen
    private void makeAMove(){

        int col = this.askColumn();                                     //Der User wird nach einer Spalte gefragt
        this.Board.setCoordinates(newMove(col,this.getActivePlayer())); //Eingegebene Spalte und aktiver Spieler werden an newMove übergeben
                                                                        //NewMove prüft die spalte ob platz ist, und trägt das Spielersymbol ein
                                                                        //SetCoordinates speichert die neuen Daten im Board objekt
    }

    private int askColumn(){
        int answer;
        int[] validAnswers = new int[]{1,2,3,4,5,6,7};

        while(true) {
            answer = this.Console.ask("Column: ",validAnswers);
            if (this.checkColumn(answer)) {
                break;
            }
        }
        return answer;
    }

    private boolean checkColumn(int col){
        int[][]coordinates = this.getCoordinates();
        if(coordinates[1][col] == 0){
            return true;
        }else {
            System.out.println("Column " + col + " is full");
            System.out.println("Please choose another one");
        }
        return false;
    }

    private int[][] newMove(int col, player player){   //Methode um einen Spielstein auf das Spielfeld zu setzen. Die größe des Spielfelds, sowie die aktuell gesetzten Steine und der Spieler der den Spielzug durchführt werden übergeben
        int rows = getRowCount();                           //Anzahl Zeilen ermitteln
        int[][] coordinates = this.getCoordinates();  //Array mit bisherigen Spielsteinen holen
        for(int row = rows; row > 0; row--){   //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(checkField(row,col,coordinates)){                //Ist der Eintrag im Koordianten Array leer
                coordinates[row][col] = player.getPlayerID();     //Wird das Zeichen des Spieler eingetragen
                break;                             //Schleife unterbrechen
            }
        }
        return coordinates;
    }

    private int getRowCount(){
        return this.Board.getRows();
    }

    private int getColCount(){
        return this.Board.getCols();
    }


    private int[][] getCoordinates(){
        return this.Board.getCoordinates();
    }

    private boolean checkField(int row, int col, int[][]coordinates) {  //Methode um zu prüfen ob ein einzelnes Spielfeld noch frei ist. Die position des zu setzenden Spielsteins sowie die bisher gesetzen Spielsteine werden übergeben
        if (coordinates[row][col] == 0) {                               //Befindet sich im angegebenen Feld eine 0 ist das Feld leer
            return true;
        }
        return false;
    }

    private void checkWinner(int[][] coordinates,player player){  //Methode zur Prüfung ob es einen Gewinner gibt
        int rows = this.getRowCount();
        int cols = this.getColCount();
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
                        }else{ //Points = 0;
                             break;
                        }
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
                        }else {//Points = 0;
                            break;
                        }
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

    //Wurde ein Gewinner ermittelt sorgt die methode für die Speicherung des Gewinners, der Siegbedingung
    // und der Koordinaten der Spielsteine die zum Sieg geführt haben
    private void setWinner(player winner, String WindCondition, int[][] WinCoordinates) {
        this.winner = winner;
        this.WinCondition = WindCondition;
        this.setWinCoordinates(WinCoordinates);
        this.changeGameState(3);    //Spielstatus 3 -> Gewinner anzeigen
    }

    private void setWinCoordinates(int[][] WinCoordinates){
        this.WinCoordinates = WinCoordinates;
    }

    private void showWinner(){
        Console.printWinner(getWinner(),getWinCondition());
        Console.printWinnerBoard(this.Board.getCols(),this.Board.getRows(),this.Board.getHeader(),this.Board.getCoordinates(), this.player1, this.player2,this.WinCoordinates);
    }

    private player getWinner(){
        return this.winner;
    }

    private String getWinCondition(){
        return this.WinCondition;
    }

    private void checkDraw(int rows, int cols, int[][] coordinates){            //Methode zur Prüfung bo es noch freie felder auf dem spielfeld gibt -> True bei unentschieden
        for(int y = 1; y <= rows; y++){    //Alle Zeilen
            for(int x = 1; x <= cols; x++){    // Und alle Felder in der Reihe abarbeiten
                if (coordinates[y][x] == 0){ //Prüfen ob leer
                 return;                      //Wenn leer, kein unentschieden
                }
            }
        }
        this.changeGameState(4); //Wird kein freies feld gefunden endet das Spiel mit unentschieden
    }

    private void showDraw(){
        Console.printDraw();
    } //Anzeige der Meldung das es Unentschieden ist

    private void Playagain(){
        if ( Console.askPlayAgain() == 1){
            this.changeGameState(1); //Spiel neu initialisieren
        }else{
            this.changeGameState(6); //Spiel beenden
        }
    }
}

class board {      //Klasse für das Spielfeld
    //SpielfeldEigenschaften
    private int rows;           //höhe
    private int cols ;          //breite
    private int[][] coordinates;//Spielsteine

    void setSize(int cols, int rows){   //Methode zum festlegen der Spielfeldgröße
        this.rows = rows;
        this.cols = cols;
        this.coordinates = new int[this.rows+1][this.cols+1];   //TODO +1 aktuell damit bei user eingaben von 1 und nicht von 0 angefangen wird
    }

    void initialize(int FillWithValue){     //Das Array für die Spielersymbole wird zu beginn des Spiels mit 0en gefüllt

        for(int y  = 1; y < this.rows+1; y++ ){
            for(int x  = 1; x < this.cols+1; x++ ){
                this.coordinates[y][x] = FillWithValue;
            }
        }
    }

    int[][] getCoordinates(){
        return this.coordinates;
    }   //Array mit den Spielsteine übergeben

    void setCoordinates(int[][] coordinates) {//Array mit Spielsteinen aktualisieren
        this.coordinates = coordinates;
    }

    int getCols(){
        return this.cols;
    }   //Array breite übergeben
    int getRows(){
        return this.rows;
    }   //Array höhe übergeben

    String getHeader(){                   //Überschrift mit Spaltennummerrierung übergeben
        String header = "";
        for(int i = 1; i<=getCols();i++ ){
            header = header + "  " + i + "  ";
        }
        return header;
    }
}

class player {      //Definiert die Eigenschaften eines Spielers
    private int ID;
    private char symbol;

    void setPlayerID(int playerID){
        this.ID =  playerID;
    }
    void setPlayerSymbol( char playerSymbol){
        this.symbol = playerSymbol;
    }
    int getPlayerID(){
        return this.ID;
    }
    char getPlayerSymbol(){
        return this.symbol;
    }

}

class UserInterface {   //Alle Aus- und Eingaben innerhalb der Klasse UserInterface

    void printWelcome(){
        System.out.println("Welcome to 4Wins v1.0");
    }

    void printHelp(){
        System.out.println();
        System.out.println("To win this game you need to get 4 of your marks in a row. The row can be vertical, horizontal or diagonal");
        System.out.println("When its your turn, you are able to place your mark by typing in the number of the column where you want to place it");
    }

    //Ausgabe des Spielfelds TODO ggf. in FourWins ein String array mit erforderlichen Inhalten erstellen und hier nurnoch ausgeben
    void printBoard(int cols, int rows, String Header, int[][]coordinates, player player1, player player2){
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
    //Ausgabe des Spielfelds inkl. markierung der Spielsteine die zum Sieg geführt haben
    void printWinnerBoard(int cols, int rows, String Header, int[][]coordinates, player player1, player player2, int[][]WinCoordinates){
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

    void printWinner(player winner, String WinCondition){
        System.out.println("Congrats, Player "+ winner.getPlayerSymbol() +" has won the game");
        System.out.println(WinCondition);
    }

    void printDraw(){
        System.out.println("Draw! The Game is over because all fields on the board are full");
    }

    int askPlayAgain(){
        int answer;
        int[] validAnswers = new int[]{0,1};
        answer = ask("Play again? 1 = Yes / 0 = No",validAnswers);
        //System.out.println(answer);
        return answer;
    }

    void printActivePlayer(player activePlayer){
        System.out.println("Its your turn Player " + activePlayer.getPlayerSymbol());
    }

    int ask(String question, int[] validAnswers ){
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