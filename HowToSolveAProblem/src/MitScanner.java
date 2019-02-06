import java.util.Scanner;

public class MitScanner {
    public static void main(String[] args) {
        boolean solve_problem = true;
        while (solve_problem) {
            Scanner dieEingabe = new Scanner(System.in);
            int antwort;

            System.out.println("Beantworte folgende Fragen mit 1 für JA oder 0 für NEIN!\n");

            System.out.print("Funktioniert alles wie es soll? ");
            antwort = dieEingabe.nextInt();

            if (antwort == 1) {
                System.out.println("Lass es so!");
                System.out.println("Es gibt kein Problem!");
            }
            else if(antwort == 0){
                System.out.print("Hast du dran rumgefummelt? ");
                antwort = dieEingabe.nextInt();
                if (antwort == 1) {
                    System.out.println("Du Blödmann!");

                    System.out.print("Weiß jemand davon? ");
                    antwort = dieEingabe.nextInt();

                    if (antwort == 1) {
                        System.out.println("Du armes Schwein!");
                    }
                    else if(antwort == 0){
                        System.out.println("Behalt's für dich!");
                        System.out.println("Es gibt kein Problem!");
                    }
                }
                else if(antwort == 0){
                    System.out.print("Bist du dafür verantwortlich? ");
                    antwort = dieEingabe.nextInt();

                    if(antwort == 1) {
                        System.out.print("Kannst du es jemand anderem anhängen? ");
                        antwort = dieEingabe.nextInt();

                        if (antwort == 1) {
                            System.out.println("Es gibt kein Problem");
                        }
                        else if(antwort == 0){
                            System.out.println("Du armes Schwein!");
                        }
                    }
                    else if(antwort == 0){
                        System.out.println("Mehr Glück als Verstand!");
                        System.out.println("Es gibt kein Problem!");
                    }
                }
            }
            System.out.print("Noch einmal versuchen? ");
            antwort = dieEingabe.nextInt();
            if(antwort != 1) {
                solve_problem = false;
            }
        }
     }
}
