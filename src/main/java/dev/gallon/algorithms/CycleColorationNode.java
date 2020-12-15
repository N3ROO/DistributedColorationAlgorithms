package dev.gallon.algorithms;

import io.jbotsim.core.Node;

public class CycleColorationNode extends Node{

    public static int DMAX;

    @Override
    public void onClock() {
        // Appelée au début de chaque ronde (NewPulse)

        // TODO:
        // - récupérer les messages reçus avec getMailBox()
        // - calculer des trucs et préparer les nouveaux messages à envoyés
        // - envoyer les messages aux voisins avec send(Node, Message) ou sendAll(Message)
    }
}
