import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TFIDFCalculator {
    public static void main(String[] args) {
        String filePath = args[0];
        String tc = args[1];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int linecount = 0;
            Trie bigTrie = new Trie();
            Trie trie = null;
            ArrayList<Trie> Trie_List = new ArrayList<Trie>();
            while ((line = reader.readLine()) != null) {
                if(linecount%5==0) {
                    trie = new Trie();
                }
                String alphabetic = line.replaceAll("[^a-zA-Z]+", " ").toLowerCase();
                String[] words = alphabetic.split("\\s+");
                for (String word : words) {
                    if(!word.isEmpty()){
                        if(trie.search(word)==0){
                            bigTrie.insert(word);
                        } 
                    trie.insert(word);
                    }
        
                }
                if(linecount%5==4){
                    Trie_List.add(trie);
                }
                linecount++;
            }
            reader.close();
            BufferedWriter writer_output = new BufferedWriter(new FileWriter("output.txt"));
            writer_output.write("");
            writer_output.close();
            List<String> tcLines = Files.readAllLines(Paths.get(tc));
            String[] words = tcLines.get(0).split("\\s+");
            String[] indices = tcLines.get(1).split("\\s+");
            for(int test = 0;test<words.length;test++){
                Calculator.tfIdfCalculate(Trie_List, bigTrie, words[test], Integer.parseInt(indices[test]));
            }
            
        } catch(IOException e){
            System.err.println("Error");
        }
    }
}

class Calculator {
    public static double tf(int index, String term, ArrayList<Trie> trie_list) {
        Trie check = trie_list.get(index);
        return (double)check.search(term)/(double)check.total;
    }

    public static double idf(String term, Trie bigtrie, ArrayList<Trie> trie_List) {
        return Math.log((double)trie_List.size()/(double)bigtrie.search(term));
    }
    
    public static void tfIdfCalculate(ArrayList<Trie> trie_List, Trie bigTrie, String term, int index){
          double tfIdf = tf(index, term, trie_List) * idf(term, bigTrie, trie_List);
          try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt", true));
                writer.write(String.format("%.5f", tfIdf) + " ");
                writer.close();
            } catch(IOException e) {
                System.err.println("error");
            }
      }
    
}

class TrieNode {
    TrieNode[] children = new TrieNode[26];
    int count;
}

class Trie {
    int total;
    TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        node.count++;
        this.total++;
    }

    public int search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return 0;
            }
        }
        return node.count;
    }
}