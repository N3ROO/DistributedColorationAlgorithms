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

    // algo
    class AlgoData {
        protected STATUS status;
        private Node father;
        private List<Node> children;
        private double l;
        private int recvMsgCount;
        private boolean noMsgLastRound;
        private Integer y;
        private int round;

        public AlgoData init() {
            status = STATUS.INIT;
            getMailbox().clear();
            y = null;
            updateColor();
            recvMsgCount = 0;
            noMsgLastRound = false;
            round = 0;
            return this;
        }
    }

    AlgoData data;
    protected int x;

    @Override
    public void onStart() {
        data = new AlgoData().init();
        x = getID();
    }

    protected void coloration(int k, AlgoData data) {
        if (data.status != STATUS.DONE)
            data.round ++;

        switch (data.status) {
            case INIT:
                // On créé une 1-orientation
                if (getNeighbors().size() > 0) {
                    if (getNeighbors().size() <= k) {
                        data.father = getNeighbors().get(0);
                    } else {
                        data.father = getNeighbors().get(k-1);
                    }
                } else {
                    data.father = null;
                }

                if (data.father != null) log("Father: " + data.father.getID());
                data.children = getNeighbors();
                data.children.remove(data.father);

                // Début algo color6
                // 1.
                updateColor();
                data.l = Math.ceil(Math.log(GraphProperties.N));
                // 2.
                if (data.father == null)
                    data.y = Utility.firstFree(getNeighbors(), this);

                log("Finished init round #" + data.round + " k=" + k);
                data.status = STATUS.COLOR;
                break;
            case COLOR:
                // Suite algo color 6

                // 3/a ok
                // b:
                if (!data.noMsgLastRound)
                    for (Node child : data.children)
                        send(child, new Message(new ColorMsg(STATUS.COLOR, x)));

                // c:
                if (data.father != null) {
                    for (Message m : getMailbox()) {
                        ColorMsg content = (ColorMsg) m.getContent();
                        if (content.status != STATUS.COLOR) continue;
                        if (m.getSender() == data.father) {
                            data.y = content.color;
                            break;
                        }
                    }
                }

                // d:
                if (data.y == null) {
                    if (data.noMsgLastRound) {
                        // Cas u <--> v (pere u c'est v et vice versa)
                        data.status = STATUS.FIX;
                        x = 0;
                        updateColor();
                        log("attend");
                    }
                    data.noMsgLastRound = true;
                    break;
                } else {
                    data.noMsgLastRound = false;
                    x = Utility.posDiff(x, data.y);
                    updateColor();
                }

                // e:
                double lastL = data.l;
                data.l = 1 + Math.ceil(Math.log(data.l));

                // jusqu'à ce que l = l'
                if (data.l == lastL) {
                    log("Finished color6 round #" + data.round + " k=" + k);
                    data.status = STATUS.FIX;
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
                    data.recvMsgCount++;
                }

                if (data.recvMsgCount >= getNeighbors().size()) {
                    log("Finished algorithm, round #" + data.round + " k=" + k);
                    data.status = STATUS.DONE;
                }
                break;
            case DONE:
                break;
        }
    }

    @Override
    public void onClock() {
        coloration(1, data);
    }

    protected void updateColor() {
        setColor(Utility.getColorFromInt(x));
    }

    protected void log(String msg) {
        System.out.println("Node " + getID() + ": " + msg);
    }
}
