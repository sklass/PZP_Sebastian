package Game.Model;

public class Player { //Klasse für die Spieler
    private int ID;
    private char symbol;
    private int Points;

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
    public int getPoints() {
        return Points;
    }
    public void increasePoints() {
        this.Points++;
    }


}
