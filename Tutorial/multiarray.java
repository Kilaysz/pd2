import java.util.Arrays;

public class multiarray {
    public static void main(String[] args) {
        int[][] numbers = new int[2][5];
        int [][] number = {{1,2,3},{1,2,3}}; 
        numbers[1][2] = number[0][0];
        System.out.println(Arrays.deepToString(numbers));

    }
}
