package dev.gallon.algorithms;

import io.jbotsim.core.Node;

import java.util.BitSet;
import java.util.List;

public class Utility {

    /**
     * @param x the int value (decimal format)
     * @return the first bit set position
     */
    public static int ffs(int x) {
        return (int)((Math.log10(x & -x)) / Math.log10(2)) + 1;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public static int posDiff(int x, int y) {
        int p = ffs(x^y) - 1;
        return (p<<1) | ((x>>p) & 1);
    }

    /**
     * @param nodes
     * @param except a node to remove (null to ignore)
     * @return the first free ID in the current topology
     */
    public static int firstFree(List<Node> nodes, Node except) {
        return nodes.stream()
                .filter((node) -> !node.equals(except))
                .mapToInt(Node::getID)
                .collect(BitSet::new, BitSet::set, BitSet::or).nextClearBit(0);
    }
}
