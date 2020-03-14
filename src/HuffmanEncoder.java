import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

@SuppressWarnings({"unchecked"})
public class HuffmanEncoder {

    private int encodedLength;
    private ArrayList<Node> tree;
    private String input;
    private HashMap<Character, String> codeTable;

    public HuffmanEncoder(String t) {
        tree = new ArrayList<Node>();
        input = t;
    }

    public void createTree(){
        int ret;
        char[] arr = input.toCharArray();
        for (char token : arr) {
            ret = findIndex(token);
            if (ret == -1) {
                tree.add(new Node(token, 1));
            }
        }
        Collections.sort(tree, new Node());

        while (tree.size() > 1) {
            makeNode();
        }
        codeTable = createCodeTable(tree.get(0));

    }

    public String encodeString() {
        createTree();
        char[] chars = input.toCharArray();
        StringBuilder s = new StringBuilder();
        for (char c : chars) {
            s.append(codeTable.get(c));
        }
        encodedLength = s.toString().length();
        return s.toString();
    }

    public String decodeString(String input) {
        char[] str = input.toCharArray();
        StringBuilder s = new StringBuilder();
        Node cur = tree.get(0);
        for (int i = 0; i < str.length; i++) {
            if (cur.token == '\u0000') {
                if (str[i] == '0') {
                    cur = cur.left;
                    if (cur.token != '\u0000') {
                        s.append(cur.token);
                        cur = tree.get(0);
                    }
                }
                else {
                    cur = cur.right;
                    if (cur.token != '\u0000') {
                        s.append(cur.token);
                        cur = tree.get(0);
                    }
                }
            }
        }
        return s.toString();
    }

    public void runTest() {
        this.decodeString(encodeString());
        System.out.println("Original length in bits: " + input.length() * 8);
        System.out.println("Encoded length in bits: " + encodedLength);
        System.out.println("Compression rate: " + (encodedLength * 1.0) / (input.length()* 8));

    }

    HashMap<Character, String> createCodeTable(Node node)
    {
        HashMap<Character, String> ret = new HashMap<Character, String>();
        char[] path = new char[1000];
        createCodeTable(node, path, 0, '4', ret);
        return ret;
    }

    private void createCodeTable(Node node, char[] path, int pathLen, char binary,  HashMap<Character, String> ret)
    {
        if (node == null)
            return;
        path[pathLen] = binary;
        pathLen++;

        if (node.left == null && node.right == null) {
            StringBuilder s = new StringBuilder();
            for (int i = 1; i < pathLen; i++) {
                s.append(path[i]);
            }
            ret.put(node.token, s.toString());
        }
        else
        {
            createCodeTable(node.left, path, pathLen, '0', ret);
            createCodeTable(node.right, path, pathLen, '1', ret);
        }
    }

    private void makeNode() {
        Node right = tree.remove(tree.size() - 1);
        Node left = tree.remove(tree.size() - 1);
        Node newNode = new Node(left, right);
        tree.add(newNode);
        Collections.sort(tree, new Node());
    }


    private int findIndex(char token) {
        for (Node node : tree) {
            if (node.token == token) {
                node.total++;
                return tree.indexOf(node);
            }
        }
        return -1;
    }

    private class Node implements Comparator<Node>{
        private Node left;
        private Node right;
        private int total;
        private char token;

        private Node(Node left, Node right) {
            total = left.total + right.total;
            this.left = left;
            this.right = right;
        }

        private Node(char token, int total) {
            this.token = token;
            this.total = total;
            left = null;
            right = null;
        }
        private Node() {
            left = null;
            right = null;
        }

        @Override
        public int compare(Node o1, Node o2) {
            return o2.total - o1.total;
        }
    }
}


