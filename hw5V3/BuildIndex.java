import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BuildIndex {
    public static void main(String[] args) {
        String line;
        int linecount = 0;
        Indexer bigTrie = new Indexer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if(linecount%5==0){
                    i = 0;
                }
                String[] words = line.replaceAll("[^a-zA-Z]+", " ").toLowerCase().trim().split("\\s+");
                for (String word : words) {
                    bigTrie.insert(word, Integer.toString(linecount/5));
                    i++;
                }
                if(linecount%5==4){
                    bigTrie.addwordpertxt(i);
                }
                linecount++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();	
        }
            File file = new File(args[0]);
        try (FileOutputStream fos = new FileOutputStream("corpus" + file.getName().substring(6,7) + ".ser");
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
             oos.writeObject(bigTrie);
            System.out.println("Serialization completed.");
        } catch (IOException e) {
            e.printStackTrace();	
        }
    }
}
