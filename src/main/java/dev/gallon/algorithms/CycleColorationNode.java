package dev.gallon.algorithms;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

import java.util.List;

public class CycleColorationNode extends Node {

    enum STATUS {
        INIT, COLOR, FIX, DONE
    }

    class ColorMsg {
        public STATUS status;
        public int color;

        public ColorMsg(STATUS status, int color) {
            this.status = status;
            this.color = color;
        }
    }

    private STATUS status;
    private Node father;
    private List<Node> children;

    private int round;
    private int x;
    private double l;
    private Integer y;
    private int recvMsgCount;
    private boolean noMsgLastRound;

    @Override
    public void onStart() {
        status = STATUS.INIT;
        round = 0;
        getMailbox().clear();
        x = getID();
        y = null;
        updateColor();
        recvMsgCount = 0;
        noMsgLastRound = false;
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

                log("Finished init round #" + round);
                status = STATUS.COLOR;
                break;
            case COLOR:
                // Suite algo color 6

                // 3/a ok
                // b:
                if (!noMsgLastRound)
                    for (Node child : children)
                        send(child, new Message(new ColorMsg(STATUS.COLOR, x)));

                // c:
                if (father != null) {
                    for (Message m : getMailbox()) {
                        ColorMsg content = (ColorMsg) m.getContent();
                        if (content.status != STATUS.COLOR) continue;
                        if (m.getSender() == father) {
                            y = content.color;
                            break;
                        }
                    }
                }

                // d:
                if (y == null) {
                    if (noMsgLastRound) {
                        // Cas u <--> v (pere u c'est v et vice versa)
                        status = STATUS.FIX;
                        x = 0;
                        updateColor();
                        log("attend");
                    }
                    noMsgLastRound = true;
                    break;
                } else {
                    noMsgLastRound = false;
                    x = Utility.posDiff(x, y);
                    updateColor();
                }

                // e:
                double lastL = l;
                l = 1 + Math.ceil(Math.log(l));

                // jusqu'à ce que l = l'
                if (l == lastL) {
                    log("Finished color6 round #" + round);
                    status = STATUS.FIX;
                }

                break;
            case FIX:
                for (Node node : getNeighbors()) {
                    send(node, new Message(new ColorMsg(STATUS.FIX, x)));
                }

                for (Message m : getMailbox()) {
                    ColorMsg content = (ColorMsg) m.getContent();
                    if (content.status != STATUS.FIX) continue;
                    if (content.color == x && m.getSender().getID() < getID()) {
                        x = Utility.firstFree(getNeighbors(), this);
                        updateColor();
                    }
                    recvMsgCount++;
                }

                if (recvMsgCount == getNeighbors().size()) {
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
