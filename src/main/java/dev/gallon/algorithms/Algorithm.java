package dev.gallon.algorithms;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JTopology;
import io.jbotsim.ui.JViewer;

public abstract class Algorithm {

    protected Topology tp;
    protected JViewer jviewer;

    Algorithm(Class<? extends Node> nodeClass) {
        this.tp = new Topology();
        this.tp.setDefaultNodeModel(nodeClass);
    }

    public abstract void loadGraph(String dot);

    public Algorithm build() {
        this.jviewer = new JViewer(tp, false);
        return this;
    }

    public JTopology getView() {
        return this.jviewer.getJTopology();
    }

    public abstract String toString();
}
