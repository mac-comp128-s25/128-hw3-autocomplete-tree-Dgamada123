package autoComplete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 * Dureti, I got help from multiple preceptors to complete this assignment.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * If the first letter does not exist as root's child, then create new node, assign it this first letter, and add it as a child for root.
     * If the first letter does exist as root's child, create a representative node for it.
     * Iterate through the rest of the letters in the word, updating the first letter node to be the next letter node,
     * checking if the current letter already has a node made for it. If no node exists, create a node for this new letter,
     * and make the current letter a child of the previous letter.
     * @param word
     */
    public void add(String word){
        TreeNode aLetterNode = new TreeNode();
        aLetterNode.letter = word.charAt(0);
        if (!root.children.containsKey(word.charAt(0))) {
            root.children.put(word.charAt(0), aLetterNode);
        } 
        aLetterNode = root.children.get(word.charAt(0));
            for (int i = 1; i < word.length(); i++) {
                Character character = word.charAt(i);
                if (!aLetterNode.children.containsKey(character)) {
                    TreeNode nextLetter = new TreeNode();
                    nextLetter.letter = character;
                    aLetterNode.children.put(character, nextLetter);
                }
                aLetterNode = aLetterNode.children.get(character);
                if (i == word.length()-1 && !aLetterNode.isWord) {
                    aLetterNode.isWord = true;
                    size++;
                }
        }
    }

    /**
     * Checks whether the word has been added to the tree. Uses similar methods as the add function to loop through the word's
     * children nodes for the length of the word to check if the word is contained.
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){
        TreeNode aLetterNode = new TreeNode();
        aLetterNode.letter = word.charAt(0);
        if (root.children.containsKey(word.charAt(0))) {
            aLetterNode = root.children.get(word.charAt(0));
        } else {
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            Character character = word.charAt(i);
            if (aLetterNode.children.containsKey(character)) {
                TreeNode nextLetter = new TreeNode();
                nextLetter.letter = character;
            }
            aLetterNode = aLetterNode.children.get(character);
            if (i == word.length()-1 && aLetterNode.isWord) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recursive helper method for getWordsForPrefix that recursively functions on each currentNode, which will be children nodes of the
     * previous currentNode. Each recursion will check if the currentNode is a word, and if so, will add it to the list. Each recursion
     * will also update the prefix to contain the previous letter node so that prefix is accurately the actual word. 
     * Once all recursions are complete, the prefixList will be returned.
     * @param prefix
     * @param currentNode
     * @param prefixList
     * @return
     */
    public ArrayList<String> getWordsForPrefixHelper(String prefix, TreeNode currentNode, ArrayList<String> prefixList) {
        if (currentNode.isWord) {
            prefix+=currentNode.letter;
            prefixList.add(prefix);
        } else {
            prefix+=currentNode.letter;
        }
        for (TreeNode aNode: currentNode.children.values()) {
            TreeNode placeholderNode = aNode;
            getWordsForPrefixHelper(prefix, placeholderNode, prefixList);
        }
        return prefixList;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * Does so by looping through the length of the prefix, then checking if this prefix is a word.
     * Calls a helper method to loop through the children of the last node of the prefix and return
     * the list of words from the helper method.
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> prefixList = new ArrayList<>();
        TreeNode aNode = new TreeNode();
        if (root.children.containsKey(prefix.charAt(0))) {
            aNode = root.children.get(prefix.charAt(0));
        for (int i = 1; i < prefix.length(); i++) {
            if (aNode == null || aNode.children == null) {
                System.out.println("Not a prefix");
                return prefixList;
            }
            aNode = aNode.children.get(prefix.charAt(i));
            if (i == prefix.length()-1) {
                if (aNode.isWord) {
                    prefixList.add(prefix);
                }
            }
        }
        for (TreeNode aChar : aNode.children.values()) {
            getWordsForPrefixHelper(prefix, aChar, prefixList);
        }
    } 
        return prefixList;
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }

}
