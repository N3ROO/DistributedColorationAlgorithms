package dev.gallon.ui;

import dev.gallon.algorithms.Algorithm;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JTopology;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class Window extends JFrame {

    private final ArrayList<Algorithm> algorithms;
    private final JPanel view;
    private final JLabel footerText = new JLabel();
    private JTopology currentAlgorithm = new JTopology(new Topology());

    public Window(ArrayList<Algorithm> algorithms) {
        super("Project");

        this.algorithms = algorithms;

        // Configuration
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout(FlowLayout.LEFT));
        controls.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        view = new JPanel();
        view.setBorder(new TitledBorder(new EtchedBorder(), "View"));

        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Components
        String[] algorithmsNames = new String[this.algorithms.size()];
        for (int i = 0; i < this.algorithms.size(); i++)
            algorithmsNames[i] = this.algorithms.get(i).toString();
        JComboBox<String> algorithmSelector = new JComboBox<>(algorithmsNames);

        //noinspection rawtypes
        algorithmSelector.addActionListener(
                (event) -> this.onAlgorithmChange(((JComboBox) event.getSource()).getSelectedIndex())
        );
        this.onAlgorithmChange(0);
        controls.add(algorithmSelector);
        view.add(currentAlgorithm);
        footer.add(footerText);

        // Display
        this.add(controls, "North");
        this.add(view, "Center");
        this.add(footer, "South");
        this.pack();
        this.setVisible(true);
    }

    private void onAlgorithmChange(int index) {
        this.currentAlgorithm = this.algorithms.get(index).getView();
        updateFooter(this.algorithms.get(index).toString() + " loaded");
    }

    private void updateFooter(String text) {
        this.footerText.setText("Status: " + text);
    }
}
