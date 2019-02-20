import java.util.Scanner;

public class UserInterface { //Klasse für die Ein und Ausgaben
    void print(String Text){
        System.out.println(Text);
    }
/*
    void printHelp(){
        System.out.println();
        System.out.println("To win this game you need to get 4 of your marks in a row. The row can be vertical, horizontal or diagonal");
        System.out.println("When its your turn, you are able to place your mark by typing in the number of the column where you want to place it");
    }
*/
    void PrintBoard(int cols, int rows, String Header, String[][] BoardContent){
        System.out.println();
        System.out.println(Header);
        for(int y  = 1; y < rows+1; y++ ) {
            for (int x = 1; x < cols + 1; x++) {
                System.out.print(BoardContent[y][x]);
            }
            System.out.println();
        }

    }

    void printWinner(char WinningPlayerSymbol, String WinCondition){
        System.out.println("Congrats, Player "+ WinningPlayerSymbol +" has won the game");
        System.out.println(WinCondition);
    }

    void printPlayerPoints(Player player1, Player player2){
        System.out.println("Player " + player1.getPlayerSymbol() + " has won " + player1.getPoints() + " game(s)");
        System.out.println("Player " + player2.getPlayerSymbol() + " has won " + player2.getPoints() + " game(s)");
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

    void printActivePlayer(char activePlayerSymbol){
        System.out.println("Its your turn Player " + activePlayerSymbol);
    }

    void printColumnFull(int col){
        System.out.println("Column " + col + " is full");
        System.out.println("Please choose another one");
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
            while(!Input.hasNextInt()){                               //Falls kein Int eingegeben wird
                System.out.println("Invalid Input! Numbers only!");
                Input.next();
            }
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
