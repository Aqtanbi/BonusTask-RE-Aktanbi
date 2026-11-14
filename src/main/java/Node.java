import java.util.ArrayList;
import java.util.List;

public class Node {
    public Node[] next = new Node[26];
    public Node fail;
    public List<Integer> out = new ArrayList<>();
}