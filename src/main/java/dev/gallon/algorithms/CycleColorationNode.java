package dev.gallon.algorithms;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

import java.util.List;

public class CycleColorationNode extends Node {

    enum STATUS {
        INIT, COLOR, FIX, DONE
    }

    private STATUS status;
    private Node father;
    private List<Node> children;

    private int round;
    private int x;
    private double l;
    private int y;
    private boolean noMsgLastRound;

    @Override
    public void onStart() {
        status = STATUS.INIT;
        round = 0;
        noMsgLastRound = false;
        getMailbox().clear();
        x = getID();
        updateColor();
    }

    @Override
    public void onClock() {
        if (status != STATUS.DONE)
            round ++;

        switch (status) {
            case INIT:
                // On créé une 1-orientation
                father = getNeighbors().size() > 0 ? getNeighbors().get(0) : null;
                if (father != null) log("Father: " + father.getID());
                children = getNeighbors();
                children.remove(father);

                // Début algo color6
                // 1.
                updateColor();
                l = Math.ceil(Math.log(GraphProperties.N));
                // 2.
                if (father == null)
                    y = Utility.firstFree(getNeighbors(), this);
                else
                    y = -1;

                log("Finished init round #" + round);
                status = STATUS.COLOR;
                break;
            case COLOR:
                // Suite algo color 6

                // 3/a ok
                // b:
                for (Node child : children)
                    send(child, new Message(x));

                // c:
                if (father != null) {
                    for (Message m : getMailbox()) {
                        if (m.getSender() == father) {
                            y = (int) m.getContent();
                            break;
                        }
                    }


                    if (y == -1) {
                        // We are here because the node could not find the message sent by its father
                        // It may happen at the beginning. But if it happens two times in a row, it means
                        // that the father finished its work, and won't send any message anymore, so we
                        // tell to the node to stop (since it won't receive any more messages)

                        if (noMsgLastRound) {
                            log("Finished color6 (no msg) round #" + round);
                            status = STATUS.FIX;
                        }

                        noMsgLastRound = true;
                    } else {
                        noMsgLastRound = false;

                        // d:
                        x = Utility.posDiff(x, y);
                        updateColor();

                        // e:
                        double lastL = l;
                        l = 1 + Math.ceil(Math.log(l));

                        // jusqu'à ce que l = l'
                        if (l == lastL) {
                            log("Finished color6 (properly) round #" + round);
                            status = STATUS.FIX;
                        }
                    }
                }
                break;
            case FIX:
                for (Node node : getNeighbors()) {
                    send(node, new Message(x));
                }

                boolean done = false;

                for (Message m : getMailbox()) {
                    if ((int) m.getContent() == x && m.getSender().getID() < getID()) {
                        x = Utility.firstFree(getNeighbors(), this);
                        updateColor();
                    }
                    done = true;
                }

                if (done) {
                    log("Finished algorithm, round #" + round);
                    status = STATUS.DONE;
                }

                break;
            case DONE:
                break;
        }
    }

    private void updateColor() {
        setColor(Utility.getColorFromInt(x));
    }

    private void log(String msg) {
        System.out.println("Node " + getID() + ": " + msg);
    }
}
