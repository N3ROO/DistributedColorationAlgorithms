package dev.gallon.algorithms;

import io.jbotsim.core.Color;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.TopologyListener;
import io.jbotsim.ui.JTopology;
import io.jbotsim.ui.JViewer;

public class Algorithm {

    private final String name;
    protected JViewer jviewer;

    public Algorithm(Class<? extends Node> nodeClass, String name) {
        this.name = name;

        Topology tp = new Topology();
        tp.setDefaultNodeModel(nodeClass);
        tp.addTopologyListener(new TopologyListener() {
            @Override
            public void onNodeAdded(Node node) {
                updateGraphVariables();
            }

            @Override
            public void onNodeRemoved(Node node) {
                updateGraphVariables();
            }
        });
        this.jviewer = new JViewer(tp, false);
        this.jviewer.getJTopology().getTopology().setTimeUnit(1000);
    }

    private void updateGraphVariables() {
        // todo update graph variables
        GraphProperties.DEG_MAX = 0;
        GraphProperties.N = this.jviewer.getJTopology().getTopology().getNodes().size();
    }

    public void clear() {
        jviewer.getJTopology().getTopology().clear();
    }

    public JTopology getView() {
        return this.jviewer.getJTopology();
    }

    public String toString() {
        return this.name;
    }
}
