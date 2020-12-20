package dev.gallon;

import dev.gallon.algorithms.Algorithm;
import dev.gallon.algorithms.CycleColorationNode;
import dev.gallon.ui.Window;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        // algorithms.add(new GreedyAlgorithm());
        algorithms.add(new Algorithm(CycleColorationNode.class, "Coloration de cycle"));

        new Window(algorithms);
    }
}
