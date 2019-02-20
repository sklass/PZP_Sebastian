package Game.View;

import Game.Model.Player;

import java.util.Scanner;

public class UserInterface { //Klasse für die Ein und Ausgaben
    public void print(String Text){
        System.out.println(Text);
    }

    public void PrintBoard(int cols, int rows, String Header, String[][] BoardContent){
        System.out.println();
        System.out.println(Header);
        for(int y  = 1; y < rows+1; y++ ) {
            for (int x = 1; x < cols + 1; x++) {
                System.out.print(BoardContent[y][x]);
            }
            System.out.println();
        }

    }

    public void printWinner(char WinningPlayerSymbol, String WinCondition){
        System.out.println("Congrats, Game.Model.Player "+ WinningPlayerSymbol +" has won the game");
        System.out.println(WinCondition);
    }

    public void printPlayerPoints(Player player1, Player player2){
        System.out.println("Game.Model.Player " + player1.getPlayerSymbol() + " has won " + player1.getPoints() + " game(s)");
        System.out.println("Game.Model.Player " + player2.getPlayerSymbol() + " has won " + player2.getPoints() + " game(s)");
    }

    public void printDraw(){
        System.out.println("Draw! The Game is over because all fields on the board are full");
    }

    public  int askPlayAgain(){
        int answer;
        int[] validAnswers = new int[]{0,1};
        answer = ask("Play again? 1 = Yes / 0 = No",validAnswers);
        //System.out.println(answer);
        return answer;
    }

    public void printActivePlayer(char activePlayerSymbol){
        System.out.println("Its your turn Game.Model.Player " + activePlayerSymbol);
    }

    public void printColumnFull(int col){
        System.out.println("Column " + col + " is full");
        System.out.println("Please choose another one");
    }

    public int ask(String question, int[] validAnswers ){
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
