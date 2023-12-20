package cow.mocjang.core.search.trie;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                System.out.println("new char inserted : " + ch);
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.endOfString = true;
        System.out.println("successfully inserted " + word + " in trie");
    }

    public boolean search(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = currentNode.children.get(ch);
            if (node == null) {
                System.out.println("there is no data : " + word);
                return false;
            }
            currentNode = node;
        }
        if (currentNode.endOfString) {
            System.out.println("find word : " + word);
            return true;
        } else {
            System.out.println("there is no data : " + word);
        }
        return currentNode.endOfString;
    }

    private boolean delete(TrieNode parentNode, String word, int index) {
        char ch = word.charAt(index);
        TrieNode currentNode = parentNode.children.get(ch);
        boolean canThisNodeBeDeleted;

        if (currentNode.children.size() > 1) {
            delete(currentNode, word, index + 1);
            return false;
        }

        if (index == word.length() - 1) {
            if (currentNode.children.size() >= 1) {
                currentNode.endOfString = false;
                return false;
            } else {
                parentNode.children.remove(ch);
                return true;
            }
        }

        if (currentNode.endOfString) {
            delete(currentNode, word, index + 1);
            return false;
        }

        canThisNodeBeDeleted = delete(currentNode, word, index + 1);

        if (canThisNodeBeDeleted) {
            parentNode.children.remove(ch);
            return true;
        } else {
            return false;
        }
    }

    public void delete(String world) {
        if (search(world)) {
            delete(root, world, 0);
        }
    }

    public List<String> findAllWithPrefix(String searchWords) {
        TrieNode currentNode = root;
        if(searchWords.isBlank()){
            return new ArrayList<>();
        }
        for (int i = 0; i < searchWords.length(); i++) {
            char ch = searchWords.charAt(i);
            TrieNode node = currentNode.children.get(ch);
            if (node == null) {
                return new ArrayList<>(); // 접두사가 없다면 빈 리스트 반환
            }
            currentNode = node;
        }
        List<String> allWords = new ArrayList<>();
        findAllWords(currentNode, new StringBuilder(searchWords), allWords);
        return allWords;
    }

    private void findAllWords(TrieNode node, StringBuilder word, List<String> list) {
        if (node.endOfString) {
            list.add(word.toString());
        }
        for (char ch : node.children.keySet()) {
            word.append(ch);
            findAllWords(node.children.get(ch), word, list);
            word.deleteCharAt(word.length() - 1); // 백트래킹
        }
    }
}
