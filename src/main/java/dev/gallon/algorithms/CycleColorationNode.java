package dev.gallon.algorithms;

public class CycleColorationNode extends NodeWithProperties {

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
