package autoComplete;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
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
     * Checks whether the word has been added to the tree
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
     * Helper method for getWordsForPrefix
     */
    public ArrayList<String> getWordsForPrefixHelper(String prefix, TreeNode currentNode) {

        ArrayList<String> prefixList = new ArrayList<>();

        if (currentNode.children.isEmpty()) {
            return prefixList;
        }

        prefix+= currentNode.letter;

        if (currentNode.isWord) {
            prefixList.add(prefix);
        }

        getWordsForPrefixHelper(prefix, currentNode);

        return null;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        //TODO: complete me

        ArrayList<String> prefixList = new ArrayList<>();

        //aNode is now the first node from the root's descendents
        TreeNode aNode = new TreeNode();
        aNode = root.children.get(prefix.charAt(0));

        for (int i = 0; i < prefix.length(); i++) {
            
            aNode.letter = prefix.charAt(i);
            aNode = aNode.children.get(prefix.charAt(i));

            if (i == prefix.length()) {
                //or prefix.charAt(i)
                if (aNode.isWord) {
                    prefixList.add(prefix);
                }
            }
        }
        
       
        for (TreeNode aChar : aNode.children.values()) {
            getWordsForPrefixHelper(prefix, aChar);
        }

        

        return prefixList;
    }

    

    

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    


    //Check: similar to contains method, where i check if it is in there, if not in there return an empty list. 

    //traverses to end of prefix
    //checks if prefix is a word itself
    //for all children, call helper


    //update prefix, check if isword, call itself



    // if there are multiple decendents, call recursive method
    // if word, add it to the list. Then, for each child, call prefixhelper. PrefixHelper will check if it is a word
    // Update prefix. Then loops through children, and calls itself in this loop.
}
