package dev.gallon;

import dev.gallon.algorithms.Algorithm;
import dev.gallon.algorithms.CycleColoration;
import dev.gallon.ui.Window;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new CycleColoration());

        new Window(algorithms);
    }
}
