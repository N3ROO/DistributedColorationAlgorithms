package dev.gallon;

import dev.gallon.algorithms.Algorithm;
import dev.gallon.algorithms.ColorSixNode;
import dev.gallon.algorithms.CycleColorationNode;
import dev.gallon.algorithms.GreedyAlgorithm;
import dev.gallon.ui.Window;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new GreedyAlgorithm("Greedy (hors projet)"));
        algorithms.add(new Algorithm(ColorSixNode.class, "Color6 (hors projet)"));
        algorithms.add(new Algorithm(CycleColorationNode.class, "Coloration de cycle (q1)"));

        new Window(algorithms);
    }
}
