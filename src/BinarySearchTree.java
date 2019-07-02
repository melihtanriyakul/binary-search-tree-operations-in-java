import java.util.ArrayList;

class BinarySearchTree {
    /**
     * Root of Binary Search Tree.
     */
    Node root;
    private int nodeCount;
    private int height = 0;

    /**
     * Constructor
     */
    BinarySearchTree() {
        root = null;
    }


    void insert(int key) {
        root = insertRec(root, key, 0);
    }

    /**
     * Insert a new key in BST recursively.
     */
    private Node insertRec(Node root, int key, int level) {
        /** If tree is empty, creates a new node with the given key and returns it. */
        if (root == null) {
            if (level > height) {
                height = level;
            }
            root = new Node(key, level);
            return root;
        }

        /** If not null, traverse down the tree. */
        if (key < root.key) {
            root.left = insertRec(root.left, key, root.level + 1);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key, root.level + 1);
        }

        return root;
    }

    /**
     * Traverse the whole tree preorderly and insert the nodes
     * into an array list.
     */
    ArrayList<String> inOrderRec(Node root, ArrayList<String> nodes) {
        if (root != null) {
            inOrderRec(root.left, nodes);
            nodes.add(Integer.toString(root.key));
            inOrderRec(root.right, nodes);
        }

        return nodes;
    }

    /**
     * Traverse the whole tree preorderly and insert the nodes
     * into an arraylist.
     */
    ArrayList<String> preOrderRec(Node root, ArrayList<String> nodes) {
        if (root != null) {
            nodes.add(Integer.toString(root.key));
            preOrderRec(root.left, nodes);
            preOrderRec(root.right, nodes);
        }

        return nodes;
    }

    int getHeight() {
        return height;
    }

    int getNodeCount() {
        return nodeCount;
    }

    void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }
}
