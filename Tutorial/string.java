import com.erik.*;
public class string {
    public static void main(String[] args) {
        data_type.main(args);
        System.out.println("OK gas");
        String Hello = "......";
        System.out.println(Hello.equals("......"));
        String Hello2 = new String();
        System.out.println(Hello);
        System.out.println(Hello.endsWith("2"));
        System.out.println(Hello.startsWith("Hello"));
        System.out.println(Hello.length());
        System.out.println(Hello.indexOf("H"));
        System.out.println(Hello.indexOf("!"));
        System.out.println(Hello.replace("H", "O"));
        System.out.println(Hello.toUpperCase());
        System.out.println(Hello.toLowerCase());
        System.out.println(Hello.trim());
        System.out.println(Hello.contains("world"));
        System.out.println(Hello2);
    }
}
