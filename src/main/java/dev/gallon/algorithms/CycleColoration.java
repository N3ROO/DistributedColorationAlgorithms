package dev.gallon.algorithms;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JTopology;

public class CycleColoration implements Algorithm {

    private Topology tp;
    private JTopology jtp;

    public CycleColoration() {
        this.tp = new Topology();
        this.tp.setDefaultNodeModel(TheNode.class);
        this.jtp = new JTopology(tp);
    }

    class TheNode extends Node {

        public TheNode() {

        }

        @Override
        public void onStart() {
            // JBotSim executes this method on each node upon initialization
        }

        @Override
        public void onSelection() {
            // JBotSim executes this method on a selected node
        }

        @Override
        public void onClock() {
            // JBotSim executes this method on each node in each round
        }

        @Override
        public void onMessage(Message message) {
            // JBotSim executes this method on a node every time it receives a message
        }
    }

    @Override
    public JTopology getView() {
        return jtp;
    }

    @Override
    public String toString() {
        return "Cycle coloration";
    }
}
