import java.util.Scanner;

            public class Main {

                public static void main(String[] args) {
                    Scanner Eingabe = new Scanner(System.in);
                    String filler = " ";
                    System.out.println("Bitte länge des Rechtecks eingeben\n");
                    int laenge = Eingabe.nextInt();

                    System.out.println("Bitte breite des Rechtecks eingeben\n");
                    int breite = Eingabe.nextInt();

                    System.out.println("Rechteck ausgefüllt = 1 - Rechteck nicht ausgeüllt = 0\n");
                    int fill = Eingabe.nextInt();

                    System.out.println("Erzeuge Rechteck mit Breite" + breite + " und Länge " + laenge);

                    if (fill == 1){
                        //Zeichen zum füllen des Rechtecks abfragen
                        System.out.println("Welches Zeichen soll zur Füllung genutzt werden\n");
                        filler = Eingabe.next();

                    }else if(fill == 0) {
                        System.out.println("Das Rechteck wird nicht gefüllt\n");
                        filler = " ";
                    }
                        //erste Zeile ausgeben (basierend auf breite)
                        for (int b = 0; b < breite; b++){
                            System.out.print("#");
                            if(b == breite-1){
                                System.out.print("\n");
                            }
                        }
                        //Alle zeilen zwischen erster und letzter
                        for(int l = 0; l < laenge-2; l++){
                            System.out.print("#");
                            for (int bb = 0; bb < breite-2; bb++){
                                System.out.print(filler);
                            }
                            System.out.print("#\n");
                        }
                        //letzte Zeile ausgeben
                        for (int b = 0; b < breite; b++){
                            System.out.print("#");
                        }
    }
}
