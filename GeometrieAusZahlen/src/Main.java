import java.sql.SQLOutput;
import java.util.Scanner;
public class Main {
    //Deklaration
    static Scanner Eingabe = new Scanner(System.in);
    static int form;

    public static void main(String[] args) {

        System.out.println("Rechteck = 1 oder Dreieck = 2?");
        form = Eingabe.nextInt();

        if(form == 1){
            zeichneRechteck();
        }else if(form == 2){
            zeichneDreieck();
        }
    }
    static void zeichneRechteck(){

    }
    static void zeichneDreieck(){

    }
}
