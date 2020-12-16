package dev.gallon.algorithms;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.core.event.TopologyListener;
import io.jbotsim.ui.JTopology;
import io.jbotsim.ui.JViewer;

public class Algorithm {

    private final String name;
    protected JViewer jviewer;

    public Algorithm(Class<? extends NodeWithProperties> nodeClass, String name) {
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
    }

    private void updateGraphVariables() {
        // todo update graph variables
        NodeWithProperties.DEG_MAX = 0;
    }

    public JTopology getView() {
        return this.jviewer.getJTopology();
    }

    public String toString() {
        return this.name;
    }
}
