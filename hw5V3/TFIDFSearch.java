import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


class Calculator {
    public static double tf(String term, Indexer trie, int result) {
         return (double)trie.search_smalltrie(term, Integer.toString(result))/(double)trie.word_per_txt.get(result);
    }

    public static double idf(String term, Indexer bigtrie) {
        if(bigtrie.search_total(term)==0){
            return 0;
        }
        return Math.log((double)bigtrie.word_per_txt.size()/ (double)bigtrie.search_total(term));
    }

    public static double tfIdfCalculate(String term, Indexer bigTrie, int result){
        return tf(term, bigTrie, result) * idf(term, bigTrie);
    }
}

class Printer {
    public static void print(Indexer BigTrie, Set<Integer> resultSet, Set<String> query, int n) throws IOException {
        if(resultSet.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            for (int i = 0; i < n; i++) {
                writer.write("-1 ");
            }
            writer.newLine();
            return;
        }

        }
        // Create a map to store the tf-idf values for each result
        HashMap<Integer, Double> map = new HashMap<>();
        for (Integer result : resultSet) {
            double tfIDF = 0.0d;
            for (String queryString : query) {
                tfIDF += Calculator.tfIdfCalculate(queryString, BigTrie, result);
            }
            map.put(result, tfIDF);
        }

        // Sort the results by tf-idf values in descending order
        List<Map.Entry<Integer, Double>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort((entry1, entry2) -> {
            // Compare tf-idf values
            int tfidfComparison = entry2.getValue().compareTo(entry1.getValue());
            if (tfidfComparison != 0) {
                // If tf-idf values are not equal, return comparison result
                return tfidfComparison;
            } else {
                // If tf-idf values are equal, compare IDs in ascending order
                return entry1.getKey().compareTo(entry2.getKey());
            }
        });
        // Write the sorted results to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true))) {
            int count = 0;
            for (Map.Entry<Integer, Double> entry : sortedEntries) {
                writer.write(entry.getKey() + " ");
                count++;
                if (count >= n) {
                    break;
                }
            }

            for (int i = count; i < n; i++) {
                writer.write("-1 ");
            }
            writer.newLine();
        }
    }
}

public class TFIDFSearch {
    public static void main(String[] args) {
        String corpus = args[0];
        String testcase = args[1];
        Indexer bigTrie;
        HashMap <Integer, String> check = new HashMap<>();
        List<ArrayList<String>> query_List = new ArrayList<>();
        int linecount = 0;
        String line;
        int n=0;
        try {
            FileInputStream fis = new FileInputStream(corpus+".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            bigTrie = (Indexer) ois.readObject();
            ois.close();
            fis.close();

            BufferedReader reader = new BufferedReader(new FileReader(testcase));
            while((line = reader.readLine())!= null){
                String[] temp;
                query_List.add(new ArrayList<>());
                if(linecount==0){
                    n = Integer.parseInt(line);
                } else if(line.contains("AND")){
                    temp = line.split(" AND ");
                    query_List.get(linecount).addAll(Arrays.asList(temp));
                    check.put(linecount, "and");
                } else if(line.contains("OR")){
                    temp = line.split(" OR ");
                    query_List.get(linecount).addAll(Arrays.asList(temp));
                    check.put(linecount, "or");
                } else {
                    query_List.get(linecount).add(line);
                    check.put(linecount, "single");
                }
                    linecount++;
            }
            reader.close();
            
            for (int i = 1; i < query_List.size(); i++) {
                Set<String> queryword_list = new HashSet<>(query_List.get(i));
                String type = check.get(i);
                Set<Integer> resultSet = new HashSet<>();

                if ("and".equals(type)) {
                    boolean first = true;
                    for (String term : queryword_list) {
                        if(bigTrie.search_total(term)==0){
                            resultSet.clear();
                            break;
                        }
                        Set<Integer> termResults = new HashSet<>(bigTrie.getID(term));
                        if (first) {
                            first = false;
                            resultSet.addAll(termResults);
                        } else {
                            resultSet.retainAll(termResults);
                        }
                    }
                } else if ("or".equals(type)) {
                    for (String term : queryword_list) {
                        if(bigTrie.search_total(term)==0){
                            continue;
                        }
                        Set<Integer> termResults = new HashSet<>(bigTrie.getID(term));
                        resultSet.addAll(termResults);
                    }
                } else if ("single".equals(type)) {
                    Iterator<String> temp = queryword_list.iterator();
                    String term = temp.next();
                    if(bigTrie.search_total(term)!=0){
                        resultSet.addAll(bigTrie.getID(term));
                    } 
                }
                Printer.print(bigTrie, resultSet, queryword_list, n);
            }
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


   }
}