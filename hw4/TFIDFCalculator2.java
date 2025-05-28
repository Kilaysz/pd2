import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TFIDFCalculator2 {
    public static void main(String[] args) {
        String filePath = args[0];
        String tc = args[1];
        try {
            // Read and process the document file
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<List<String>> docList = new ArrayList<>();
            List<Hashtable<String, Integer>> wordCountsList = new ArrayList<>();
            List<String> currentDoc = null;
            Hashtable<String, Integer> currentWordCounts = null;

            for (int i = 0; i < lines.size(); i++) {
                if (i % 5 == 0) {
                    currentDoc = new ArrayList<>();
                    currentWordCounts = new Hashtable<>();
                    docList.add(currentDoc);
                    wordCountsList.add(currentWordCounts);
                }
                String line = lines.get(i).replaceAll("[^a-zA-Z]+", " ").toLowerCase();
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        currentDoc.add(word);
                        currentWordCounts.put(word, currentWordCounts.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Read the test case file
            List<String> tcLines = Files.readAllLines(Paths.get(tc));
            String[] words = tcLines.get(0).split("\\s+");
            String[] indices = tcLines.get(1).split("\\s+");

            // Calculate TF-IDF and collect results
            StringBuilder outputBuilder = new StringBuilder();
            for (int k = 0; k < words.length; k++) {
                int index = Integer.parseInt(indices[k]);
                String term = words[k];
                double tfIdf = Calculator.tfIdfCalculate(docList.get(index), term, wordCountsList);
                outputBuilder.append(String.format("%.5f ", tfIdf));
            }

            // Write the results to the output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
                writer.write(outputBuilder.toString().trim());
            }
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}

class Calculator {
    public static double tf(List<String> doc, String term) {
        long count = doc.stream().filter(item -> item.equals(term)).count();
        return count / (double) doc.size();
    }
    
    public static double idf(String term, List<Hashtable<String, Integer>> wordCountsList) {
        double count = 0.0;
        for (Hashtable<String, Integer> wordCounts : wordCountsList) {
            if (wordCounts.containsKey(term)) {
                count++;
            }
        }
        return Math.log((double) wordCountsList.size() / count);
    }

    public static double tfIdfCalculate(List<String> doc, String term, List<Hashtable<String, Integer>> wordCountsList) {
        double tf = tf(doc, term);
        double idf = idf(term, wordCountsList);
        return tf * idf;
    }
}
