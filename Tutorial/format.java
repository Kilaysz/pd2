import java.text.NumberFormat;

public class format {
    public static void main(String[] args) {
        NumberFormat anji = NumberFormat.getCurrencyInstance();
        String result = anji.format(1232213.1213);
        System.out.println(result);

        String a = NumberFormat.getPercentInstance().format(12);
        System.out.println(a);
    }
}
