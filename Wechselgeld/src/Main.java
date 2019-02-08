import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner Eingabe = new Scanner(System.in);
        System.out.println("Nenne einen Geldbetrag und ich gebe dir das Wechselgeld in so wenig Scheinen/Münzen wie möglich");
        while (!Eingabe.hasNextFloat()) Eingabe.next();
        float Betrag = Eingabe.nextFloat();
        System.out.println("Betrag: " + Betrag + " €");

        //Runden
        Betrag = round(Betrag, 2);
        System.out.println("Betrag: " + Betrag + " €");


    }

    static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
