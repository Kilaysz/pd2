import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class TrieNode implements Serializable {
    TrieNode[] children = new TrieNode[26];
    ArrayList <Integer> ID = new ArrayList<>();
    HashMap <String, Integer> Maptocount = new HashMap<>();
    int count;
}


public class Indexer implements Serializable {
    // word_per_txt -> To note the total word per text
    ArrayList<Integer> word_per_txt = new ArrayList<>();
    TrieNode root = new TrieNode();
    

    // Insert a word to a trie
    public void insert(String word, String ID) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        // when the node is ready to be inserted
        if(node.Maptocount.get(ID)==null){
            node.count++;
            node.ID.add(Integer.parseInt(ID));
        }
        node.Maptocount.compute(ID, (key, value) -> (value == null)?1:value+1);
    }

    public int search_total(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return 0;
            }
        }
        return node.count;
    }

    public int search_smalltrie(String word, String ID){
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return 0;
            }
        }
        if(node.Maptocount.containsKey(ID)){
            return node.Maptocount.get(ID);
        } else {
            return 0;
        }
    }

    public ArrayList<Integer> getID(String word){
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children[c - 'a'];
            if (node == null) {
                return new ArrayList<>();
            }
        }
        return node.ID;
    }

    public void addwordpertxt(int term_count){
        word_per_txt.add(term_count);
    }
}