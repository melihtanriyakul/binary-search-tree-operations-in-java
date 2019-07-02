import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

class OperationHandler {
    static void handleOperation(String operations, PrintWriter output) {
        if (!operations.equals("")) {
            String[] lines = operations.split("\\r?\\n");
            BinarySearchTree bst = new BinarySearchTree();

            for (String line : lines) {
                /**
                 * Splits the line into two parts for the CreateBST and
                 * CreateBSTH operations.
                 */
                String[] splitLine = line.split(" ");
                String keyword = splitLine[0];

                try {
                    /**
                     * Selects the operation according to the keyword and calls corresponding
                     * method.
                     */
                    switch (keyword) {
                        case "CreateBST": {
                            /** Converts the numbers in input file into integers */
                            String[] strNumbers = splitLine[1].split(",");
                            int[] nums = new int[strNumbers.length];

                            for (int i = 0; i < strNumbers.length; i++) {
                                nums[i] = Integer.parseInt(strNumbers[i]);
                            }

                            bst = createBST(nums, output);
                        }
                        break;
                        case "CreateBSTH": {
                            int height = Integer.parseInt(splitLine[1]);
                            if (height > 0) {
                                bst = createBSTH(height, output);
                            } else {
                                output.println("error");
                            }
                        }
                        break;
                        case "FindHeight": {
                            if (bst.root != null) {
                                output.println("Height:" + bst.getHeight());
                            } else {
                                output.println("error");
                            }
                        }
                        break;
                        case "FindWidth": {
                            if (bst.root != null) {
                                int width;
                                int[] numOfNodesAtLevels = new int[bst.getHeight() + 1];

                                width = findWidth(bst.root, numOfNodesAtLevels);
                                output.println("Width:" + width);
                            } else {
                                output.println("error");
                            }
                        }
                        break;
                        case "Preorder": {
                            if (bst.root != null) {
                                ArrayList<String> nodes = new ArrayList<>();
                                nodes = bst.preOrderRec(bst.root, nodes);

                                String strNodes = String.join(" ", nodes);
                                output.println("Preorder:" + strNodes);
                            } else {
                                output.println("error");
                            }
                        }
                        break;
                        case "LeavesAsc": {
                            ArrayList<String> leaves = new ArrayList<>();
                            if (bst.root != null) {
                                getLeaves(bst.root, leaves);
                            }

                            String strNodes = String.join(" ", leaves);
                            output.println("LeavesAsc:" + strNodes);
                        }
                        break;
                        case "DelRoot": {
                            if (bst.root != null) {
                                output.println("Root Deleted:" + bst.root.key);
                                bst.root = delRoot(bst.root);
                            }
                        }
                        break;
                        case "DelRootLc": {
                            if (bst.root != null && bst.root.left != null) {
                                output.println("Left Child of Root Deleted:" + bst.root.left.key);
                                bst.root.left = delRoot(bst.root.left);
                            }
                        }
                        break;
                        case "DelRootRc": {
                            if (bst.root != null && bst.root.right != null) {
                                output.println("Right Child of Root Deleted:" + bst.root.right.key);
                                bst.root.right = delRoot(bst.root.right);
                            }
                        }
                        break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    output.println("error");
                }
            }
        } else {
            System.out.println("The input file is empty.");
        }
    }

    /**
     * Creates a binary search tree and inserts the given numbers into it by using
     * the insert method of Binary Search Tree class and updates the output file.
     */
    private static BinarySearchTree createBST(int[] numbers, PrintWriter output) {
        BinarySearchTree bst = new BinarySearchTree();

        for (int number : numbers) {
            bst.insert(number);
        }

        /** Write the BST tree to an output file with inorder traversal. */
        output.print("BST created with elements:");
        writeNodesToOutput(bst, output);

        return bst;
    }

    /**
     * Creates a binary search tree and recursively inserts random numbers into it
     * until the height of the tree to the designated height which means the tree
     * contains the maximum number of nodes with respect to the given height.
     */
    private static BinarySearchTree createBSTH(int height, PrintWriter output) {
        BinarySearchTree bst = new BinarySearchTree();
        Random rand = new Random();
        int totalNodeCount = (int) Math.pow(2, (height + 1)) - 1;

        while (totalNodeCount > bst.getNodeCount()) {
            int randNum = rand.nextInt(10 * (int) Math.pow(10, height)) + 200;
            int level = bst.root != null ? bst.root.level : -1;
            bst.root = insertForFullBST(bst.root, height, randNum, level, bst);
        }

        /** Write the full BST tree to an output file with inorder traversal. */
        output.print("A full BST created with elements:");
        writeNodesToOutput(bst, output);

        return bst;
    }

    /**
     * Recursively inserts a new key in BST until the tree becomes a full binary
     * search tree and reaches the designated height.
     */
    private static Node insertForFullBST(Node root, int height, int val, int level, BinarySearchTree bst) {

        if (height > level) {

            /** If tree is empty, creates a new node with the given key and returns it. */
            if (root == null) {
                root = new Node(val, level + 1);
                bst.setNodeCount(bst.getNodeCount() + 1);
                return root;
            }

            if (val < root.key) {
                root.left = insertForFullBST(root.left, height, val - 20, root.level, bst);
            }
            if (val > root.key) {
                root.right = insertForFullBST(root.right, height, val + 20, root.level, bst);
            }
        }

        return root;
    }

    /**
     * Writes the nodes of the given tree to the given output file with inorder
     * traversal.
     */
    private static void writeNodesToOutput(BinarySearchTree bst, PrintWriter output) {
        ArrayList<String> nodes = new ArrayList<>();
        nodes = bst.inOrderRec(bst.root, nodes);

        String strNodes = String.join(" ", nodes);
        output.println(strNodes);
    }

    /**
     * Traverse the whole tree and updates the given array of the number of nodes
     * lying on each level. After the traversal is done, finds the maximum in the
     * array and returns it as the maximum width of the tree.
     */
    private static int findWidth(Node root, int[] numOfNodes) {
        if (root != null) {
            findWidth(root.left, numOfNodes);
            numOfNodes[root.level]++;
            findWidth(root.right, numOfNodes);
        }

        int maxWidth = 0;
        for (int num : numOfNodes) {
            if (num > maxWidth) {
                maxWidth = num;
            }
        }

        return maxWidth;
    }

    /**
     * Traverses the tree recursively and finds the leaves of it and adds them into
     * the given arraylist.
     */
    private static void getLeaves(Node root, ArrayList<String> leaves) {
        if (root.left == null && root.right == null) {
            leaves.add(Integer.toString(root.key));
        }

        if (root.left != null) {
            getLeaves(root.left, leaves);
        }

        if (root.right != null) {
            getLeaves(root.right, leaves);
        }
    }

    /**
     * Finds the node with a minimum value which means the most leftwards node in
     * the tree and returns it.
     */
    private static Node minNode(Node root) {
        Node current = root;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    /**
     * Finds the node with minimum value and deletes
     * it and assigns its children to the its parent
     * and returns the root. This method used in the
     * method for deleting the root of the tree.
     */
    private static Node delMin(Node root) {
        Node current = root;
        if (root.left == null) {
            root = root.right;
        } else {
            while (current.left.left != null) {
                current = current.left;
            }

            current.left = current.left.right;
        }

        return root;
    }

    /**
     * Deletes the root of tree and assigns
     * the node with minimum value of its
     * right subtree and if there is no node
     * on its right subtree, then assigns
     * the node with maximum value of its
     * left subtree and returns the root.
     */
    private static Node delRoot(Node root) {
        Node min;

        if (root.right == null && root.left == null) {
            return null;
        } else if (root.right == null) {
            return root.left;
        } else if (root.left == null) {
            return root.right;
        } else {
            min = minNode(root.right);

            root.right = delMin(root.right);
        }

        min.level = root.level;
        min.left = root.left;
        min.right = root.right;
        root = min;

        return root;
    }
}
