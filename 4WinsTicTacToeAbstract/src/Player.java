public class Player {
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
