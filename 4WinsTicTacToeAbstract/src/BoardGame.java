//Grundgerüst für TicTacToe und 4Gewinnt von denen beide abgeleitet werden

abstract class BoardGame {
    protected int GameStatus;
    protected Player player1;
    protected Player player2;
    protected Player activePlayer;
    protected Player winner;
    protected String WinCondition;
    protected int[][] WinCoordinates;
    protected Board Board;
    protected UserInterface Console;


    public BoardGame() {
        player1 = new Player();
        player2 = new Player();
        activePlayer = new Player();
        GameStatus = 0;
        Board = new Board();
        Console = new UserInterface();
    }

    protected void changeGameState(int newGameState) {
        this.GameStatus = newGameState;
    }

    protected void initGame(int rows, int cols, char Player1Symbol, char Player2Symbol) {
        this.player1.setPlayerID(1);
        this.player1.setPlayerSymbol(Player1Symbol);

        this.player2.setPlayerID(-1);
        this.player2.setPlayerSymbol(Player2Symbol);

        this.Board.setSize(cols, rows);
        this.Board.initialize(0);

        this.WinCoordinates = new int[2][4];
    }

    //Methode zum wechseln des aktiven Spielers
    protected void toggleActivePlayer() {

        if (this.activePlayer.getPlayerID() == this.player1.getPlayerID()) {      //Wenn der aktive Spieler derzeit Spieler1 ist, setze Spieler2 als aktiven Spieler
            this.activePlayer.setPlayerID(this.player2.getPlayerID());
            this.activePlayer.setPlayerSymbol(this.player2.getPlayerSymbol());
        } else {                                                               //Wenn der aktive Spieler derzeit nicht Spieler1 ist, setze Spieler1 als aktiven Spieler
            this.activePlayer.setPlayerID(this.player1.getPlayerID());
            this.activePlayer.setPlayerSymbol(this.player1.getPlayerSymbol());
        }
    }

    //aktiven Spieler anzeigen
    protected void showActivePlayer() {
        this.Console.printActivePlayer(this.activePlayer.getPlayerSymbol());
    }

    protected void showBoard() { //Methode die die Methode zum anzeigen des Spielfelds auruft
        int cols = this.Board.getCols();      //Board größe, koordinaten der vorhandenen Spielsteine und header ermitteln
        int rows = this.Board.getRows();
        String header = this.Board.getHeader();
        int[][] coordinates = this.Board.getCoordinates();
        //das Koordinaten array wird in ein String-Array umgewandelt und ausgegeben
        this.Console.PrintBoard(cols, rows, header, interpretCoordinates(coordinates));
    }

    protected String[][] interpretCoordinates(int[][] coordinates) {
        int rows = this.Board.getRows();
        int cols = this.Board.getCols();
        String[][] BoardContent = new String[rows + 1][cols + 1];
        int[][] WinCoordinates = this.WinCoordinates;
        int i = 0;
        String ANSI_RED = "\u001B[31m";
        String ANSI_WHITE = "\033[0m";

        for (int y = 1; y < rows + 1; y++) {
            for (int x = 1; x < cols + 1; x++) {

                if (WinCoordinates != null) {                   //es gibt einen gewinner
                    if (i > 3) i = 0;
                    if (y == WinCoordinates[0][i] && x == WinCoordinates[1][i]) {   //prüfe ob aktuelle Kkoordinate eine gewinner koordinate ist
                        i++;                                                        //Ist es eine koordinate die zum sieg geführt hat, gib sie rot aus
                        if (coordinates[y][x] == player1.getPlayerID()) {
                            BoardContent[y][x] = ANSI_RED + "[ " + player1.getPlayerSymbol() + " ]" + ANSI_WHITE;
                        } else if (coordinates[y][x] == player2.getPlayerID()) {
                            BoardContent[y][x] = ANSI_RED + "[ " + player2.getPlayerSymbol() + " ]" + ANSI_WHITE;
                        } else {
                            BoardContent[y][x] = "[ " + " " + " ]";
                        }
                    } else {                                                          //es gibt einen sieger aber die aktuelle koordinate gehört nicht zur sieger koordinate -> weisse ausgabe
                        if (coordinates[y][x] == player1.getPlayerID()) { //Symbol gehört Player1
                            BoardContent[y][x] = "[ " + player1.getPlayerSymbol() + " ]";
                        } else if (coordinates[y][x] == player2.getPlayerID()) {
                            BoardContent[y][x] = "[ " + player2.getPlayerSymbol() + " ]";
                        } else {
                            BoardContent[y][x] = "[ " + " " + " ]";
                        }
                    }
                } else {                                                            //es gibt keinen sieger, alles normal ausgeben
                    if (coordinates[y][x] == player1.getPlayerID()) {
                        BoardContent[y][x] = "[ " + player1.getPlayerSymbol() + " ]";
                    } else if (coordinates[y][x] == player2.getPlayerID()) {
                        BoardContent[y][x] = "[ " + player2.getPlayerSymbol() + " ]";
                    } else {
                        BoardContent[y][x] = "[ " + " " + " ]";
                    }
                }
            }
        }
        return BoardContent;
    }

    protected boolean checkField(int row, int col, int[][]coordinates) {  //Methode um zu prüfen ob ein einzelnes Spielfeld noch frei ist. Die position des zu setzenden Spielsteins sowie die bisher gesetzen Spielsteine werden übergeben
        if (coordinates[row][col] == 0) {                               //Befindet sich im angegebenen Feld eine 0 ist das Feld leer
            return true;
        }
        return false;
    }

    protected void checkRow(int AmountofSymbolsToWin) {
        int rows = this.Board.getRows();
        int cols = this.Board.getCols();
        int coordinates[][] = this.Board.getCoordinates();
        int Points = 0;
        String WinCondition;
        //waagerecht
        for (int y = rows; y > 0; y--) {             //Läuft von der untersten bis zur obersten Reihe (6-1)
            int[][] WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
            for (int x = 1; x <= cols; x++) {       //für jedes Feld in der Reihe
                if (coordinates[y][x] == activePlayer.getPlayerID()) {    //Prüfung ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                                     //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                    WinCoordinates[0][Points - 1] = y;
                    WinCoordinates[1][Points - 1] = x;
                }
                if (coordinates[y][x] != activePlayer.getPlayerID()) {    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 setzen
                }
                if (Points == AmountofSymbolsToWin) {                   //Wurden vier gleiche Zeichen in folge gefunden
                    WinCondition = AmountofSymbolsToWin + " in one row";
                    setWinner(activePlayer, WinCondition, WinCoordinates);                  // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;
        }
    }
    protected void checkCol(int AmountofSymbolsToWin) {
        int rows = this.Board.getRows();
        int cols = this.Board.getCols();
        int coordinates[][] = this.Board.getCoordinates();
        //Senkrecht
        int Points = 0;
        for (int x = cols; x > 0; x--) {             //Läuft von Spalte ganz rechts bis zum anfang
            int[][] WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
            for (int y = 1; y <= rows; y++) {       //Prüft jedes Feld in der Spalte
                if (coordinates[y][x] == activePlayer.getPlayerID()) {    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                    WinCoordinates[0][Points - 1] = y;
                    WinCoordinates[1][Points - 1] = x;
                }
                if (coordinates[y][x] != activePlayer.getPlayerID()) {    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                //System.out.print(Points);
                if (Points == AmountofSymbolsToWin) {                   //Wurden vier gleiche Zeichen in folge gefunden
                    WinCondition = AmountofSymbolsToWin + " in one Column";
                    setWinner(activePlayer, WinCondition, WinCoordinates);                   // Gibt die Schleife den Gewinner zurück
                }else{
                    this.changeGameState(7);    //Wenn kein gewinner mit programm fortfahren
                }
            }
            Points = 0;                           //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
    }

    protected void setWinner(Player winner, String WindCondition, int[][] WinCoordinates) {
        this.winner = winner;
        this.WinCondition = WindCondition;
        this.WinCoordinates =WinCoordinates;
        this.changeGameState(8);    //Spielstatus 8 -> Gewinner anzeigen
    }

    protected void showWinner() {
        this.showBoard();
        Console.printWinner(this.winner.getPlayerSymbol(), this.WinCondition);
    }

    protected void checkDraw(int rows, int cols, int[][] coordinates){            //Methode zur Prüfung bo es noch freie felder auf dem spielfeld gibt -> True bei unentschieden
        for(int y = 1; y <= rows; y++){    //Alle Zeilen
            for(int x = 1; x <= cols; x++){    // Und alle Felder in der Reihe abarbeiten
                if (coordinates[y][x] == 0){ //Prüfen ob leer
                    this.changeGameState(2);
                    return;                      //Wenn leer, kein unentschieden
                }
            }
        }
        this.changeGameState(9); //Wird kein freies feld gefunden endet das Spiel mit unentschieden
    }

    protected void showDraw() {
        this.showBoard();
        Console.printDraw();
    }

    protected void Playagain(){
        if ( Console.askPlayAgain() == 1){
            this.changeGameState(1); //Spiel neu initialisieren
        }else{
            this.changeGameState(6); //Spiel beenden
        }
    }

}


