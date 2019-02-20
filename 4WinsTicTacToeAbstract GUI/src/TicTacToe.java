public class TicTacToe extends BoardGame{

     void GameStateHandler(){ //Zustandsautomat zuständig für die Regelung des Spielablaufs in Schritten

        while(GameStatus <=10){
            switch(GameStatus){
                case 0: // Vor dem Spiel die Regeln und bedienung erklären
                    this.Console.print("Welcome to TicTacToe v1.1");
                    this.Console.print("Get 3 of your symbols in a row");
                    this.changeGameState(1); //spiel initialisieren
                    break;
                case 1: //Spiel wird gestartet
                    this.initGame(3,3,'X','O');
                    this.changeGameState(2);
                    break;
                case 2: //Spieler wechseln
                    this.toggleActivePlayer();
                    this.changeGameState(3);
                    break;
                case 3: //Spielfeld anzeigen
                    this.showBoard();
                    this.changeGameState(4);
                    break;
                case 4: //Aktiven Spieler anzeigen
                    this.showActivePlayer();
                    this.changeGameState(5);
                    break;
                case 5: //Spielzug durchführen
                    this.makeAMove();
                    this.changeGameState(6);
                    break;
                case 6:     //Gewinner  prüfen
                    this.checkRow(3); //Prüfen ob jemand 3 in einer Reihe hat
                    this.checkCol(3); //Prüfen ob jemand 3 in einer Spalte hat
                    this.checkDiagonale();
                    this.checkWinner();
                    break;
                case 7:     //Unentschieden prüfen
                    this.checkDraw(Board.getRows(), Board.getCols(), Board.getCoordinates()); //Prüfen ob Unentschieden
                    //this.changeGameState(8);
                    break;
                case 8: //Ein Spieler hat gewonnen
                    this.showWinner(); //zeige den Gewinner an und hebe die spielsteine hervor die zum sieg geführt haben
                    this.changeGameState(10); //nochmal spielen?
                    break;
                case 9: // Unentschieden
                    this.showDraw();
                    this.changeGameState(10);
                    break;
                case 10: //Nochmal spielen
                    this.Playagain();
                    break;
                default: //Beenden
                    //System.out.println("Bye");
                    changeGameState(11);
                    break;
            }
        }
    }

    //Methode um neuen Spielstein aufs Feld zu setzen
    void makeAMove(){
        int[][] coordinates = this.Board.getCoordinates();  //Array mit bisherigen Spielsteinen holen
        int[] validAnswers = new int[]{1,2,3};

        while(true){                                                         //Schleife solange durchlaufen bis gültiger wert eingegeben wird
            int col = this.Console.ask("Column: ", validAnswers);   //Der User wird nach einer Spalte gefragt
            int row = this.Console.ask("Row: ", validAnswers);      //Der User wird nach einer Reihe gefragt

            if(checkField(row,col,coordinates)){                //Ist der Eintrag im Koordianten Array leer
                coordinates[row][col] = activePlayer.getPlayerID();     //Wird das Zeichen des Spieler eingetragen
                break;
            }else{
                this.Console.print("Field is not empty!");
            }
        }
        this.Board.setCoordinates(coordinates); //Das aktuelle Koordinaten array wird ans Board zurückgegeben
    }

    void checkDiagonale(){
        int rows = this.Board.getRows();
        int cols = this.Board.getCols();
        int coordinates[][] = this.Board.getCoordinates();
        //drei diagonale von links unten nach rechts oben
        int Points1 = 0;
        int Points2 = 0;
        int[][] WinCoordinates1 = new int[2][4];       //Speichert die position des Spielsteins in einem Array
        int[][] WinCoordinates2 = new int[2][4];

        for(int i = 1; i <=3; i++) {
            if(coordinates[i][i] == this.activePlayer.getPlayerID()) { //1-1 2-2 3-3 -> Von oben links nach unten rechts
                WinCoordinates1[0][Points1] = i;
                WinCoordinates1[1][Points1] = i;
                Points1++;
            }else Points1 = 0;

            if(Points1 == 3 ){                   //Wurden 3 gleiche Zeichen in folge gefunden
                this.WinCondition = "Three diagonal -> upper left to lower right";
                setWinner(this.activePlayer,this.WinCondition,WinCoordinates1);           //setzen wir einen Sieger
                break;
            }

            int y = 4-i;
            int x = i;
            if(coordinates[y][x] == activePlayer.getPlayerID()) { //3-1 2-2 1-3 -> Von oben links nach unten rechts
                WinCoordinates2[0][Points2] = y;
                WinCoordinates2[1][Points2] = x;
                Points2++;
            }else Points2 = 0;

            if(Points2 == 3 ){                   //Wurden 3 gleiche Zeichen in folge gefunden
                WinCondition = "Three diagonal -> lower left to upper right";
                setWinner(this.activePlayer,this.WinCondition,WinCoordinates2);           //setzen wir einen Sieger
                break;
            }
         }

    }





}
