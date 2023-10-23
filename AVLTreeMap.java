package project2;
import java.lang.AssertionError; // needed for testing
import java.lang.IllegalArgumentException; // needed for testing
import java.math.*;

public class AVLTreeMap implements Map {

    class TreeNode {
        Integer key = 0;
        Integer value = 0;
        TreeNode left = null; // DO NOT RENAME THIS
        TreeNode right = null; // DO NOT RENAME THIS
        Integer height;

        TreeNode(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 0;
        }

    }

    private int size = 0;
    private TreeNode root = null; // DO NOT RENAME THIS


    public AVLTreeMap() {
        root = null;
    }

    public void clear() {
        root = null;
        this.size = 0;
    }

    public int size() {
        return this.size; 
    }
    
    //helper
    public int height(TreeNode N) { 
    	if (N == null) {
    		return 0; 
    	}
    	return N.height;
    }
    
    //helper
    public int max(int a, int b) { 
    	return (a > b) ? a : b;
    }
    
  //helper
    public TreeNode rightRotate(TreeNode y) { 
    	TreeNode x = y.left;
	    TreeNode T2 = x.right;
	    x.right = y;
	    y.left = T2;
	    y.height = max(height(y.left), height(y.right)) + 1; 
	    x.height = max(height(x.left), height(x.right)) + 1; 
	    return x;
    }
    
  //helper
    public TreeNode leftRotate(TreeNode x) { 
    	TreeNode y = x.right;
	    TreeNode T2 = y.left;
	    y.left = x;
	    x.right = T2;
	    x.height = max(height(x.left), 
	    height(x.right)) + 1;
	    y.height = max(height(y.left), 
	    height(y.right)) + 1;
	    return y; 
	}

  //helper
    public int getBalanceFactor(TreeNode node) {
    	if (node == null) {
    		return 0;
    	}
    	return height(node.left) - height(node.right); 
    }

    public boolean put(Integer key, Integer value) {
    	
    	if (root == null || size == 1) {
    		return put(key, value, root);
    	} else if (key < root.key){
    		return put(key, value, root.left);
    	} else {
    		return put(key, value, root.right);
    	}
    }
    
    public boolean put(Integer key, Integer value, TreeNode node) {
    	// Find position and insert node 
    	if (contains(key) == false) {
    		this.size++;
    		TreeNode newNode = new TreeNode(key, value);
    		if (node == null) {
    			root = newNode;
    			return true;
    		}
    		else if (key < node.key) {
    			node.left = newNode;
    		}
    		else if (key > node.key) {
    			node.right = newNode;
    		}
    		else {
    			return true;
    		}
    	}
    	else {
    		Integer newValue = value;
    		// Find node
    		if (node == null) {
    			new TreeNode(key,value);
    		}
    		else if (key < node.key) {
    			node = node.left;
    		}
    		else if (key > node.key) {
    			node = node.right;
    		}
    		// Assign new value
    		node.value = newValue;
    		return false;
    	}
    	
    	
    	
    	// Update balance factor of each node and balance the tree
    	node.height = 1 + max(height(node.left), height(node.right)); 
    	int balanceFactor = getBalanceFactor(node);
    	if (balanceFactor > 1) {
    		if (key < node.left.key) { 
    			rightRotate(node);
    	} else if (key > node.left.key) { 
    		node.left = leftRotate(node.left); 
    		rightRotate(node);
    		} 
    	}
    	if (balanceFactor < -1) {
    		if (key > node.right.key) {
    			leftRotate(node);
    	} else if (key < node.right.key) {
    		node.right = rightRotate(node.right);
    		leftRotate(node); 
    		}
    	}
    	return true; 
    	}
    	
    
    
    /**
     * Check if a key is in the Map.
     *
     * @param key The key.
     * @return True if the key is in the map, false otherwise.
     */
    public boolean contains(Integer key) {
        return contains(key, root);
    }
    
    public boolean contains(Integer key, TreeNode node) {
    	boolean found = false;
    	while (node != null && !found)  {  
            int nodeKey = node.key;  
            if (key < nodeKey)  
                node = node.left;  
            else if (key > nodeKey)  
                node = node.right;  
            else  
            {  
                found = true;
                break;  
            }  
            found = contains(key, node);  
        }  
        return found; 
    }
    
    /**
     * Get the value associated with a key.
     *
     * This function assumes that the key is in the Map.
     *
     * @param key The key.
     * @return The value associated with the key.
     */
    public Integer get(Integer key) {
        return get(key, root);
    }
    
    public Integer get(Integer key, TreeNode node) {
    	while (node != null)  {  
            int nodeKey = node.key;  
            if (key < nodeKey)  
                node = node.left;  
            else if (key > nodeKey)  
                node = node.right;  
            else {  
                break;  
            }
            
    	}
    	return node.value;
    }
    
  

    public void remove(Integer key) {
        return; // FIXME optional
    }

    // DO NOT CHANGE ANYTHING BELOW THIS LINE

    /**
     * Check that an AVLTreeMap matches the given structure string.
     *
     * This checks that an AVLTreeMap has the structure as defined by the
     * string argument. The string represents every TreeNode as a parenthesis,
     * inside of which is the key, followed by a colon, followed by the value.
     * Finally, the string representation of the left and right child are
     * added.
     *
     * The one special case is for the empty node, which is represented by a
     * pair of empty parenthesis "()". Null children are always included in the
     * structure, as it is necessary to know whether one child is the left or
     * right.
     *
     * As an example, consider the following tree. The keys are drawn in the
     * diagram below, with the values being the squares of the keys:
     *
     *       5
     *      / \
     *     3   6
     *    / \   \
     *   1   4   7
     *
     * This tree is represented by the string:
     *
     * (5:25(3:9(1:1()())(4:16()()))(6:36()(7:49()())))
     *
     * For the purposes of testing, it may be clearer to build the string over
     * multiple lines:
     *
     * String structure =
     *     "(5:25" +
     *       "(3:9" +
     *         "(1:1()())" +
     *         "(4:16()())" +
     *       ")" +
     *       "(6:36" +
     *         "()" +
     *         "(7:49()())" +
     *       ")" +
     *     ")";
     *
     * Calling this method with the above string will check that the tree has
     * the specified structure. An AssertionError will be thrown if the tree
     * does not match the structure. An IllegalArgumentException will be thrown
     * if the string is malformed. If the string is valid and the tree matches
     * the described structure, the function will return normally.
     *
     * @param structure The structure string for the entire tree.
     * @throws IllegalArgumentException If the string is malformed.
     * @throws AssertionError If the tree doesn't match the string.
     */
    public void assertStructureEquals(String structure) {
        // DO NOT CHANGE THIS
        structure = structure.replaceAll(" ", "");
        int parsedIndex = AVLTreeMap.assertStructureEquals(this.root, structure, 0);
        if (parsedIndex != structure.length() - 1) {
            throw new AssertionError(
                "assertStructureEquals called with structure string that has trailing characters"
            );
        }
    }

    private static int assertStructureEquals(TreeNode node, String structure, int start) {
        // DO NOT CHANGE THIS
        // check that the string is valid
        if (structure.charAt(start) != '(') {
            throw new IllegalArgumentException(
                "assertStructureEquals called with invalid structure string: " + structure.substring(start)
            );
        }
        // check for the empty tree
        if (structure.charAt(start + 1) == ')') {
            if (node == null) {
                return start + 1;
            } else {
                throw new AssertionError("expected the empty tree but got a non-null TreeNode");
            }
        }
        // parse key
        String keyStr = "";
        int index = start + 1;
        while (Character.isDigit(structure.charAt(index)) || structure.charAt(index) == '-') {
            keyStr += structure.charAt(index);
            index++;
        }
        if (structure.charAt(index) != ':') {
            throw new IllegalArgumentException(
                "assertStructureEquals called with invalid key-value pair: " + structure.substring(start, index)
            );
        }
        // check key
        int key = Integer.parseInt(keyStr);
        if (node == null) {
            throw new AssertionError("expected key " + key + " but got the empty tree");
        } else if (node.key != key) {
            throw new AssertionError("expected key " + key + " but got " + node.key);
        }
        // parse value
        index++;
        String valStr = "";
        while (Character.isDigit(structure.charAt(index)) || structure.charAt(index) == '-') {
            valStr += structure.charAt(index);
            index++;
        }
        // check value
        int val = Integer.parseInt(valStr);
        if (node.value != val) {
            throw new AssertionError("expected key " + key + " to have value " + val + " but got " + node.value);
        }
        // check the left child
        int leftIndex = assertStructureEquals(node.left, structure, index);
        // check the right child
        return assertStructureEquals(node.right, structure, leftIndex + 1) + 1;
    }

    /**
     * Create an sorted array of the keys.
     *
     * For example, if the tree was:
     *
     *       5
     *      / \
     *     3   6
     *    / \   \
     *   1   4   7
     *
     * this function would return:
     *
     * {1, 3, 4, 5, 6, 7}
     *
     * @return An array of the keys of the map, in sorted order.
     */
    public int[] toKeysArray() {
        // DO NOT CHANGE THIS
        int[] result = new int[this.size()];
        int index = 0;

        TreeNode[] stack = new TreeNode[this.size()];
        int depth = -1;

        TreeNode curr = this.root;
        while (curr != null) {
            depth++;
            stack[depth] = curr;
            curr = curr.left;
        }

        while (depth >= 0) {
            result[index] = stack[depth].key;
            index++;

            if (stack[depth].right != null) {
                curr = stack[depth].right;
                while (curr != null) {
                    depth++;
                    stack[depth] = curr;
                    curr = curr.left;
                }
            } else {
                int oldKey = stack[depth].key;
                do {
                    depth--;
                } while (depth >= 0 && stack[depth].key < oldKey);
            }
        }

        return result;
    }

    /**
     * Print the AVLTreeMap in a pretty way.
     *
     * The printout will put the right child on a line before their parent,
     * which in turn will be before the left child. Consider this AVLTreeMap
     * below, with the values being the squares of the keys:
     *
     *       5
     *      / \
     *     3   6
     *    / \   \
     *   1   4   7
     *
     * This method will print this tree as:
     *
     *     7: 49
     *   6: 36
     * 5: 25
     *     4: 16
     *   3: 9
     *     1: 1
     *
     * The number before the colon (:) is the key, and the number after the
     * colon is the value. This particular printing format means that you can
     * rotate your screen clockwise by 90 degrees to get a "graphical"
     * representation.
     *
     * Note that this function is provide only for debugging purposes, and will
     * not always work. In particular, this function will cause a stack
     * overflow if the tree is circular.
     */
    public void prettyPrint() {
        // DO NOT CHANGE THIS
        this.prettyPrint(this.root, 0);
    }

    private void prettyPrint(TreeNode node, int depth) {
        // DO NOT CHANGE THIS
        if (node == null) {
            return;
        }
        this.prettyPrint(node.right, depth + 1);
        String line = "";
        for (int i = 0; i < depth; i++) {
            line += "  ";
        }
        line += node.key + ": " + node.value;
        System.out.println(line);
        this.prettyPrint(node.left, depth + 1);
    }
    
    public static void main(String[] args) {
    	AVLTreeMap tree = new AVLTreeMap();


    	System.out.println(tree.put(9, 81)); //true
    	System.out.println(tree.put(2, 4)); //true
    	System.out.println(tree.put(1, 1)); //true
    	System.out.println(tree.contains(2)); //true
    	tree.prettyPrint();

    	System.out.println(tree.contains(5)); // false 
    	System.out.println(tree.get(9)); // 81

    	System.out.println(tree.size()); // 3
    	tree.clear();
    	tree.prettyPrint(); 

    	System.out.println(tree.put(3, 9)); // true
    	System.out.println(tree.put(6, 36)); // true
    	System.out.println(tree.put(7, 49)); // true
    	System.out.println(tree.put(7, 99)); // false
    	
    	tree.prettyPrint();
    	System.out.println(tree.contains(6)); // true
    	System.out.println(tree.size()); // 3
    	System.out.println(tree.get(7)); // 99
    	System.out.println(tree.get(3)); // 9
    	tree.clear();
    	
    	tree.prettyPrint();
    	System.out.println(tree.contains(3)); // false
    	System.out.println(tree.size()); // 0
    	
    	
    }
    	
    

}