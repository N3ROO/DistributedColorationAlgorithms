package dev.gallon.algorithms;

import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JTopology;

public abstract class Algorithm {

    protected Topology tp;
    protected JTopology jtp;

    Algorithm(Class<? extends Node> nodeClass) {
        this.tp = new Topology();
        this.tp.setDefaultNodeModel(nodeClass);
    }

    public abstract void loadGraph(String dot);

    public Algorithm build() {
        this.jtp = new JTopology(tp);
        return this;
    }

    public JTopology getView() {
        return this.jtp;
    }

    public abstract String toString();
}
