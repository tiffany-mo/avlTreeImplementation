package project2;
import java.lang.AssertionError;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AVLTreeMapTestUtils {

    /**
     * Check if two booleans are the same.
     *
     * @param actual The boolean to check.
     * @param expected The expected (correct) boolean.
     */
    public static void assertEquals(boolean actual, boolean expected) {
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    /**
     * Check if two Integers are equal.
     *
     * Note that Java automatically converts ints to Integers, so this will work:
     *
     * assertEquals(2, Integer.valueOf(2));
     *
     * @param actual The computed integer to check.
     * @param expected The expected (correct) integer.
     */
    public static void assertEquals(Integer actual, Integer expected) {
        if (!actual.equals(expected)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

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
     *         "(3:9" +
     *             "(1:1()())" +
     *             "(4:16()())" +
     *         ")" +
     *         "(6:36" +
     *             "()" +
     *             "(7:49()())" +
     *         ")" +
     *     ")";
     *
     * Calling this method with the above string will check that the tree has
     * the specified structure. An AssertionError will be thrown if the tree
     * does not match the structure. An IllegalArgumentException will be thrown
     * if the string is malformed. If the string is valid and the tree matches
     * the described structure, the function will return normally.
     *
     * @param tree The AVLTree to check.
     * @param structure The structure string for the entire tree.
     * @throws IllegalArgumentException If the string is malformed.
     * @throws AssertionError If the tree doesn't match the string.
     */
    public static void assertTreeStructure(AVLTreeMap tree, String structure) {
        tree.assertStructureEquals(structure);
        Matcher matcher = Pattern.compile("(-?[0-9]+):(-?[0-9]+)").matcher(structure);
        int size = 0;
        while (matcher.find()) {
            int key = Integer.parseInt(matcher.group(1));
            int value = Integer.parseInt(matcher.group(2));
            if (!tree.contains(key)) {
                throw new AssertionError("expected contains(" + key + ") to be true but got false");
            } else if (tree.get(key) != value) {
                throw new AssertionError("expected get(" + key + ") to be " + key + " but got " + tree.get(key));
            }
            size++;
        }
        if (tree.size() != size) {
            throw new AssertionError("expected size() to be " + size + " but got " + tree.size());
        }
    }

}