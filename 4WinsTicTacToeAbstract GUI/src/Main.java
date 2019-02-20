public class Main { //Wird bei Programmstart aufgerufen
    static private int GameStatus;

    public static void main(String[] args) {
        GameStatus = 0;
        GameStateHandler();
    }

    private static void GameStateHandler(){
        while(GameStatus < 3){
            switch(GameStatus){
                case 0: //Abfrage welches Spiel oder Beenden
                    UserInterface Console = new UserInterface();
                    int[] validAnswers = new int[]{1,2,3};
                    GameStatus = Console.ask("\nMain Menu:\nPlay TicTacToe = 1\nPlay FourWins  = 2\nQuit           = 3\n",validAnswers);
                    break;
                case 1: //TicTacToe spielen
                    TicTacToe TicTacToe = new TicTacToe();
                    TicTacToe.start();
                    GameStatus = 0;
                    break;
                case 2: //FourWins Spielen
                    FourWins FourWins = new FourWins();
                    FourWins.start();
                    GameStatus = 0;
                    break;
            }
        }
    }
}
