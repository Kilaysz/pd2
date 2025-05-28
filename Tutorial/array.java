import java.util.Arrays;

public class array {
    public static void main(String[] args) {
        int[] number = {1,4,3,0};
        int[] number2 = new int[10];
        // number[5] = 10;
        System.out.println(number);
        // Arrays.sort(number);
        System.out.println(Arrays.asList(number).indexOf(number2));
        int target = 4;
        System.out.println(Arrays.asList(number).indexOf(target));
        System.out.println(Arrays.toString(number));
        System.out.println(number2[5]);
        
    }
}
