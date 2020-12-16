package dev.gallon.algorithms;

import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

import java.util.List;

public class CycleColorationNode extends Node {

    private final double x = getID();
    private double l;
    private List<Node> parents;

    @Override
    public void onStart() {
        this.l = Math.log(GraphProperties.N);
        this.parents = getInNeighbors();
        this.setColor(Color.DARK_GRAY);
    }

    @Override
    public void onClock() {
        // Appelée au début de chaque ronde (NewPulse)

        // TODO:
        // - récupérer les messages reçus avec getMailBox()
        // - calculer des trucs et préparer les nouveaux messages à envoyés
        // - envoyer les messages aux voisins avec send(Node, Message) ou sendAll(Message)

        List<Message> recvMessages = getMailbox();


        System.out.println(getID());
    }
}
