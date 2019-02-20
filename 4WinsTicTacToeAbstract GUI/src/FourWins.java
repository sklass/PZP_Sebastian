public class FourWins extends BoardGame{ //Klasse FourWins wird von der abstrakten klasse BoardGame abgeleitet


    void GameStateHandler(){ //Zustandsautomat zuständig für die Regelung des Spielablaufs in Schritten

        while(GameStatus <=10){
            switch(GameStatus){
                case 0: // Vor dem Spiel die Regeln und bedienung erklären
                    this.Console.print("Welcome to FourWins v1.1");
                    this.Console.print("Get 4 of your symbols in a row");
                    this.changeGameState(1); //spiel initialisieren
                    break;
                case 1: //Spiel wird gestartet
                    this.initGame(6,7,'X','O');
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
                    this.checkRow(4); //Prüfen ob jemand 4 in einer Reihe hat
                    this.checkCol(4); //Prüfen ob jemand 4 in einer Spalte hat
                    this.checkDiagonale();                //Prüfen ob jemand 4 Diagonal hat
                    this.checkWinner();                   //Prüfen ob eine der obigen bedingungen zugetroffen hat
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
        int col = this.askColumn();                                     //Der User wird nach einer Spalte gefragt
        this.Board.setCoordinates(newMove(col,this.activePlayer)); //Eingegebene Spalte und aktiver Spieler werden an newMove übergeben
        //NewMove prüft die spalte ob platz ist, und trägt das Spielersymbol ein
        //SetCoordinates speichert die neuen Daten im Board objekt
    }

    //Abfrage einer Spalte mithilfe des UserInterface
    private int askColumn(){
        int answer;
        int[] validAnswers = new int[]{1,2,3,4,5,6,7};

        while(true) {                                                   //Abfrage wird solange wiederholt bis eine gültige Spalte eingegeben wird
            answer = this.Console.ask("Column: ",validAnswers);
            if (this.checkColumn(answer)) {
                break;
            }
        }
        return answer;
    }

    private boolean checkColumn(int col){
        int[][]coordinates = this.Board.getCoordinates();
        if(coordinates[1][col] == 0){
            return true;
        }else {
            this.Console.printColumnFull(col);
        }
        return false;
    }

    private int[][] newMove(int col, Player player){   //Methode um einen Spielstein auf das Spielfeld zu setzen. Die größe des Spielfelds, sowie die aktuell gesetzten Steine und der Spieler der den Spielzug durchführt werden übergeben
        int rows = this.Board.getRows();                           //Anzahl Zeilen ermitteln
        int[][] coordinates = this.Board.getCoordinates();  //Array mit bisherigen Spielsteinen holen
        for(int row = rows; row > 0; row--){   //Schleife zählt von max.höhe des Spielfelds runter bis auf 1. Falls schon ein Spielstein in der Spalte ist, wird der nächste darüber platziert
            if(checkField(row,col,coordinates)){                //Ist der Eintrag im Koordianten Array leer
                coordinates[row][col] = player.getPlayerID();     //Wird das Zeichen des Spieler eingetragen
                break;                             //Schleife unterbrechen
            }
        }
        return coordinates;
    }

    void checkDiagonale(){
        int rows = this.Board.getRows();
        //int cols = this.Board.getCols();
        int[][] coordinates = this.Board.getCoordinates();
        int Points;
        //vier diagonale von links unten nach rechts oben
        Points = 0;
        int[][] WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = rows; y >= 3; y--){             //In jeder Spalte Von der untersten Reihe bis zur 4. von unten durchlaufen
                if(coordinates[y][x] == activePlayer.getPlayerID()){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    WinCoordinates[0][Points-1] = y;
                    WinCoordinates[1][Points-1] = x;
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen bis drei spalten weiter rechts erreicht wurden
                        row--;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (coordinates[row][col] == activePlayer.getPlayerID()) {    //ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;
                            WinCoordinates[0][Points-1] = row;
                            WinCoordinates[1][Points-1] = col;
                        }else{ //Points = 0;
                            break;
                        }
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            WinCondition = "Four diagonal -> lower left to upper right";
                            setWinner(activePlayer,WinCondition,WinCoordinates);// Gibt die Schleife den Gewinner zurück
                            return;
                        }
                    }
                }
                Points = 0; //Nach jeder Zeile wird der Punktezähler zurückgesetzt
            }
            Points = 0; //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }

        //vier diagonale von links oben nach rechts unten
        Points = 0;
        //int x = 1;
        WinCoordinates = new int[2][4];       //Speichert die position des Spielsteins in einem Array
        for(int x = 1; x  <= 4; x++ ){              //Von Spalte eins bis 4 durchlaufen
            for(int y = 1; y <= 3; y++){             //In jeder Spalte Von der obersten Reihe bis zur 3. von oben durchlaufen

                if(coordinates[y][x] == activePlayer.getPlayerID()){    //Prüfen ob im feld das zeichen des Spielers ist
                    Points++;                       //Einen punkt für das erste zeichen gutschreiben
                    WinCoordinates[0][Points-1] = y;
                    WinCoordinates[1][Points-1] = x;
                    int row = y;                            //aktuelle Reihennummer in row speichern
                    for (int col = x+1; col <= x+3; col++) { //aktuelle spaltennummer in x speichern und per for immer um eins erhöhen
                        row++;                              //reihennummer mit jedem durchlauf um eins verringern
                        if (coordinates[row][col] == activePlayer.getPlayerID()) {    //Prüfen ob sich darin das Zeichen des Spielers befindet der gerade dran ist
                            Points++;                             //Einen punkt für das zeichen gutschreiben
                            WinCoordinates[0][Points-1] = row;
                            WinCoordinates[1][Points-1] = col;
                        }else {//Points = 0;
                            break;
                        }
                        if(Points == 4 ){                   //Wurden vier gleiche Zeichen in folge gefunden
                            WinCondition = "4 diagonal -> from upper left to lower right";
                            setWinner(activePlayer,WinCondition,WinCoordinates);                   // Gibt die Schleife den Gewinner zurück
                        }
                    }
                }
                Points = 0; //Nach jeder Zeile wird der Punktezähler zurückgesetzt
            }
            Points = 0; //Nach jeder Spalte wird der Punktezähler zurückgesetzt
        }
    }

}
