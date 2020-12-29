package dev.gallon.algorithms;

import io.jbotsim.core.Node;

public class GreedyAlgorithm extends Algorithm {

    public GreedyAlgorithm(String name) {
        super(Node.class, name);

        jviewer.getJTopology().getTopology().addStartListener(() -> {
            for (int round = 1; round <= 4; round++) {
                for (Node node : jviewer.getJTopology().getTopology().getNodes()) {
                    int newColorIndex = Utility.firstFree(node.getNeighbors(), node);
                    node.setColor(Utility.getColorFromInt(newColorIndex));
                }
            }
        });
    }


}
