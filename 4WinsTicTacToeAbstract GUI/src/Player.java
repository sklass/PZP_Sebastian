public class Player { //Klasse für die Spieler
    private int ID;
    private char symbol;
    private int Points;

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
    int getPoints() {
        return Points;
    }
    void increasePoints() {
        this.Points++;
    }


}
