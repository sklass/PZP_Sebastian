import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FourWins MyGame = new FourWins();
    }


}
class FourWins{
    public FourWins(){
        board FourWinsBoard = new board();
        player player1 = new player(1);
        player player2 = new player(-1);
        int activePlayerID = player1.ID;

        FourWinsBoard.initialize();
        while(true) {
            FourWinsBoard.print(player1.ID, player2.ID);
            System.out.println("Active Player: "+activePlayerID);
            FourWinsBoard.newMove(FourWinsBoard.askColumn(), activePlayerID);
            activePlayerID = togglePlayer(activePlayerID, player1.ID, player2.ID);
        }
    }

    private int togglePlayer(int activePlayer, int player1, int player2){
        int newactivePlayer;
        if(activePlayer == player1){
            newactivePlayer = player2;
        }else newactivePlayer = player1;
        return newactivePlayer;
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

    public void print(int player1ID, int player2ID){
        System.out.println("  1    2    3    4    5    6    7  ");
        for(int y  = 1; y < this.rows+1; y++ ){
            for(int x  = 1; x < this.cols+1; x++ ){
                System.out.print("[ ");
                if(coordinates[y][x] == player1ID ){
                    System.out.print("X");
                }else if(coordinates[y][x] == player2ID) {
                    System.out.print("O");
                }else{
                    System.out.print(" ");
                }
                System.out.print(" ]");
            }
            System.out.println();
        }
    }
    public int askColumn(){
        userInput myInput = new userInput();
        int answer;

        while(true) {
            answer = myInput.ask("Column: ");
            if (checkColumn(answer)) {
               break;
            }else{
                System.out.println("Column "+answer+ " is full");
                System.out.println("Please choose another one");
            }
        }
        System.out.println(answer);
        return answer;
    }
    //Prüfung ob noch Platz in der Spalte ist
    private boolean checkColumn(int col){
        if(coordinates[1][col] == 0){
            return true;
        }
        return false;
    }

    private boolean checkField(int row, int col) {
        if (coordinates[row][col] == 0) {
            return true;
        }
        return false;
    }

    public void newMove(int col, int player){
        for(int row = this.rows; row > 0; row--){   //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(checkField(row,col)){                //Ist der Eintrag im Koordianten Array leer
                coordinates[row][col] = player;     //Wird das Zeichen des Spieler eingetragen
                break;                              //Schleife unterbrechen
            }
        }
    }

}

class player {
    public int ID;
    public player(int playerID){
        this.ID =  playerID;
    }

}

class userInput {
    int[] validAnswers = new int[]{1,2,3,4,5,6,7};

    public int ask(String question ){
        Scanner Input = new Scanner(System.in);
        System.out.println(question);
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

