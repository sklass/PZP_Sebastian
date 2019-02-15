import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FourWins MyGame = new FourWins(); //objekt vom Typ FourWins wird erzeugt
    }

}
class FourWins{
    //Constructor Methode beinhaltet den gesamten SPielablauf
    public FourWins(){
        board FourWinsBoard = new board();                                              //Spielfeld Objekt erzeugen
        player player1 = new player(1, 'X', true);      //Spieler Objekte erzeugen
        player player2 = new player(-1, 'O', false);
        player activePlayer = (player1.active) ? player1 : player2;

        FourWinsBoard.initialize();

        while((checkWinner(FourWinsBoard,activePlayer) == 0) && (checkDraw(FourWinsBoard) == false)) { //Schleife ausführen solange Kein Gewinner & kein Unentschiden
            activePlayer = (player1.active) ? player1 : player2;                            //aktiven Spieler festlegen

            FourWinsBoard.print(player1, player2);                                          //Spielfeld ausgeben
            System.out.println("Active Player: "+activePlayer.symbol);
            FourWinsBoard.newMove(FourWinsBoard.askColumn(),activePlayer);                  //Neuen spielzug durchführen
            togglePlayer( player1, player2);                                                //festlegen welcher spieler als nächstes dran ist
        }
        FourWinsBoard.print(player1, player2);                                              //Nachdem das Spiel vorbei ist, nochmal das Spielfeld ausgeben
    }

    private void togglePlayer(player player1, player player2){                              //Über die Methode wird nur der aktiv-Status des Spielers geändert
                                                                                            //wer den nächsten Zug machen darf wird über die Eigenschaft active Player definniert
        if(player1.active){
            player1.active = false;
            player2.active = true;
        }else{
            player1.active = true;
            player2.active = false;
        }
    }

    private int checkWinner(board MyBoard,player player){  //Methode zur Prüfung ob es einen Gewinner gibt
        int Points = 0;

        //waagerecht
        for(int y = MyBoard.rows; y > 0; y--){             //Läuft von der untersten bis zur obersten Reihe (6-1)
            for(int x = 1; x <= MyBoard.cols; x++){       //für jedes Feld in der Reihe
                if(MyBoard.coordinates[y][x] == player.ID){    //Prüfung ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(MyBoard.coordinates[y][x] != player.ID){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    System.out.println("Vier Waagerechte in einer Reihe");
                    System.out.println("Spieler " + player.symbol + " hat Gewonnen!");
                    return player.ID;                  // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;
        }
        //Senkrecht
        Points = 0;
        for(int x = MyBoard.cols; x > 0; x--){             //Läuft von Spalte ganz rechts bis zum anfang
            for(int y = 1; y <= MyBoard.rows; y++){       //Prüft jedes Feld in der Spalte
                if(MyBoard.coordinates[y][x] == player.ID){    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                    Points++;                       //Für jedes aufeinenderfolgende gleiche Zeiche gibt es einen Punkt
                }
                if(MyBoard.coordinates[y][x] != player.ID){    //Findet die Schleife ein anderes Zeichen als das des Spielers der and er Reihe sit
                    Points = 0;                     // Punktezähler wieder auf 0 srtzen
                }
                //System.out.print(Points);
                if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                    System.out.println("Vier senkrechte in einer Reihe");
                    System.out.println("Spieler " + player.symbol + " hat Gewonnen!");
                    return player.ID;                  // Gibt die Schleife den Gewinner zurück
                }
            }
            Points = 0;                           //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
        //vier diagonale von links unten nach rechts oben
        Points = 0;
        //int x = 1;
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = MyBoard.rows; y >= 3; y--){             //In jeder Spalte Von der untersten Reihe bis zur 4. von unten durchlaufen
                if(MyBoard.coordinates[y][x] == player.ID){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen
                        row--;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (MyBoard.coordinates[row][col] == player.ID) {    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;
                        }else Points = 0;
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            System.out.println("4diagonal links unten ->rechts oben");
                            System.out.println("Spieler " + player.symbol + " hat Gewonnen!");
                            return player.ID;                  // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
            }
            Points = 0; //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }

        //vier diagonale von links oben nach rechts unten
        Points = 0;
        //int x = 1;
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = 1; y <= 3; y++){             //In jeder Spalte Von der untersten Reihe bis zur 4. von unten durchlaufen
                if(MyBoard.coordinates[y][x] == player.ID){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen
                        row++;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (MyBoard.coordinates[row][col] == player.ID) {    //Prüfen ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;                             //Einen punkt für das zeichen gutschreiben
                        }else Points = 0;
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            System.out.println("4 Diagonal links oben ->rechts unten");
                            System.out.println("Spieler " + player.symbol + " hat Gewonnen!");
                            return player.ID;                  // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
            }
            Points = 0; //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
        return 0;
    }

    private boolean checkDraw(board MyBoard){            //Methode zur Prüfung bo es noch freie felder auf dem spielfeld gibt -> True bei unentschieden
        for(int y = 1; y <= MyBoard.rows; y++){    //Alle Zeilen
            for(int x = 1; x <= MyBoard.cols; x++){    // Und alle Felder in der Reihe abarbeiten
                if (MyBoard.coordinates[y][x] == 0){ //Prüfen ob leer
                    return false;                   //Wenn leer, kein unentschieden
                }
            }
        }
        System.out.println("Draw!");    //Wenn kein leeres feld gefunden, unentschieden
        System.out.println("Game OVER!");
        return true;
    }

}

class board {
    int rows = 6;
    int cols = 7;
    int[][] coordinates = new int[this.rows+1][this.cols+1]; //+1 damit wir bei 1 und nicht bei null starten können

    public int[][] initialize(){

        for(int y  = 1; y < this.rows+1; y++ ){
            for(int x  = 1; x < this.cols+1; x++ ){
                coordinates[y][x] = 0;
            }
        }
        return coordinates;
    }

    public void print(player player1, player player2){                  //Spielfled ausgeben -> Player objekte müssen übergeben werden, damit die ID und das Symbol des Spielers bekannt sind
        //System.out.println("  1    2    3    4    5    6    7  ");
        System.out.println(printHeader());
        for(int y  = 1; y < this.rows+1; y++ ){
            for(int x  = 1; x < this.cols+1; x++ ){
                System.out.print("[ ");
                if(coordinates[y][x] == player1.ID ){
                    System.out.print(player1.symbol);
                }else if(coordinates[y][x] == player2.ID) {
                    System.out.print(player2.symbol);
                }else{
                    System.out.print(" ");
                }
                System.out.print(" ]");
            }
            System.out.println();
        }
    }

    public String printHeader(){
        String header = "";
        for(int i = 1; i<=cols;i++ ){
            header = header + "  " + i + "  ";
        }
        return header;
    }

    public int askColumn(){
        userInput myInput = new userInput();
        int answer;

        while(true) {
            answer = myInput.ask("Column: ");
            if (checkColumn(answer)) {
               break;
            }
        }
        System.out.println(answer);
        return answer;
    }
    //Prüfung ob noch Platz in der Spalte ist
    private boolean checkColumn(int col){
        if(coordinates[1][col] == 0){
            return true;
        }else {
            System.out.println("Column " + col + " is full");
            System.out.println("Please choose another one");
        }
        return false;
    }

    private boolean checkField(int row, int col) {
        if (coordinates[row][col] == 0) {
            return true;
        }
        return false;
    }

    public void newMove(int col, player player){
        for(int row = this.rows; row > 0; row--){   //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(checkField(row,col)){                //Ist der Eintrag im Koordianten Array leer
                coordinates[row][col] = player.ID;     //Wird das Zeichen des Spieler eingetragen
                break;                              //Schleife unterbrechen
            }
        }
    }

}

class player {
    int ID;
    char symbol;
    boolean active;
    public player(int playerID, char playerSymbol, boolean isActivePlayer){
        this.ID =  playerID;
        this.symbol = playerSymbol;
        this.active = isActivePlayer;
    }

}

class userInput {
    int[] validAnswers = new int[]{1,2,3,4,5,6,7};

    public int ask(String question ){
        Scanner Input = new Scanner(System.in);
        System.out.print(question);
        while(!Input.hasNextInt()){                               //Falls kein Int eingegeben wird
            System.out.println("Invalid Input! Numbers only!");
            Input.next();
        }
        int answer = Input.nextInt();
        while(!validate(answer)){
            System.out.println("Invalid Input!");
            System.out.println(question);
            answer = Input.nextInt();
        }
        return answer;
    }

    public boolean validate(int answer){
        for(int valid: validAnswers)                                //Prüfen ob es sich um eine gültige Antwort handelt
        {
            if(answer == valid) {
                return true;                                      //Wenn gültig, antwort übergeben
            }
        }
        return false;
    }
}

