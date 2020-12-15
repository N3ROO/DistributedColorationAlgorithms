package dev.gallon.algorithms;

public class CycleColoration extends Algorithm {

    public CycleColoration() {
        super(CycleColorationNode.class);
    }

    @Override
    public void loadGraph(String dot) {
        // TODO:
        // - vérifier l'argument attendu (est-ce un string??)
        // - calculer le degré max du graph
        // - charger le graph dans this.tp
        // - init les variables statiques correctement TheNode.DMAX = 0;
    }

    @Override
    public String toString() {
        return "Cycle coloration";
    }
}
