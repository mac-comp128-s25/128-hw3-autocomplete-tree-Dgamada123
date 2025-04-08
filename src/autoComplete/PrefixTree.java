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
                TreeNode nextLetter = new TreeNode();
                nextLetter.letter = character;

                if (!aLetterNode.children.containsKey(character)) {
                    aLetterNode.children.put(character, nextLetter);

                    aLetterNode = nextLetter;
                    aLetterNode.letter = nextLetter.letter;

                    if (i == word.length()-1) {
                        nextLetter.isWord = true;
                        size++;
                    }
    
                }
        }
    }


    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){

        for (int i=0; i<word.length(); i++) {
            if (root.children.containsKey(word.charAt(i))) {
                if (root.children.get(word.charAt(i)).isWord) {
                    return true;
                }
                root.children = root.children.get(word.charAt(i)).children;
                continue;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        //TODO: complete me
        return null;
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    
}
