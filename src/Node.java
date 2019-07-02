class Node {
    int key;
    int level;
    Node left, right;

    Node(int item, int level) {
        key = item;
        this.level = level;
        left = right = null;
    }
}