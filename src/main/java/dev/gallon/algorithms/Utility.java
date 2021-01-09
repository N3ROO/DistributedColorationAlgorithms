package dev.gallon.algorithms;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;

import java.util.*;

public class Utility {

    static final List<Color> colors;

    static {
        colors = new ArrayList<>();
        colors.addAll(Color.getIndexedColors());
        colors.addAll(Color.getIndexedColors());
        colors.addAll(Color.getIndexedColors());
    }

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

    public static int firstFree(Node... nodes) {
        return firstFree(Arrays.asList(nodes.clone()), null);
    }

    /**
     *
     * @param nodes the nodes
     * @param except the node to remove from the node list (null -> ignored)
     * @return the first available id that is not taken by the given nodes in the [min; max] interval. If not fund,
     * returns -1.
     */
    public static int firstFree(List<Node> nodes, Node except) {
        HashMap<Integer, Boolean> takenMap = new HashMap<>();
        for (Node node : nodes) {
            if (node.equals(except)) continue;
            takenMap.put(Utility.getIntFromColor(node.getColor()), true);
        }

        for (int id = 0; id < Integer.MAX_VALUE; id++) {
            if (!takenMap.getOrDefault(id, false)) return id;
        }

        return -1;
    }

    public static Color getColorFromInt(int id) {
        return getIndexedColors().get(id);
    }

    public static List<Color> getIndexedColors() {
        return colors;
    }

    public static int getIntFromColor(Color c) {
        return getIndexedColors().indexOf(c);
    }
}
