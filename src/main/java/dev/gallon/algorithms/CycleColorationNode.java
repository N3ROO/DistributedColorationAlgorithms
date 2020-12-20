package dev.gallon.algorithms;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

import java.util.List;

public class CycleColorationNode extends Node {

    private boolean sentDoneMessage = false;
    private int rounds = 0;

    private Integer x;
    private Integer y;
    private Double l1;
    private Double l2;
    private List<Node> children;

    @Override
    public void onStart() {
        this.x = getID();
        this.setColor(Utility.getColorFromInt(this.x));

        this.l1 = Math.ceil(Math.log(GraphProperties.N));
        this.l2 = this.l1 - 1; // Pour être sûr que l1 != l2
        this.children = getOutNeighbors();

        if (hasInNeighbor(this)) {
            this.y = Utility.firstFree(getTopology().getNodes(), this, 0, Integer.MAX_VALUE);
        }
    }

    @Override
    public void onClock() {
        // Appelée au début de chaque ronde (NewPulse)
        rounds ++;

        if (!l1.equals(l2)) {
            // b
            for (Node child : children) {
                send(child, new Message(getID()));
            }

            // c - d - e
            for (Message recvMessage : getMailbox()) {
                y = (Integer) recvMessage.getContent();
                x = Utility.posDiff(getID(), y);
                l2 = l1;
                l1 = 1 + Math.ceil(Math.log(l1));
            }
        } else if (!sentDoneMessage ){
            sentDoneMessage = true;
            System.out.println("Node " + getID() + " done! (" + rounds + " rounds)");
        }

        if (!sentDoneMessage) {
            setID(this.x);
            setColor(Utility.getColorFromInt(this.x));
        }
    }
}
