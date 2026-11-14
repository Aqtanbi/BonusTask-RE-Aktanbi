import java.util.LinkedList;
import java.util.Queue;

public class AhoCorasick {
    private final Node root = new Node();

    public void insert(String pattern, int id) {
        Node curr = root;
        for (char ch : pattern.toCharArray()) {
            int c = ch - 'a';
            if (curr.next[c] == null) {
                curr.next[c] = new Node();
            }
            curr = curr.next[c];
        }
        curr.out.add(id);
    }

    public void build() {
        Queue<Node> q = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            if (root.next[i] != null) {
                root.next[i].fail = root;
                q.add(root.next[i]);
            } else {
                root.next[i] = root;
            }
        }

        while (!q.isEmpty()) {
            Node curr = q.poll();
            for (int i = 0; i < 26; i++) {
                if (curr.next[i] != null) {
                    Node failNode = curr.fail;
                    while (failNode.next[i] == null) {
                        failNode = failNode.fail;
                    }
                    curr.next[i].fail = failNode.next[i];
                    curr.next[i].out.addAll(curr.next[i].fail.out);
                    q.add(curr.next[i]);
                }
            }
        }
    }

    public int search(String text) {
        Node curr = root;
        int matches = 0;
        for (char ch : text.toCharArray()) {
            int c = ch - 'a';
            while (curr.next[c] == null) {
                curr = curr.fail;
            }
            curr = curr.next[c];
            matches += curr.out.size();
        }
        return matches;
    }
}