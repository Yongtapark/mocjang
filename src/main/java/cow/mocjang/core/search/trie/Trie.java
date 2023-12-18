package cow.mocjang.core.search.trie;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
        System.out.println("the trie has been created");
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

        //현재 문자 외에도 사용하는 문자가 존재할 경우
        if (currentNode.children.size() > 1) {
            delete(currentNode, word, index + 1);
            return false;
        }

        //끝까지 순회하였을 때
        if (index == word.length() - 1) {
            //마지막 문자열의 자식이 본인문자뿐만이 아닐 때
            if (currentNode.children.size() >= 1) {
                currentNode.endOfString = false;
                return false;
            } else {
                //본인 뿐이라면 삭제
                parentNode.children.remove(ch);
                return true;
            }
        }

        // 현재노드의 끝이 사실일때 삭제
        if (currentNode.endOfString) {
            delete(currentNode, word, index + 1);
            return false;
        }

        canThisNodeBeDeleted = delete(currentNode, word, index + 1);

        //현재 노드의 끝이 사실일때 부모노드의 자식 삭제
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

    // 접두사로 시작하는 모든 단어를 찾는 메서드
    public List<String> findAllWithPrefix(String searchWords) {
        TrieNode currentNode = root;
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

    // 주어진 노드부터 모든 단어를 찾는 재귀 메서드
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
