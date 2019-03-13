package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program which creates and prints an ordered binary tree of unique node values.
 * Node values are input through the console. Typing "kraj" prints the tree and ends the program.
 *
 * @author Bruna Dujmović
 *
 */
public class UniqueNumbers {

    /**
     * Static class that represents a binary tree node.
     */
    public static class TreeNode {
        TreeNode left;  // Left child of the tree node
        TreeNode right; // Right child of the tree node
        int value;  // Value of the tree node
    }

    /**
     * Main method. Creates an ordered binary tree of node values entered through the keyboard, and prints it in
     * ascending & descending order.
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        TreeNode head = null;

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.format("Unesite broj > ");

            if (sc.hasNextInt()) {
                int value = sc.nextInt();

                if (containsValue(head, value)) {
                    System.out.println("Broj već postoji. Preskačem.");
                } else {
                    System.out.println("Dodajem.");
                    head = addNode(head, value);
                }

            } else {
                String input = sc.next();

                if (input.equals("kraj")) {
                    System.out.format("Ispis od najmanjeg: ");
                    printTreeInOrder(head, true);
                    System.out.format("%nIspis od najvećeg: ");
                    printTreeInOrder(head, false);
                    break;
                }

                System.out.format("'%s' nije cijeli broj.", input);
            }
        }

        sc.close();
    }

    /**
     * Calculates the size of a given binary tree.
     * @param head head node of the tree
     * @return size of the tree
     */
    public static int treeSize(TreeNode head) {
        if (head == null) {
            return 0;
        }

        return treeSize(head.left) + 1 + treeSize(head.right);
    }

    /**
     * Checks if a node exists in the ordered binary tree.
     * @param head head node of the ordered binary tree
     * @param value value of node to check
     * @return true if node exists in the tree, else false
     */
    public static boolean containsValue(TreeNode head, int value) {
        if (head == null) {
            return false;
        }

        if (head.value == value) {
            return true;
        } else if (value < head.value) {
            return containsValue(head.left, value);
        } else {
            return containsValue(head.right, value);
        }
    }

    /**
     * Creates a new tree node.
     * @param value value of the new node
     * @return newly created node
     */
    private static TreeNode createNode(int value) {
        TreeNode node = new TreeNode();

        node.left = null;
        node.right = null;
        node.value = value;

        return node;
    }

    /**
     * Inserts a node into the ordered binary tree if it doesn't already exist.
     * @param head head node of the ordered binary tree
     * @param value value of node that should be added
     * @return head node of the tree
     */
    public static TreeNode addNode(TreeNode head, int value) {
        if (head == null) {
            return createNode(value);
        }

        if (!containsValue(head, value)) {
            if (value < head.value) {
                head.left = addNode(head.left, value);
            }
            else if (value > head.value) {
                head.right = addNode(head.right, value);
            }
        }

        return head;
    }

    /**
     * Prints the nodes of an ordered binary tree in ascending or descending order.
     * @param head head node of the ordered binary tree
     * @param ascending true if nodes should be printed in ascending order, else false
     */
    public static void printTreeInOrder(TreeNode head, boolean ascending) {
        if (head == null) {
            return;
        }

        printTreeInOrder(ascending ? head.left : head.right, ascending);
        System.out.format("%d ", head.value);
        printTreeInOrder(ascending ? head.right : head.left, ascending);
    }
}
