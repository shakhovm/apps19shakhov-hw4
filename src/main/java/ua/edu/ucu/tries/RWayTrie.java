package ua.edu.ucu.tries;


import ua.edu.ucu.collections.Queue;

import java.util.Arrays;
import java.util.Comparator;


public class RWayTrie implements Trie {
    Queue q;
    private static int R = 256;
    private Node root;
    private int n;
    private static class Node
    {
        private Tuple val = null;//new Tuple(null, 0);
        private Node[] next = new Node[R];
    }

    private Node put(Node x, Tuple val, int d)
    { // Change value associated with key if in subtrie rooted at x.
        if (x == null) {
            x = new Node();
        }
        if (d == val.weight) {
            if (x.val == null) {
                n++;
            }
            x.val = val;
            return x;
        }
        char c = val.term.charAt(d); // Use dth key char to identify subtrie.
        x.next[c] = put(x.next[c], val, d+1);
        return x;
    }

    @Override
    public void add(Tuple t){
        root = put(root, t, 0);
    }

    public Tuple get(String key)
    {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.val;
    }
    private Node get(Node x, String key, int d)
    { // Return value associated with key in the subtrie rooted at x.
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d); // Use dth key char to identify subtrie.
        return get(x.next[c], key, d+1);
    }


    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {

        root = delete(root, word, 0);
        return true;
    }

    private Node delete(Node x, String key, int d)
    {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            if (x.val != null) {
                n--;
            }
            x.val = null;
        }

        else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }
        if (x.val != null) {
            return x;
        }
        for (char c = 0; c < R; c++)
            if (x.next[c] != null) return x;
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        Object[] objects = q.toArray();
        String[] strings = Arrays.copyOf(
                objects, objects.length, String[].class);
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        return () -> Arrays.stream(strings).iterator();
    }

    private void collect(Node x, String pre,
                         Queue q)
    {
        if (x == null) {
            return;
        }
        if (x.val != null) {
            q.enqueue(pre);
        }

        for (char c = 0; c < R; c++) {
            collect(x.next[c], pre + c, q);
        }

    }



    @Override
    public int size() {
        return n;
    }

}
